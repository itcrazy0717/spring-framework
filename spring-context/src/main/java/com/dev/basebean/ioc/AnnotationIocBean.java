package com.dev.basebean.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description: ioc 测试bean
 */
@Component
public class AnnotationIocBean {

	/**
	 * 测试@Autowired自动注入属性
	 */
	@Autowired
	private AnnotationService annotationService;

	private String name;

	private String gender;

	private String placeHolderValue;

	public void sayHello() {
		System.out.println("Hello Aop");
	}

	public void setPlaceHolderValue(String placeHolderValue) {
		this.placeHolderValue = placeHolderValue;
	}

	public String getPlaceHolderValue() {
		return placeHolderValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void initMethod() {
		System.out.println("Enter IocTestBean.initMethod()");
	}
}
