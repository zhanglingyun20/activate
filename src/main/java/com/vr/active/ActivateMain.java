package com.vr.active;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vr.active.common.SystemCache;
/**
 * 程序入口
 * @author sawyer
 * @date 2016年8月11日
 */
public class ActivateMain {

	private static Logger logger  =  Logger.getLogger(ActivateMain.class );
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		SpringContext.setApplicationContext(context);
		if(SystemCache.initCache()) {
			new MainUI(true);
		}
	}
}
