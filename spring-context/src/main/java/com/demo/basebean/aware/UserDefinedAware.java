package com.demo.basebean.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author: developer
 * @date: 2019/4/29 21:09
 * @description: Aware接口 演示类
 */

public class UserDefinedAware implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {

	private String name;
	private ClassLoader classLoader;
	private BeanFactory beanFactory;

	@Override
	public void setBeanName(String name) {
		System.out.println("调用了 BeanNameAware 的 setBeanName方法");
		this.name = name;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("调用了 BeanClassLoaderAware 的 setBeanClassLoader 方法");
		this.classLoader = classLoader;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("调用了 BeanFactoryAware 的 setBeanFactory 方法");
		this.beanFactory = beanFactory;
	}

	public void showMsg() {
		System.out.println("beanName=" + this.name);
		System.out.println("classLoader=" + this.classLoader.getClass());
		System.out.println("是否为单例=" + beanFactory.isSingleton(this.name));
	}
}
