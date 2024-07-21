package com.demo.basebean.circledepend.setter;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: setter循环依赖演示
 */

public class SetterCircleDependA {

	private SetterCircleDependB setterCircleDependB;

	public void setSetterCircleDependB(SetterCircleDependB setterCircleDependB) {
		this.setterCircleDependB = setterCircleDependB;
	}
}
