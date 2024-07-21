package org.springframework.web.demo.pojo;

/**
 * @author: dengxin.chen
 * @date: 2019-06-24 15:48
 * @description:输出测试bean
 */
public class OutputDTOTest {

	private Integer age;

	private String userName;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "TestOutput[" +
			   "age=" + age +
			   ", userName='" + userName + '\'' +
			   ']';
	}
}
