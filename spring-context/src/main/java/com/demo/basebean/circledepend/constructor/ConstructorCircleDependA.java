package com.demo.basebean.circledepend.constructor;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: 构造器循环依赖演示
 */

public class ConstructorCircleDependA {

	private ConstructorCircleDependB constructorCircleDependB;

	/**
	 * 演示构造器的循环依赖 会直接抛出异常
	 *
	 * @param constructorCircleDependB
	 */
	public ConstructorCircleDependA(ConstructorCircleDependB constructorCircleDependB) {
		this.constructorCircleDependB = constructorCircleDependB;
	}

}
