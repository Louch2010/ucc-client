package com.louch2010.ucc.client;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigDataPool {
	private static Map<String, String> dataPool = new ConcurrentHashMap<String, String>();
	
	public static void load(){
		dataPool.put("jdbc.username", "croborder");
		dataPool.put("jdbc.password", "360buy");
	}
	
	public static Object get(String key, Type t){
		String data = dataPool.get(key);
		if(data == null){
			return null;
		}
		if(Integer.TYPE == t){
			return Integer.parseInt(data);
		}
		//类型判断
		/*if(data instanceof ){
			
		}*/
		return data;
	}
}
