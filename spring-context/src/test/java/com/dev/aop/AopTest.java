package com.dev.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.aop.JdkDynamicAopProxyInterface;
import com.dev.basebean.aop.UserDefinedAopAnnotationBean;
import com.dev.basebean.aop.UserDefinedCglibAopProxy;
import com.dev.basebean.aop.proxycreator.InterceptorTestService;

/**
 * @author: dengxin.chen
 * @date: 2018/11/21 13:41
 * @description: Aop测试 包括CglibAopProxy和JdkDynamicAopProxy代理模式进行aop增强
 */
public class AopTest {

	/**
	 * 基于xml配置形式的aop测试 由于该类未实现接口所以会使用CglibAopProxy进行代理生成
	 */
	@Test
	public void xmlAopTest() {

		System.out.println("基于xml配置形式Aop测试开始——CglibAopProxy代理");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/cglibaopproxy_aop.xml");

		UserDefinedCglibAopProxy cglibAopProxy = context.getBean(UserDefinedCglibAopProxy.class);

		System.out.println("proxy type=" + cglibAopProxy.getClass());

		cglibAopProxy.aopTest("aop test");

		System.out.println();
		System.out.println("age=" + cglibAopProxy.getAge() + " name=" + cglibAopProxy.getName());
		System.out.println();
		System.out.println("基于xml配置形式Aop测试结束——CglibAopProxy代理");

	}

	/**
	 * 基于注解形式的aop测试
	 */
	@Test
	public void annotationAopTest() {

		System.out.println("基于注解形式Aop测试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/annotation_aop.xml");

		UserDefinedAopAnnotationBean userDefinedAopAnnotationBean = context.getBean(UserDefinedAopAnnotationBean.class);

		userDefinedAopAnnotationBean.aopTest("annotation aop test");

		System.out.println();
		System.out.println("基于注解形式Aop测试结束");

	}

	/**
	 * beanNameAutoProxyCreator测试
	 */
	@Test
	public void beanNameAutoProxyCreatorTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.dev.basebean.aop");
		InterceptorTestService service = context.getBean(InterceptorTestService.class);
		service.printMsg();
	}

	/**
	 * 基于xml配置形式的接口实现aop测试
	 * proxy-target-class属性值默认为false，表示使用JDK动态代理织入增强；
	 * 当值为true时，表示使用CGLib动态代理织入增强；
	 * 在使用CGlib进行增强时，如果代理类还是接口，仍然使用JDK动态代理
	 */
	@Test
	public void xmlInterfaceAopTest() {

		System.out.println("基于xml配置形式Aop测试开始——JdkDynamicAopProxy代理");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/jdkdynamicaopproxy_aop.xml");

		JdkDynamicAopProxyInterface jdkDynamicAopProxyInterface = (JdkDynamicAopProxyInterface) context.getBean("aopInterface");

		System.out.println("proxy type=" + jdkDynamicAopProxyInterface.getClass());

		jdkDynamicAopProxyInterface.update();

		System.out.println();
		System.out.println("基于xml配置形式Aop测试开始——JdkDynamicAopProxy代理");
	}
}
