package com.demo.basebean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author: dengxin.chen
 * @date: 2019/5/5 16:06
 * @description:bean生命周期演示
 */
public class BeanLifeCycle implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {

	private String msg;

	public BeanLifeCycle() {
		System.out.println("调用构造函数:BeanLifeCycle()");
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		System.out.println("属性注入:setMsg");
		this.msg = msg;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("BeanNameAware#setBeanName 被调用");
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("BeanClassLoaderAware#setBeanClassLoader 被调用");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryAware#setBeanFactory 被调用");

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean#afterPropertiesSet 被调用，并对msg属性进行修改");
		this.msg = "InitializingBean#afterPropertiesSet方法对msg属性进行修改";
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean#destroy 被调用");
	}

	public void initMethod() {
		System.out.println("init-method 被调用，并对msg属性进行修改，调用顺序在InitializingBean之后");
		this.msg = "init-method方法对msg属性进行修改";
	}

	public void destroyMethod() {
		System.out.println("destroy-method 被调用，调用顺序在DisposableBean之后");
	}
}
