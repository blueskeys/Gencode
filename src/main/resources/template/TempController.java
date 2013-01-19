package ${packagename}.controller;

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

import ${packagename}.dao.bean.${className};
import ${packagename}.dao.model.${className}Model;
import ${packagename}.service.${className}Service;
import cn.blueram.gencode.utils.MethodUtil;
 
/**
  * ${codeName} controller
  * @author renjunjie
  *
  */
@Controller
@RequestMapping("/${lowerName}")
public class ${className}Controller extends MultiActionController{
	
	private final static Logger log= LoggerFactory.getLogger(${className}Controller.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ${className}Service<${className}> ${lowerName}Service; 
	
	// Feild start
	private List<${className}> ${lowerName}List = null;
	private ${className} ${lowerName} = new ${className}();

	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>${codeName}列表页面,传统jsp方式，带分页<br>
	 * @return
	 */
	@RequestMapping("/list") 
	public ModelAndView list(${className}Model model){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			${lowerName}List = ${lowerName}Service.selectByModel(model);
			map.put("model",model);
			map.put("resultList",${lowerName}List);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		//访问WEB-INF/jsp/${lowerName}/${lowerName}List.jsp
		return new ModelAndView("${lowerName}/${lowerName}List",map); 
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>${codeName}列表页面,ajax传输json方式，带分页<br>
	 * @return
	 */
	@RequestMapping("/jsonList") 
	public @ResponseBody List<${className}> jsonList(${className}Model model){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			${lowerName}List = ${lowerName}Service.selectByModel(model);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		//直接由springmvc框架依赖jackson生成返回json
		return ${lowerName}List; 
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>${codeName}到添加${codeName}信息页面
	 * <br>
	 * @return
	 */
	@RequestMapping("/toadd") 
	public ModelAndView toadd(${className} bean){
		return new ModelAndView("${lowerName}/${lowerName}Edit"); //访问WEB-INF/jsp/${lowerName}/${lowerName}Edit.jsp
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>${codeName}到编辑${codeName}信息页面<br>
	 * @param ids 确保前提只能转成一个主键编号过来
	 * @return
	 */
	@RequestMapping("/toedit") 
	public ModelAndView toedit(String ids){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			${lowerName} = ${lowerName}Service.selectById(ids);	
			//如果记录为空则跳转到错误页面
			if(${lowerName} == null){
				return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
			}
			map.put("bean", ${lowerName});
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return new ModelAndView("${lowerName}/${lowerName}Edit",map); //访问WEB-INF/jsp/${lowerName}/${lowerName}Edit.jsp
	}
	
	
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>保存${codeName}信息<br>
	 * @return
	 */
	@RequestMapping("/save") 
	public ModelAndView save(${className} bean){
		try{
			//判断Id主键是否为空，如果主键不叫Id，请改成你的主键属性名称
			if(bean.getId() != null && bean.getId()> 0){
				//id不为空修改
				${lowerName}Service.updateBySelective(bean);
			}else{
				//设置主键
				bean.setId(MethodUtil.getInit().getLongId());
				//id为空新增
				${lowerName}Service.insert(bean);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return  list(new ${className}Model());//调整到list页面
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>删除${codeName}信息<br>
	 * @return
	 */
	@RequestMapping("/deletes") 
	public ModelAndView deletes(String[] ids){
		try{
			${lowerName}Service.delete(ids);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return new ModelAndView("error"); //访问WEB-INF/jsp/error.jsp
		}
		return  list(new ${className}Model());//调整到list页面
	}

}
