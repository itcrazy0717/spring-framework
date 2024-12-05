package com.demo.basebean.ioc;

import org.springframework.stereotype.Component;

/**
 * @author: dengxin.chen
 * @version: $ FirstBean.java,v0.1 2024-12-05 16:02 dengxin.chen Exp $
 * @description: 测试最先启动bean
 */
@Component
public class FirstBean {

	public FirstBean() {
		System.out.println("构造函数-FirstBean");
	}
}
