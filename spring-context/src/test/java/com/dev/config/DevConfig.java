package com.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dev.basebean.ioc.IocTestBean;

/**
 * @author: dengxin.chen
 * @date: 2019-12-21 11:08
 * @description:
 */
@Configuration
public class DevConfig {

	@Bean
	public IocTestBean iocTestBean() {
		return new IocTestBean();
	}
}
