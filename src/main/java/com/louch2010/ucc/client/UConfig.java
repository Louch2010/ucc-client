package com.louch2010.ucc.client;

import java.util.HashMap;
import java.util.Map;

import com.louch2010.ucc.client.constant.Constants;

/** 
  * @Description: UCC配置
  * @author: luocihang
  * @date: 2016年10月22日 下午2:40:09
  * @version: V1.0 
  * @see：
  */
public class UConfig {
	private String appId;
	private String serverHost;
	private String cacheDir = Constants.ElementDefaultValue.CACHE_DIR;
	private int syncInterval = Constants.ElementDefaultValue.SYNC_INTERVAL;
	private Map<String, String> sources = new HashMap<String, String>();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getCacheDir() {
		return cacheDir;
	}

	public void setCacheDir(String cacheDir) {
		this.cacheDir = cacheDir;
	}

	public int getSyncInterval() {
		return syncInterval;
	}

	public void setSyncInterval(int syncInterval) {
		this.syncInterval = syncInterval;
	}

	public Map<String, String> getSources() {
		return sources;
	}

	public void setSources(Map<String, String> sources) {
		this.sources = sources;
	}

	@Override
	public String toString() {
		return "Ucc [appId=" + appId + ", serverHost=" + serverHost
				+ ", cacheDir=" + cacheDir + ", syncInterval=" + syncInterval
				+ ", sources=" + sources + "]";
	}
	
}
