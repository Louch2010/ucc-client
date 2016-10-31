package com.louch2010.ucc.client.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

	/**
	  *description : 注入
	  *@param      : @param application
	  *@return     : void
	  *modified    : 1、2016年10月31日 下午3:36:37 由 luocihang 创建 	   
	  */ 
	public void inject(ApplicationContext application) {
		Map<String, Object> beans = application.getBeansWithAnnotation(UConfigBean.class);
		for(String name:beans.keySet()){
			Object obj = beans.get(name);
			if(logger.isDebugEnabled()){
				logger.debug("inject ucc config bean's field: " + name);
			}
			this.doInject(obj);
		}
	}
	
	/**
	  *description : 为bean注入字段值
	  *@param      : @param bean
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:48:45 由 luocihang 创建 	   
	  */ 
	private void doInject(Object bean){
		Class<? extends Object> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(UConfigInject.class)){
				field.setAccessible(true);
				//声明
				String key = field.getAnnotation(UConfigInject.class).value();
				//过滤final修饰的属性
				if(Modifier.isFinal(field.getModifiers())){
					if(logger.isErrorEnabled()){
						logger.error("ucc can not inject value to field '" + key + "' is final");
					}
					throw new IllegalArgumentException("ucc can not inject value to field '" + key + "' is final");
				}
				//注入值
				Type t = field.getGenericType();
				Object value = ConfigDataPool.get(key, t);
				if(value == null){
					continue;
				}
				try {
					field.set(bean, value);
				}catch (Exception e) {
					if(logger.isErrorEnabled()){
						logger.error("inject value to field error", e);
					}
					throw new IllegalArgumentException(e);
				}
			}
		}
	}
}
