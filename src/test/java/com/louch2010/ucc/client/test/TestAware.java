package com.louch2010.ucc.client.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TestAware implements BeanNameAware,//获得Bean名，也就是<Bean>标签的id属性值。  
								  BeanClassLoaderAware,//获得装载过程中的ClassLoader对象。  
								  BeanFactoryAware,//获得BeanFactory对象  
								  ApplicationContextAware,//获得ApplicationContext对象  
								  InitializingBean, //在Bean的所有属性设置完后，并且在调用完上面接口的方法后，调用此接口的afterPropertiesSet方法  
								  DisposableBean //当销毁Bean时，调用此接口的destroy方法  
{

	public void destroy() throws Exception {
		System.out.println("DisposableBean - destroy");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean - afterPropertiesSet");
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("ApplicationContextAware - setApplicationContext");
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryAware - setBeanFactory");
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("BeanClassLoaderAware - setBeanClassLoader");
	}

	public void setBeanName(String name) {
		System.out.println("BeanNameAware - setBeanName");
		System.out.println("BeanNameAware - setBeanName - " + name);
	}

}
