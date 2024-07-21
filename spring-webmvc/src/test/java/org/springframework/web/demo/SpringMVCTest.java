package org.springframework.web.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/mvc/test/get");
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
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/mvc/test/get-with-out-param");
		request.setParameter("inputA", "hello sping mvc");
		request.setParameter("inputB", " two paramters");
		MockHttpServletResponse response = new MockHttpServletResponse();
		dispatcherServlet.service(request, response);
		Assert.assertEquals("hello sping mvc two paramters", response.getContentAsString());
	}

	/**
	 * 返回bean对象单元测试
	 * 注意需要在springmvc配置文件中加入json转换器
	 */
	@Test
	public void controllerBeanTest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/mvc/test/bean");
		MockHttpServletResponse response = new MockHttpServletResponse();
		dispatcherServlet.service(request, response);
		Assert.assertEquals("{\"age\":30,\"userName\":\"itcrazy0717\"}", response.getContentAsString());
	}

	/**
	 * 测试日常工作中一般请求流程，该测试用例需要特别注意
	 * 请求参数为json，然后返回json
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void controllerBeanInputTest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/mvc/test/input");
		MockHttpServletResponse response = new MockHttpServletResponse();

		String inputContent = "{\"age\":30,\"userName\":\"itcrazy0717\"}";
		// 设置请求内容和contentType
		request.setContent(inputContent.getBytes(StandardCharsets.UTF_8));
		request.setContentType("application/json");

		dispatcherServlet.service(request, response);

		Assert.assertEquals("{\"age\":30,\"userName\":\"itcrazy0717\"}", response.getContentAsString());
	}
}
