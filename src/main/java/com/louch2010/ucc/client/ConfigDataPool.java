package com.louch2010.ucc.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

public class ConfigDataPool {
	private static Map<String, String> dataPool = new ConcurrentHashMap<String, String>();
	
	public static void put(String key, String value){
		dataPool.put(key, value);
	}
	
	/**
	  *description : 初始化
	  *@param      : @param files
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午7:54:39 由 luocihang 创建 	   
	 * @throws IOException 
	  */ 
	public static void init(List<String> files) throws IOException{
		for (String file:files) {
			load(file);
		}
	}
	
	/**
	  *description : 加载配置文件（默认UTF-8字符集）
	  *@param      : @param fileName
	  *@param      : @throws IOException
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:32:21 由 luocihang 创建 	   
	  */ 
	public static void load(String fileName) throws IOException{
		load(fileName, "UTF-8");
	}
	
	/**
	  *description : 加载配置文件
	  *@param      : @param fileName
	  *@param      : @param charset
	  *@param      : @throws IOException
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:32:40 由 luocihang 创建 	   
	  */ 
	public static void load(String fileName, String charset) throws IOException{
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(fileName)), charset);
		Properties pro = new Properties();
		pro.load(isr);
		loadProperties(pro);
	}
	
	/**
	  *description : 加载配置信息
	  *@param      : @param in
	  *@param      : @throws IOException
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:29:21 由 luocihang 创建 	   
	  */ 
	public static void load(InputStream in) throws IOException{
		Properties pro = new Properties();
		pro.load(in);
		loadProperties(pro);
	}
	
	/**
	  *description : 加载配置信息
	  *@param      : @param pro
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午3:29:19 由 luocihang 创建 	   
	  */ 
	public static void loadProperties(Properties pro){
		for(Object key:pro.keySet()){
			dataPool.put((String)key, (String)pro.get(key));
		}
	}
	
	/**
	  *description : 从配置池中取值（支持基本数据类型自动转换、json数据自动转对象）
	  *@param      : @param key
	  *@param      : @param t
	  *@param      : @return
	  *@return     : Object
	  *modified    : 1、2016年10月25日 下午3:23:17 由 luocihang 创建 	   
	  */ 
	public static Object get(String key, Type t){
		String data = dataPool.get(key);
		//String类型则直接返回
		if(t == String.class){
			return data;
		}
		//对8种基本数据类型进行转换
		if(data == null || "".equals(data.trim())){
			return null;
		}
		try {
			if(Integer.TYPE == t){
				return Integer.parseInt(data);
			}
			if(Boolean.TYPE == t){
				return Boolean.parseBoolean(data);
			}
			if(Float.TYPE == t){
				return Float.parseFloat(data);
			}
			if(Double.TYPE == t){
				return Double.parseDouble(data);
			}
			if(Long.TYPE == t){
				return Long.parseLong(data);
			}
			if(Short.TYPE == t){
				return Short.parseShort(data);
			}
			if(Character.TYPE == t){
				return data.charAt(0);
			}
			if(Byte.TYPE == t){
				return Byte.parseByte(data);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("'"+ data +"' can not convert to " + t + ", ucc key:" + key, e);
		}
		//对json类型直接转对象
		try {
			Gson gson = new Gson();
			return gson.fromJson(data, t);
		} catch (Exception e) {
			throw new IllegalArgumentException("'"+ data +"' can not convert to " + t + ", ucc key:" + key, e);
		}
	}
}
