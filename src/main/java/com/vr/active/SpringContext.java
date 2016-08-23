package com.vr.active;

import org.springframework.context.ApplicationContext;


public class SpringContext {
	private static ApplicationContext context = null;
	public static void setApplicationContext(ApplicationContext applicationcontext) {
		context = applicationcontext;
	}

	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
	public static <T> T getBean(Class<?> clz){
		return (T) context.getBean(clz);
	}
}
