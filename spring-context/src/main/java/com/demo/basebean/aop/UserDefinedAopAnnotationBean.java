package com.demo.basebean.aop;

import org.springframework.stereotype.Repository;

/**
 * @author: dengxin.chen
 * @date: 2019/5/10 14:53
 * @description:aop演示bean
 */
@Repository
public class UserDefinedAopAnnotationBean {

	private Integer age;
	private String name;

	public void aopTest(String msg) {
		System.out.println(msg);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
