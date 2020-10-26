package com.dev.basebean.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author: dengxin.chen
 * @date: 2020-10-26 15:41
 * @description: 事件定义
 */
public class TestEvent extends ApplicationEvent {

	public TestEvent(Object source) {
		super(source);
	}

	public Object getObject() {
		return source;
	}
}
