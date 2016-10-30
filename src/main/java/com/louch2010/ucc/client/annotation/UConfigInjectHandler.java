package com.louch2010.ucc.client.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.louch2010.ucc.client.ConfigDataPool;

@Component
public class UConfigInjectHandler{
	private Log logger = LogFactory.getLog(UConfigInjectHandler.class);

	public void doInject(ApplicationContext application) {
		Map<String, Object> beans = application.getBeansWithAnnotation(UConfigBean.class);
		for(String name:beans.keySet()){
			Object obj = beans.get(name);
			if(logger.isDebugEnabled()){
				logger.debug("inject ucc config bean's field: " + name);
			}
			this.inject(obj);
			System.out.println(obj);
		}
	}
	
	/**
	  *description : 为bean注入字段值
	  *@param      : @param bean
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:48:45 由 luocihang 创建 	   
	  */ 
	private void inject(Object bean){
		Class<? extends Object> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(UConfigInject.class)){
				field.setAccessible(true);
				String key = field.getAnnotation(UConfigInject.class).value();
				Type t = field.getGenericType();
				Object value = ConfigDataPool.get(key, t);
				if(value == null){
					continue;
				}
				try {
					field.set(bean, value);
				}catch (Exception e) {
					if(logger.isErrorEnabled()){
						logger.error("", e);
					}
					throw new IllegalArgumentException(e);
				}
			}
		}
	}
}
