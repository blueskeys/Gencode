package cn.blueram.gencode;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.nutz.lang.Files;

import cn.blueram.gencode.utils.CommonPageParser;
import cn.blueram.gencode.utils.CreateBean;
import cn.blueram.gencode.utils.WolfXmlUtil;



/**
 * 
* @ClassName: GencodeMain
* @Description: TODO(这里用一句话描述这个类的作用)
* @author renjunjie
* @date 2012-11-10 下午9:25:48
*
 */
public class GencodeMain {
	private static ResourceBundle res = ResourceBundle.getBundle("DataSourceConfig");
	private static String url= res.getString("gpt.url"); 
	private static String username =  res.getString("gpt.username");
	private static String passWord = res.getString("gpt.password");
	private static String tableSchema = res.getString("gpt.tableSchema");

	public static void main(String[] args) {
		 CreateBean createBean=new CreateBean();
		 createBean.setMysqlInfo(url, username, passWord);
		 
		 /** 此处修改成你的 表名 和 中文注释***/
		 String tableName="employe"; //表名
		 String codeName ="雇员信息";//中文注释  当然你用英文也是可以的 
		 String className= createBean.getTablesNameToClassName(tableName);
		 String lowerName =className.substring(0, 1).toLowerCase()+className.substring(1, className.length());
		 String packagename = "cc.quanxi.qxt";
		 //项目跟路径路径，此处修改为你的项目路径
		 String rootPath = getRootPath();// "F:\\openwork\\open\\";
		 
		 String projectName = "web";
		 packagename = packagename+"."+projectName;
		 
		 String resPath = getRootPath()+"\\src\\main\\resources\\";
		 
		 //根路径
		 String outPath = rootPath + "target\\genoutput\\";
		 //包路径
		 String mavenPath = outPath + "src\\main\\java\\";
		 //String pckPath =mavenPath+ "cn\\blueram\\"+projectName+"\\";
		 String pckPath =mavenPath+ packagename.replace('.', '\\')+"\\";

		 
		 //页面路径，放到WEB-INF下面是为了不让手动输入路径访问jsp页面，起到安全作用
		 String webPath = outPath + "src\\main\\webapp\\WEB-INF\\jsp\\"; 
		 
		 Files.createDirIfNoExists(outPath);
		 Files.createDirIfNoExists(mavenPath);
		 Files.createDirIfNoExists(webPath);
		 
		 File file=new File(mavenPath);
		 //java,xml文件名称
		 String modelPath = "dao\\model\\"+className+"Model.java";
		 String beanPath =  "dao\\bean\\"+className+".java";
		 String mapperPath = "dao\\mybatis\\mapper\\"+className+"Mapper.java";
		 String servicePath = "service\\"+className+"Service.java";
		 String controllerPath = "controller\\"+className+"Controller.java";
		 String sqlMapperPath = "mybatis\\mapper\\"+className+"Mapper.xml";
		 String springPath="resource\\spring\\";
		 String sqlMapPath="resource\\mybatis\\";
		//jsp页面路径
		 String pageEditPath = lowerName+"\\"+lowerName+"Edit.jsp";
		 String pageListPath = lowerName+"\\"+lowerName+"List.jsp";
		 
		
		VelocityContext context = new VelocityContext();
		context.put("className", className); //
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		
		context.put("packagename", packagename);
		
		/******************************生成bean字段*********************************/
		try{
			context.put("feilds",createBean.getBeanFeilds(tableName,tableSchema)); //生成bean
		}catch(Exception e){
			e.printStackTrace();
		}

		/*******************************生成sql语句**********************************/
		try{
			Map<String,Object> sqlMap=createBean.getAutoCreateSql(tableName,tableSchema);
			context.put("columnDatas",createBean.getColumnDatas(tableName,tableSchema)); //生成bean
			context.put("SQL",sqlMap);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		//-------------------生成文件代码---------------------/
		CommonPageParser.WriterPage(context, "TempBean.java",pckPath, beanPath);  //生成实体Bean
		CommonPageParser.WriterPage(context, "TempModel.java",pckPath,modelPath); //生成Model
		CommonPageParser.WriterPage(context, "TempMapper.java", pckPath,mapperPath); //生成MybatisMapper接口 相当于Dao
		CommonPageParser.WriterPage(context, "TempService.java", pckPath,servicePath);//生成Service
		CommonPageParser.WriterPage(context, "TempMapper.xml",mavenPath,sqlMapperPath);//生成Mybatis xml配置文件
		CommonPageParser.WriterPage(context, "TempController.java",pckPath, controllerPath);//生成Controller 相当于接口
//		生JSP页面，如果不需要可以注释掉
//		CommonPageParser.WriterPage(context, "TempList.jsp",webPath, pageListPath);//生成Controller 相当于接口
//		CommonPageParser.WriterPage(context, "TempEdit.jsp",webPath, pageEditPath);//生成Controller 相当于接口

		
		/*************************修改xml文件*****************************/
		WolfXmlUtil xml=new WolfXmlUtil();
		Map<String,String> attrMap=new HashMap<String, String>();
		try{
//		   /**  引入到 struts.xml  配置文件*/
//		   attrMap.put("file","cn.blueram/conf/struts2/struts2-ssi-"+lowerName+".xml");
//		   xml.getAddNode(srcPath+"struts.xml", "/struts", "include", attrMap, ""); 
//		   attrMap.clear();
//		   /**   引入到sqlmap-conf.xml 配置文件  */
//		   attrMap.put("resource", "cn.blueram/conf/sqlmap/"+className+"SQL.xml");
//		   xml.getAddNode(pckPath+sqlMapPath+"sqlmap-config.xml", "/sqlMapConfig", "sqlMap", attrMap, "");
//		   /**   引入到spring-dao.xml 配置文件 */
//		   attrMap.clear();
//		   attrMap.put("id", lowerName+"Dao");
//		   attrMap.put("class","cn.blueram.dao."+className+"Dao");
//		   xml.getAddNode(pckPath+springPath+"spring-dao.xml", "beans", "bean", attrMap, "");
//		   /**   引入到spring-service.xml 配置文件 */
//		   attrMap.clear();
//		   attrMap.put("id", lowerName+"Service");
//		   attrMap.put("class","cn.blueram.service."+className+"Service");
//		   xml.getAddNode(pckPath+springPath+"spring-service.xml", "beans", "bean", attrMap, "");

		   /**   引入到mybatis-config.xml 配置文件 */
			attrMap.clear();
			attrMap.put("resource","mybatis/mapper/"+className+"Mapper.xml");
		    xml.getAddNode(resPath+"\\mybatis\\mybatis-config.xml", "/configuration/mappers", "mapper", attrMap, "");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取项目的路径
	 * @return
	 */
	public static String getRootPath(){
		String rootPath ="";
		try{
			 File file = new File(CommonPageParser.class.getResource("/").getFile());
			 rootPath = file.getParentFile().getParent()+"\\";
			 
			 rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
			 return rootPath;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rootPath;
	}
}
