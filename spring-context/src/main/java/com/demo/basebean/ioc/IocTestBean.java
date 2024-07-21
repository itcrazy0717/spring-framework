package com.demo.basebean.ioc;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description: ioc 测试bean
 */
public class IocTestBean {

	private String name;

	private String gender;

	private String placeHolderValue;

	public void setPlaceHolderValue(String placeHolderValue) {
		this.placeHolderValue = placeHolderValue;
	}

	public String getPlaceHolderValue() {
		return placeHolderValue;
	}

	public void sayHello() {
		System.out.println("Hello Aop");
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
