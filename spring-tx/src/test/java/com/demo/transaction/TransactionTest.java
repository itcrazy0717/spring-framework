package com.demo.transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.basebean.transaction.TransactionTestBean;

/**
 * @author: dengxin.chen
 * @date: 2019-05-27 10:31
 * @description:
 */
public class TransactionTest {

	/**
	 * 基于xml事务测试
	 */
	@Test
	public void transactionXmlTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:org/springframework/transaction/transaction_xml.xml");

		TransactionTestBean transactionTestBean = context.getBean(TransactionTestBean.class);

		transactionTestBean.getAge();
	}

	/**
	 * 基于注解的事务测试
	 */
	@Test
	public void transactionAnnotationTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:org/springframework/transaction/transaction_annotation.xml");

		TransactionTestBean transactionTestBean = context.getBean(TransactionTestBean.class);

		transactionTestBean.setAge(12);

		System.out.println("age=" + transactionTestBean.getAge());

	}
}
