package com.demo.basebean.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author: dengxin.chen
 * @date: 2019/4/30 10:55
 * @description:BeanPostProcessor演示
 */
public class UserDefinedBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanName=" + beanName + " 初始化之前进入");
		if ("beanPostProcessorBase".equals(beanName)) {
			BeanPostProcessorBase processorBase = (BeanPostProcessorBase) bean;
			processorBase.setMsg("Hello BeanPostProcessor!!!!!!!");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanName=" + beanName + " 初始化之后进入");
		return bean;
	}

	public void showMsg() {
		System.out.println("BeanPostProcessor Test!!!!!!");
	}
}
