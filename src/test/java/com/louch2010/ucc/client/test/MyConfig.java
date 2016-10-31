package com.louch2010.ucc.client.test;

import org.springframework.stereotype.Component;

import com.louch2010.ucc.client.annotation.UConfigBean;
import com.louch2010.ucc.client.annotation.UConfigInject;

@UConfigBean
@Component
public class MyConfig {
	@UConfigInject("jdbc.username")
	private String username;
	@UConfigInject("jdbc.password")
	private String password;
	@UConfigInject("jdbc.port")
	private int port;
	@UConfigInject("jdbc.connectTimeout")
	private long connectTimeout;
	@UConfigInject("jdbc.keepAlive")
	private Boolean keepAlive;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public long getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Boolean getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(Boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	@Override
	public String toString() {
		return "MyConfig [username=" + username + ", password=" + password
				+ ", port=" + port + ", connectTimeout=" + connectTimeout
				+ ", keepAlive=" + keepAlive + "]";
	}
}
