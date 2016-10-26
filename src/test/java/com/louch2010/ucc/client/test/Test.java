package com.louch2010.ucc.client.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.louch2010.ucc.client.ConfigDataPool;

public class Test {
	public static void main(String[] args) {
//		ConfigDataPool.put("jdbc.username", "croborder");
//		ConfigDataPool.put("jdbc.password", "360buy");
//		ConfigDataPool.put("jdbc.port", "1521");
//		ConfigDataPool.put("jdbc.connectTimeout", "10000000");
//		ConfigDataPool.put("jdbc.keepAlive", "true");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		System.out.println(context.getBean("test"));
		System.out.println(context.getBean(MyConfig.class));
		//context.close();
	}
}
