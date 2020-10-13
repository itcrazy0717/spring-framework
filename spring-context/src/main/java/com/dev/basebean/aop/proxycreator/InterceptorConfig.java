package com.dev.basebean.aop.proxycreator;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: dengxin.chen
 * @date: 2020-10-13 09:27
 * @description:
 */
@Configuration
public class InterceptorConfig {

	@Bean
	public TestMethodInterceptor testMethodInterceptor() {
		return new TestMethodInterceptor();
	}

	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setInterceptorNames("testMethodInterceptor");
		// 设置要拦截的bean
		beanNameAutoProxyCreator.setBeanNames("*Service");
		return beanNameAutoProxyCreator;
	}
}
