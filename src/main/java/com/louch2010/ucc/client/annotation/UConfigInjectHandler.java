package com.louch2010.ucc.client.annotation;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.louch2010.ucc.client.ConfigDataPool;

public class UConfigInjectHandler implements ApplicationListener<ContextRefreshedEvent>{
	private ApplicationContext application;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.application = event.getApplicationContext();
		Map<String, Object> beans = application.getBeansWithAnnotation(UConfigBean.class);
		for(String name:beans.keySet()){
			Object obj = beans.get(name);
			this.inject(obj);
		}
	}
	
	private void inject(Object bean){
		Class<? extends Object> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(UConfigInject.class)){
				field.setAccessible(true);
				String key = field.getAnnotation(UConfigInject.class).value();
				Object value = ConfigDataPool.get(key, field.getGenericType());
				try {
					field.set(bean, value);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
