package com.demo.resource;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

/**
 * @author: developer
 * @date: 2019/3/14 22:31
 * @description: Resource体系测试
 */

public class ResourceTest {

	@Test
	public void testGetResource() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();

		Resource resource1 = resourceLoader.getResource("E:\\opensource\\testfile.txt");
		System.out.println("resource1 is FileResource:" + (resource1 instanceof FileSystemResource));
		System.out.println("resource1 is:" + resource1.getClass());

		Resource resource2 = resourceLoader.getResource("/opensource/testfile.txt");
		System.out.println("resource2 is ClassPathContextResource:" + (resource2 instanceof ClassPathResource));
		System.out.println("resource2 is:" + resource2.getClass());

		Resource resource3 = resourceLoader.getResource("file:/opensource/testfile.txt");
		System.out.println("resource3 is UrlResource:" + (resource3 instanceof FileUrlResource));
		System.out.println("resource3 is:" + resource3.getClass());

		Resource resource4 = resourceLoader.getResource("http://www.baidu.com");
		System.out.println("resource4 is UrlResource:" + (resource4 instanceof UrlResource));
		System.out.println("resource4 is:" + resource4.getClass());
	}
}
