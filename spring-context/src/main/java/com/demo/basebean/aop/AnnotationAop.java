package com.demo.basebean.aop;

import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: developer
 * @date: 2018/11/21 21:47
 * @description:利用
 */
@Aspect
@Component
public class AnnotationAop {

	@Pointcut("execution(* com.demo.basebean.aop.UserDefinedAopAnnotationBean.aopTest(..))")
	private void pointcutMethod() {

	}

	@Before("pointcutMethod()")
	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println();
		System.out.println("进入前置增强函数【注解形式】");
		Object[] args = joinPoint.getArgs();
		Stream.of(args).forEach(e -> System.out.println((String) e));
	}

	@After("pointcutMethod()")
	public void afterAdvice() {
		System.out.println("进入后置增强函数【注解形式】");
		System.out.println();
	}
}
