package com.demo.basebean.aop.proxycreator;

import org.springframework.stereotype.Service;

/**
 * @author: dengxin.chen
 * @date: 2020-10-13 09:32
 * @description:
 */
@Service
public class InterceptorTestService {

	public void printMsg() {
		System.out.println("打印msg信息");
	}
}
