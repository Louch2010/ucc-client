package com.louch2010.ucc.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.louch2010.ucc.client.annotation.UConfigInjectHandler;
import com.louch2010.ucc.client.constant.Constants;

@Component
public class ClientTimerTask extends TimerTask implements ApplicationContextAware{
	private Log logger = LogFactory.getLog(ClientTimerTask.class);
	private UConfig config;
	private UConfigInjectHandler handler;
	private ApplicationContext application;
	private Timer timer;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.application = applicationContext;
		this.config = applicationContext.getBean(UConfig.class);
		this.handler = applicationContext.getBean(UConfigInjectHandler.class);
		timer = new Timer();
		getConfigDataFromServer(config);
		timer.schedule(this, config.getSyncInterval() * 1000, config.getSyncInterval() * 1000);
	}
	
	public void stop(){
		if(timer != null){
			timer.cancel();
		}
	}
	
	@Override
	public void run() {
		getConfigDataFromServer(config);
	}
	
	public void getConfigDataFromServer(UConfig config){
		try {
			//封装请求命令
			String command = this.getRequestCommand(config.getSources());
			if(command == null){
				return;
			}
			Socket socket = new Socket(config.getServerHost(), config.getServerPort());
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			//发送请求
			PrintWriter pw = new PrintWriter(output);
			pw.println(command);
			pw.flush();
			//IOUtils.closeQuietly(pw);
			//pw.close();
			//解析响应
			List<String> list = this.parseResponse(input, config.getCacheDir());
			//IOUtils.closeQuietly(input);
			input.close();
			//关闭资源
			socket.close();
			//初始化配置池
			ConfigDataPool.init(list);
			//执行注入
			handler.doInject(application);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				//logger.error(e);
				e.printStackTrace();
			}else{
				e.printStackTrace();
			}
		}
	}
	
	/**
	  *description : 解析响应
	  *@param      : @param input
	  *@param      : @param cacheDir
	  *@return     : void
	  *modified    : 1、2016年10月25日 下午6:24:19 由 luocihang 创建 	   
	 * @throws Exception 
	  */ 
	private List<String> parseResponse(InputStream input, String cacheDir) throws Exception{
		List<String> sources = new ArrayList<String>();
		String fileName = null;
		StringBuffer content = null;
		boolean nextLineIsFileName = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String line;
		while((line = br.readLine()) != null){
			line = line.trim();
			if(Constants.Protocol.RESP_CONFIG_START.endsWith(line)){
				nextLineIsFileName = true;
				content = new StringBuffer();
				continue;
			}
			if(nextLineIsFileName){
				fileName = line;
				nextLineIsFileName = false;
				continue;
			}
			if(Constants.Protocol.RESP_CONFIG_END.endsWith(line)){
				sources.add(cacheDir + fileName);
				File dir = new File(cacheDir);
				if(!dir.exists()){
					dir.mkdirs();
				}
				IOUtils.write(content.toString(), new FileOutputStream(new File(cacheDir + fileName)), "UTF-8");
			}
			content.append(line + "\r\n");
		}
		return sources;
	}
	
	/**
	  *description : 
	  *@param      : @param sources
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年10月25日 下午6:30:55 由 luocihang 创建 	   
	  */ 
	private String getRequestCommand(Map<String, String> sources){
		if(sources.size() == 0){
			if(logger.isDebugEnabled()){
				logger.debug("no ucc config file path setting");
			}
			return null;
		}
		StringBuffer sb = new StringBuffer(Constants.Protocol.REQ_CONFIG_COMMAND);
		int i = 1;
		for(String name:sources.keySet()){
			sb.append(name);
			if(i != sources.size()){
				sb.append(Constants.Protocol.REQ_CONFIG_NAME_SPLIT);
			}
			i++;
		}
		return sb.toString();
	}

}
