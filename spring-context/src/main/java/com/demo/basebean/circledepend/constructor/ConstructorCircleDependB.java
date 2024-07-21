package com.demo.basebean.circledepend.constructor;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: 构造器循环依赖演示
 */

public class ConstructorCircleDependB {

	private ConstructorCircleDependC circleDependC;

	public ConstructorCircleDependB(ConstructorCircleDependC circleDependC) {
		this.circleDependC = circleDependC;
	}
}
