package cn.blueram.gencode.task;

import org.apache.log4j.Logger;

public class PublishTrigger {
	Logger log = Logger.getLogger(PublishTrigger.class);
	/**
	 * 定时出版首页
	 */
	public void triggerIndexPage(){
		try {
			System.out.println("定时任务 triggerIndexPage");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("定时任务出版首页内容失败",e);
		}
	}
	
	
	/**
	 * 定时出版列表页和详细页面的静态页面
	 */
	public void triggerListPage(){
		try {
			System.out.println("定时任务 triggerListPage");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("定时任务出版列表页内容失败",e);
		}
	}
	
	

}
