package org.springframework.web.spingmvc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.spingmvc.pojo.TestOutput;

/**
 * @author: dengxin.chen
 * @date: 2019-06-21 10:30
 * @description: spring mvc test controller
 */
@RestController
@RequestMapping(value = "/spring/mvc/test")
public class TestController {

	/**
	 * Controller上不带@RequestParam注解
	 *
	 * @param input
	 * @return
	 */
	@PostMapping(value = "/get-with-out-param")
	public String getMsgWithoutParam(String input) {
		return input;
	}

	/**
	 * Controller带有@RequestParam注解
	 * 两种方式获取参数名称的方式是不一样的，不加注解需要读文件
	 *
	 * @param input
	 * @return
	 */
	@PostMapping(value = "/get")
	public String getMsg(@RequestParam("input") String input) {
		return input;
	}

	@PostMapping(value = "/get/bean")
	public TestOutput getBean() {
		TestOutput output = new TestOutput();
		output.setAge(10);
		output.setUserName("testBean");
		return output;
	}
}
