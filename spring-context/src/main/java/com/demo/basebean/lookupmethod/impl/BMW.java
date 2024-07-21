package com.demo.basebean.lookupmethod.impl;

import com.demo.basebean.lookupmethod.Car;

/**
 * @author: developer
 * @date: 2019/5/15 22:42
 * @description:
 */

public class BMW implements Car {

	@Override
	public void showCarInfo() {
		System.out.println("hello,我是宝马!!!");
	}
}
