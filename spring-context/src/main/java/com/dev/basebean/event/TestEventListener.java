package com.dev.basebean.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: dengxin.chen
 * @date: 2020-10-26 15:44
 * @description: 事件监听器定义
 */
@Component
public class TestEventListener implements ApplicationListener<TestEvent> {

	@Override
	public void onApplicationEvent(TestEvent event) {
		System.out.println("事件监听器接收到消息");
		System.out.println(event.getObject());
		System.out.println("事件监听处理消息完毕");
	}
}
