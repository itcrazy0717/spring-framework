package com.demo.basebean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author: dengxin.chen
 * @date: 2019/5/5 16:10
 * @description:beanPostProcessror在bean生命周期中的演示
 */
public class BeanLifeCycleBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName=" + beanName + " 初始化之前调用:BeanPostProcessor#postProcessBeforeInitialization!!!!!");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName=" + beanName + " 初始化之后调用:BeanPostProcessor#postProcessAfterInitialization!!!!!");
		return bean;
	}
}
