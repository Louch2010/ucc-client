package com.louch2010.ucc.client.constant;

public interface Constants {
	
	String UCC = "ucc";
	String CONFIG = "config";
	
	/** 
	  * @Description: 标签
	  * @author: luocihang
	  * @date: 2016年10月22日 下午3:02:47
	  * @version: V1.0 
	  * @see：
	  */
	public interface Element{
		String APP_ID = "appId";
		String SERVER_HOST = "serverHost";
		String SERVER_PORT = "serverPort";
		String CACHE_DIR = "cacheDir";
		String SYNC_INTERVAL = "syncInterval";
		String SOURCE = "source";
		String SOURCES = "sources";
		String SOURCE_ID = "id";
		String SOURCE_PATH = "path";
	}
	
	/** 
	  * @Description: 元素默认值 
	  * @author: luocihang
	  * @date: 2016年10月22日 下午3:02:52
	  * @version: V1.0 
	  * @see：
	  */
	public interface ElementDefaultValue{
		String CACHE_DIR = "/data/ucc";
		int SYNC_INTERVAL = 30;
		int SERVER_PORT = 9527;
	}
	
	/** 
	  * @Description: ucc客户端与服务器端的通讯
	  * @author: luocihang
	  * @date: 2016年10月25日 下午5:13:54
	  * @version: V1.0 
	  * @see：
	  */
	public interface Protocol{
		String REQ_CONFIG_COMMAND = "gconf://";
		String REQ_CONFIG_NAME_SPLIT = "-||-";
		String RESP_CONFIG_START = "<!-||--||--||--||--||--||--||-!>";
		String RESP_CONFIG_END = "-||--||--||--||--||--||--||-!>";
	}
}
