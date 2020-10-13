package com.dev.basebean.aop.proxycreator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author: dengxin.chen
 * @date: 2020-10-13 09:18
 * @description:测试
 */
public class TestMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println(invocation.getThis().getClass().getSimpleName() + "#" + invocation.getMethod().getName() + "方法前调用");
		Object ret = invocation.proceed();
		System.out.println(invocation.getThis().getClass().getSimpleName() + "#" + invocation.getMethod().getName() + "方法后调用");
		return ret;
	}
}
