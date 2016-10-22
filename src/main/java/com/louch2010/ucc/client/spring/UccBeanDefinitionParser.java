package com.louch2010.ucc.client.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.louch2010.ucc.client.Ucc;
import com.louch2010.ucc.client.constant.Constants;

/** 
  * @Description: 配置文件解析
  * @author: luocihang
  * @date: 2016年10月22日 下午3:11:20
  * @version: V1.0 
  * @see：http://www.louch2010.com/schema/ucc.xsd
  */
public class UccBeanDefinitionParser extends AbstractSingleBeanDefinitionParser{
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		return Ucc.class;
	}
	
	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder bean) {
		//设置app id
		String appId = element.getAttribute(Constants.Element.APP_ID);
		Assert.hasText(appId, "ucc appId can no be empty");
		bean.addPropertyValue(Constants.Element.APP_ID, appId);
		//设置server host
		String serverHost = element.getAttribute(Constants.Element.SERVER_HOST);
		Assert.hasText(serverHost, "ucc serverHost can no be empty");
		bean.addPropertyValue(Constants.Element.SERVER_HOST, serverHost);
		//设置cache dir
		String cacheDir = element.getAttribute(Constants.Element.CACHE_DIR);
		if(StringUtils.hasText(cacheDir)){
			bean.addPropertyValue(Constants.Element.CACHE_DIR, cacheDir);
		}
		//设置sync interval
		String syncInterval = element.getAttribute(Constants.Element.SYNC_INTERVAL);
		if(StringUtils.hasText(syncInterval)){
			int interval = 0;
			try {
				interval = Integer.parseInt(syncInterval);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			bean.addPropertyValue(Constants.Element.SYNC_INTERVAL, interval);
		}
		//解析子标签
		Map<String, String> sources = new HashMap<String, String>();
		NodeList children = element.getElementsByTagName(Constants.Element.SOURCE);
		for (int i = 0; i < children.getLength(); i++) {
			Element child = (Element) children.item(i);
			String id = child.getAttribute(Constants.Element.SOURCE_ID);
			Assert.hasText(id, "ucc source id can no be empty");
			String path = child.getAttribute(Constants.Element.SOURCE_PATH);
			Assert.hasText(appId, "ucc source path no be empty");
			if(sources.containsKey(id) || sources.containsValue(path)){
				throw new IllegalArgumentException("duplicate source id or path");
			}
			sources.put(id, path);
		}
		bean.addPropertyValue(Constants.Element.SOURCES, sources);
	}
}
