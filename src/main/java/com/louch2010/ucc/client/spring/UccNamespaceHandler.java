package com.louch2010.ucc.client.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.louch2010.ucc.client.constant.Constants;

/** 
  * @Description: 注册解析器
  * @author: luocihang
  * @date: 2016年10月22日 下午3:14:01
  * @version: V1.0 
  * @see：
  */
public class UccNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
		UccBeanDefinitionParser parser = new UccBeanDefinitionParser();
		super.registerBeanDefinitionParser(Constants.UCC, parser);
	}
}
