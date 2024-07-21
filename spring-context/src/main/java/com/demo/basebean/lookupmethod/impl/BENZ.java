package com.demo.basebean.lookupmethod.impl;

import com.demo.basebean.lookupmethod.Car;

/**
 * @author: developer
 * @date: 2019/5/15 22:43
 * @description:
 */
public class BENZ implements Car {

	@Override
	public void showCarInfo() {
		System.out.println("hello,我是奔驰!!!");
	}
}
