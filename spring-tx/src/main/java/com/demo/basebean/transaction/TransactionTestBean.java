package com.demo.basebean.transaction;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author: dengxin.chen
 * @date: 2019/5/20 16:32
 * @description:
 */
public class TransactionTestBean {

	private Integer age;

	public Integer getAge() {
		return age;
	}

	@Transactional(rollbackFor = Exception.class)
	public void setAge(Integer age) {
		this.age = age;
	}
}
