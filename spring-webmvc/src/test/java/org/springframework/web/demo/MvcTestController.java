package org.springframework.web.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.demo.pojo.InputDTO;
import org.springframework.web.demo.pojo.OutputDTO;

/**
 * @author: dengxin.chen
 * @date: 2019-06-21 10:30
 * @description: spring mvc test controller
 */
@RestController
@RequestMapping(value = "/mvc/test")
public class MvcTestController {

	/**
	 * Controller上不带@RequestParam注解
	 *
	 * @param inputA
	 * @param inputB
	 * @return
	 */
	@PostMapping(value = "/get-with-out-param")
	public String getMsgWithoutParam(String inputA, String inputB) {
		return inputA + inputB;
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

	/**
	 * 获取对象
	 *
	 * @return
	 */
	@PostMapping(value = "/bean")
	public OutputDTO getBean() {
		OutputDTO output = new OutputDTO();
		output.setAge(30);
		output.setUserName("itcrazy0717");
		return output;
	}

	/**
	 * 对象输入测试
	 *
	 * @param input
	 * @return
	 */
	@PostMapping("/input")
	public OutputDTO testInput(@RequestBody InputDTO input) {
		OutputDTO output = new OutputDTO();
		output.setAge(input.getAge());
		output.setUserName(input.getUserName());
		return output;
	}
}
