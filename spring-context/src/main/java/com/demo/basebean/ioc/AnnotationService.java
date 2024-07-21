package com.demo.basebean.ioc;

import org.springframework.stereotype.Service;

/**
 * @author: dengxin.chen
 * @date: 2019-11-09 16:28
 * @description:
 */
@Service
public class AnnotationService {

	public void printMsg() {
		System.out.println("this is annotation class");
	}
}
