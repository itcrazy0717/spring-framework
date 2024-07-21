package com.demo.basebean.lookupmethod.impl;

import com.demo.basebean.lookupmethod.Car;

/**
 * @author: developer
 * @date: 2019/5/15 22:44
 * @description:
 */
public abstract class ShowCar {

	public void showCarInfo() {
		this.getCar().showCarInfo();
	}

	public abstract Car getCar();
}
