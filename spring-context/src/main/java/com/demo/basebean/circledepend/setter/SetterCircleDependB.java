package com.demo.basebean.circledepend.setter;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: setter循环依赖演示
 */

public class SetterCircleDependB {

	private SetterCircleDependC setterCircleDependC;

	public void setSetterCircleDependC(SetterCircleDependC setterCircleDependC) {
		this.setterCircleDependC = setterCircleDependC;
	}
}
