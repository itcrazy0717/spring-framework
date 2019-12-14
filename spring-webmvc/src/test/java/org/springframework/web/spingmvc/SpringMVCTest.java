package org.springframework.web.spingmvc;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.test.MockHttpServletRequest;
import org.springframework.mock.web.test.MockHttpServletResponse;
import org.springframework.mock.web.test.MockServletConfig;
import org.springframework.mock.web.test.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author: dengxin.chen
 * @date: 2019-06-21 10:16
 * @description: spring mvc 单元测试
 */
public class SpringMVCTest {

	private DispatcherServlet dispatcherServlet;

	@Before
	public void setup() throws ServletException {
		MockServletConfig servletConfig = new MockServletConfig(new MockServletContext());
		servletConfig.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, "org/springframework/web/context/WEB-INF/springmvc-servlet.xml");
		dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.init(servletConfig);
	}

	/**
	 * Controller流程测试 带有@RequestParam注解
	 */
	@Test
	public void controllerRequestParamTest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/spring/mvc/test/get");
		request.setParameter("input", "hello sping mvc");
		MockHttpServletResponse response = new MockHttpServletResponse();
		dispatcherServlet.service(request, response);
		Assert.assertEquals("hello sping mvc", response.getContentAsString());
	}

	/**
	 * Controller流程测试 不带@RequestParam注解
	 */
	@Test
	public void controllerWithOutRequestParamTest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/spring/mvc/test/get-with-out-param");
		request.setParameter("input", "hello sping mvc");
		MockHttpServletResponse response = new MockHttpServletResponse();
		dispatcherServlet.service(request, response);
		Assert.assertEquals("hello sping mvc", response.getContentAsString());
	}

	/**
	 * 返回bean对象单元测试
	 * 注意需要在springmvc配置文件中加入json转换器
	 */
	@Test
	public void controllerBeanTest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/spring/mvc/test/get/bean");
		MockHttpServletResponse response = new MockHttpServletResponse();
		dispatcherServlet.service(request, response);
		Assert.assertEquals("{\"age\":10,\"userName\":\"testBean\"}", response.getContentAsString());
	}
}
