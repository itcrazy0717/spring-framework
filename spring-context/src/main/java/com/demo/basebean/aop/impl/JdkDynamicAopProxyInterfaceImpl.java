package com.demo.basebean.aop.impl;

import com.demo.basebean.aop.JdkDynamicAopProxyInterface;

/**
 * @author: developer
 * @date: 2019/5/13 21:36
 * @description: 演示使用JdkDynamicAopProxy进行动态代理
 */

public class JdkDynamicAopProxyInterfaceImpl implements JdkDynamicAopProxyInterface {

	@Override
	public void update() {
		System.out.println("实现update方法");
	}
}
