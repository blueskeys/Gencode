package cn.blueram.gencode.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class  Log4j_Properties{
    private final static  String path;
    private final static  String log4jpath;
    private final static Logger log=Log4j_Properties.getLogClass(Log4j_Properties.class);
    static{
    	 // path=Log4j_Properties.class.getResource("../../../conf/servlet_log.properties").getPath().toString().replaceAll("%20", " ").toString();
    	 log4jpath="log4j.properties";
    	 System.out.println("-------------------------------log4j配置文件名称输出|"+log4jpath);
    	 path=Log4j_Properties.class.getResource(log4jpath).getPath().toString().replaceAll("%20", " ").toString();
    	 System.out.println("-------------------------------log4j配置文件路径输出|"+path);
    	 PropertyConfigurator.configure(path);
    	 log.info("log4j配置文件加载成功");
     }
    @SuppressWarnings("unchecked")
	public static Logger getLogClass(Class c){
    	 final Logger log=Logger.getLogger(c);
    	 return log;
     }
    public static void main(String[] args){
    	//Logger log=Logger.getLogger(Log4j_Properties.class);
    	//log.error("bbb");
    	//log.info("log4j加载配置文件成功");
    	Log4j_Properties.getLogClass(Log4j_Properties.class).info("log4j配置文件加载成功");
    }
}
