package com.demo.basebean.aop;

import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;

/**
 * @author: developer
 * @date: 2018/11/21 21:47
 * @description:利用
 */
public class XmlAop {

	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println();
		System.out.println("进入前置增强函数");
		Object[] args = joinPoint.getArgs();
		Stream.of(args).forEach(e -> System.out.println((String) e));

	}

	public void afterAdvice() {
		System.out.println("进入后置增强函数");
		System.out.println();
	}
}
