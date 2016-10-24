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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "MyConfig [username=" + username + ", password=" + password
				+ "]";
	}

}
