package com.demo.basebean.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import com.demo.basebean.ioc.FirstBean;

/**
 * @author: dengxin.chen
 * @version: $ TestBeanProcessor.java,v0.1 2024-12-05 15:43 dengxin.chen Exp $
 * @description:
 */
@Component
public class TestBeanProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 注意此处获取的bean中，如果有注入的属性是不能注入成功的，因为此时spring容器还未启动，需要手动注入相关属性
		FirstBean bean = beanFactory.getBean(FirstBean.class);
		System.out.println(bean);
	}
}
