package com.demo.basebean.initializingbean;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author: dengxin.chen
 * @date: 2019/5/5 11:24
 * @description: InitializingBean演示
 */
public class UserDefinedInitializingBean implements InitializingBean {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean afterPropertiesSet......");
		this.msg = "修改了msg，msg=hello initializingBean!!!!!!";
	}

	public void initMethod() {
		System.out.println("通过init-method方法对msg属性进行修改");
		this.msg = "修改了msg，msg=hello init-method!!!!!!";
	}
}
