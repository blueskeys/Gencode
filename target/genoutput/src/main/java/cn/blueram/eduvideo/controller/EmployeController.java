package cn.blueram.eduvideo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cn.blueram.eduvideo.dao.bean.Employe;
import cn.blueram.eduvideo.dao.model.EmployeModel;
import cn.blueram.eduvideo.service.EmployeService;
import cn.blueram.eduvideo.controller.EmployeController;
import cn.blueram.gencode.utils.MethodUtil;
 
/**
  * 雇员信息 controller
  * @author renjunjie
  *
  */
@Controller
@RequestMapping("/employe")
public class EmployeController extends MultiActionController{
	
	private final static Logger log= LoggerFactory.getLogger(EmployeController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private EmployeService<Employe> employeService; 
	
	// Feild start
	private List<Employe> employeList = null;
	private Employe employe = new Employe();

	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>雇员信息列表页面,传统jsp方式，带分页<br>
	 * @return
	 */
	@RequestMapping("/list") 
	public ModelAndView list(EmployeModel model){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			employeList = employeService.selectByModel(model);
			map.put("model",model);
			map.put("resultList",employeList);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		//访问WEB-INF/jsp/employe/employeList.jsp
		return new ModelAndView("employe/employeList",map); 
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>雇员信息列表页面,ajax传输json方式，带分页<br>
	 * @return
	 */
	@RequestMapping("/jsonList") 
	public @ResponseBody List<Employe> jsonList(EmployeModel model){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			employeList = employeService.selectByModel(model);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		//直接由springmvc框架依赖jackson生成返回json
		return employeList; 
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>雇员信息到添加雇员信息信息页面
	 * <br>
	 * @return
	 */
	@RequestMapping("/toadd") 
	public ModelAndView toadd(Employe bean){
		return new ModelAndView("employe/employeEdit"); //访问WEB-INF/jsp/employe/employeEdit.jsp
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>雇员信息到编辑雇员信息信息页面<br>
	 * @param ids 确保前提只能转成一个主键编号过来
	 * @return
	 */
	@RequestMapping("/toedit") 
	public ModelAndView toedit(Long ids){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			employe = employeService.selectById(ids);	
			//如果记录为空则跳转到错误页面
			if(employe == null){
				return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
			}
			map.put("bean", employe);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return new ModelAndView("employe/employeEdit",map); //访问WEB-INF/jsp/employe/employeEdit.jsp
	}
	
	
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>保存雇员信息信息<br>
	 * @return
	 */
	@RequestMapping("/save") 
	public ModelAndView save(Employe bean){
		try{
			//判断Id主键是否为空，如果主键不叫Id，请改成你的主键属性名称
			if(bean.getId() != null && bean.getId()> 0){
				//id不为空修改
				employeService.updateBySelective(bean);
			}else{
				//设置主键
				bean.setId(MethodUtil.getInit().getLongId());
				//id为空新增
				employeService.insert(bean);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return  list(new EmployeModel());//调整到list页面
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>删除雇员信息信息<br>
	 * @return
	 */
	@RequestMapping("/deletes") 
	public ModelAndView deletes(Long[] ids){
		try{
			employeService.delete(ids);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return  list(new EmployeModel());//调整到list页面
	}

}
