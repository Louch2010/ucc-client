package com.louch2010.ucc.client.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		System.out.println(context.getBean("test"));
	}
}
