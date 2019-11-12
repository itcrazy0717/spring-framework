/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * Default implementation of the {@link BeanDefinitionDocumentReader} interface that
 * reads bean definitions according to the "spring-beans" DTD and XSD format
 * (Spring's default XML bean definition format).
 *
 * <p>The structure, elements, and attribute names of the required XML document
 * are hard-coded in this class. (Of course a transform could be run if necessary
 * to produce this format). {@code <beans>} does not need to be the root
 * element of the XML document: this class will parse all bean definition elements
 * in the XML file, regardless of the actual root element.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Erik Wiersma
 * @since 18.12.2003
 */

/**
 * BeanDefinitionDocumentReader的默认实现，该接口只有这一个默认实现
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

	/**
	 * bean标签元素
	 */
	public static final String BEAN_ELEMENT = BeanDefinitionParserDelegate.BEAN_ELEMENT;

	/**
	 * beans标签元素
	 */
	public static final String NESTED_BEANS_ELEMENT = "beans";

	/**
	 * alias标签元素
	 */
	public static final String ALIAS_ELEMENT = "alias";

	/**
	 * name属性
	 */
	public static final String NAME_ATTRIBUTE = "name";

	public static final String ALIAS_ATTRIBUTE = "alias";

	/**
	 * import标签元素
	 */
	public static final String IMPORT_ELEMENT = "import";

	/**
	 * resource属性 import标签的属性
	 */
	public static final String RESOURCE_ATTRIBUTE = "resource";

	/**
	 * profile标签元素
	 */
	public static final String PROFILE_ATTRIBUTE = "profile";


	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * xml解析上下文
	 */
	@Nullable
	private XmlReaderContext readerContext;

	/**
	 * BeanDefinition解析委托
	 */
	@Nullable
	private BeanDefinitionParserDelegate delegate;


	/**
	 * This implementation parses bean definitions according to the "spring-beans" XSD
	 * (or DTD, historically).
	 * <p>Opens a DOM Document; then initializes the default settings
	 * specified at the {@code <beans/>} level; then parses the contained bean definitions.
	 */
	@Override
	public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
		this.readerContext = readerContext;
		logger.debug("Loading bean definitions");
		// 获取XML Document对象的root元素
		Element root = doc.getDocumentElement();
		// 执行注册
		doRegisterBeanDefinitions(root);
	}

	/**
	 * Return the descriptor for the XML resource that this parser works on.
	 */
	protected final XmlReaderContext getReaderContext() {
		Assert.state(this.readerContext != null, "No XmlReaderContext available");
		return this.readerContext;
	}

	/**
	 * Invoke the {@link org.springframework.beans.factory.parsing.SourceExtractor}
	 * to pull the source metadata from the supplied {@link Element}.
	 */
	@Nullable
	protected Object extractSource(Element ele) {
		return getReaderContext().extractSource(ele);
	}


	/**
	 * Register each bean definition within the given root {@code <beans/>} element.
	 */
	protected void doRegisterBeanDefinitions(Element root) {
		// Any nested <beans> elements will cause recursion in this method. In
		// order to propagate and preserve <beans> default-* attributes correctly,
		// keep track of the current (parent) delegate, which may be null. Create
		// the new (child) delegate with a reference to the parent for fallback purposes,
		// then ultimately reset this.delegate back to its original (parent) reference.
		// this behavior emulates a stack of delegates without actually necessitating one.

		// 记录老的BeanDefinitionParserDelegate对象
		BeanDefinitionParserDelegate parent = this.delegate;

		// 创建BeanDefinitionParserDelegate对象，并对delegate进行默认设置
		this.delegate = createDelegate(getReaderContext(), root, parent);

		// 检测<beans/>标签的命名空间是否为空，或者是否为"http://www.springframework.org/schema/beans"
		if (this.delegate.isDefaultNamespace(root)) {
			// 处理profile属性
			String profileSpec = root.getAttribute(PROFILE_ATTRIBUTE);
			if (StringUtils.hasText(profileSpec)) {
				// 如果profile属性有值，则使用分隔符进行切分，因为可能有多个profile
				String[] specifiedProfiles = StringUtils.tokenizeToStringArray(
						profileSpec, BeanDefinitionParserDelegate.MULTI_VALUE_ATTRIBUTE_DELIMITERS);
				// 检测profile是否有效，如果无效，则不进行注册
				if (!getReaderContext().getEnvironment().acceptsProfiles(specifiedProfiles)) {
					if (logger.isInfoEnabled()) {
						logger.info("Skipped XML bean definition file due to specified profiles [" + profileSpec +
								"] not matching: " + getReaderContext().getResource());
					}
					return;
				}
			}
		}
		// 解析xml前置处理，该方法为空，主要留给用户自定义实现，增强扩展性
		preProcessXml(root);
		// 核心函数，进行xml解析
		parseBeanDefinitions(root, this.delegate);
		// 解析xml后置处理，该方法也为空，主要留给用户自定义实现，增强扩展性
		postProcessXml(root);
		// 将delegate回到老的BeanDefinitionParserDelegate对象
		this.delegate = parent;
	}

	protected BeanDefinitionParserDelegate createDelegate(
			XmlReaderContext readerContext, Element root, @Nullable BeanDefinitionParserDelegate parentDelegate) {
		// 创建BeanDefinitionParserDelegate对象
		BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
		// 初始化默认标签属性
		delegate.initDefaults(root, parentDelegate);
		return delegate;
	}

	/**
	 * 解析Document的核心函数<br/>
	 * Parse the elements at the root level in the document:
	 * "import", "alias", "bean".
	 *
	 * @param root the DOM root element of the document
	 */
	protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
		// 如果根节点使用默认命名空间，则执行默认解析
		if (delegate.isDefaultNamespace(root)) {
			NodeList nl = root.getChildNodes();
			// 遍历子节点
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				if (node instanceof Element) {
					Element ele = (Element) node;
					// 如果该节点使用默认命名空间，则执行默认解析
					if (delegate.isDefaultNamespace(ele)) {
						parseDefaultElement(ele, delegate);
						// 如果该节点使用非默认命名空间，则执行自定义解析
					} else {
						// 非默认命名空间的解析会走该分支 比如 aop、注解注入bean
						delegate.parseCustomElement(ele);
					}
				}
			}
			// 否则使用自定义解析根节点
		} else {
			delegate.parseCustomElement(root);
		}
	}

	/**
	 * 解析默认标签
	 *
	 * @param ele      当前元素节点
	 * @param delegate BeanDefinition解析委托
	 */
	private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
		// import标签
		if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {
			// 主要流程获取import标签的source属性，然后通过loadBeanDefinitions加载BeanDefinition
			importBeanDefinitionResource(ele);
		} else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) { // alias标签
			processAliasRegistration(ele);
		} else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) { // bean标签，主要解析标签
			processBeanDefinition(ele, delegate);
		} else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)) { // beans标签
			// recurse
			doRegisterBeanDefinitions(ele);
		}
	}

	/**
	 * 解析import标签<br/>
	 * Parse an "import" element and load the bean definitions
	 * from the given resource into the bean factory.
	 */
	protected void importBeanDefinitionResource(Element ele) {
		// 获取resource属性
		String location = ele.getAttribute(RESOURCE_ATTRIBUTE);
		// resource为空，则直接返回
		if (!StringUtils.hasText(location)) {
			getReaderContext().error("Resource location must not be empty", ele);
			return;
		}

		// 解析系统属性
		// Resolve system properties: e.g. "${user.dir}"
		location = getReaderContext().getEnvironment().resolveRequiredPlaceholders(location);

		// 实例化一个set集合，用于存储Resource集合
		Set<Resource> actualResources = new LinkedHashSet<>(4);

		/**
		 * 判断location是相对路径还是绝对路径 默认为相对路径
		 */
		// Discover whether the location is an absolute or relative URI
		boolean absoluteLocation = false;
		try {
			// 绝对路径判断
			absoluteLocation = ResourcePatternUtils.isUrl(location) || ResourceUtils.toURI(location).isAbsolute();
		} catch (URISyntaxException ex) {
			// cannot convert to an URI, considering the location relative
			// unless it is the well-known Spring prefix "classpath*:"
		}

		// Absolute or relative?
		// 绝对路径
		if (absoluteLocation) {
			try {
				// 添加配置文件地址的Resource到actualResources，并解析BeanDefinition
				int importCount = getReaderContext().getReader().loadBeanDefinitions(location, actualResources);
				if (logger.isDebugEnabled()) {
					logger.debug("Imported " + importCount + " bean definitions from URL location [" + location + "]");
				}
			} catch (BeanDefinitionStoreException ex) {
				getReaderContext().error(
						"Failed to import bean definitions from URL location [" + location + "]", ele, ex);
			}
			// 相对路径
		} else {
			// No URL -> considering resource location as relative to the current file.
			try {
				int importCount;
				// 创建相对路径的Resource
				Resource relativeResource = getReaderContext().getResource().createRelative(location);
				// 存在
				if (relativeResource.exists()) {
					// 加载相对路径下的BeanDefinition，并将Resource加入actualResources中
					importCount = getReaderContext().getReader().loadBeanDefinitions(relativeResource);
					actualResources.add(relativeResource);
					// 不存在
				} else {
					// 获取根路径
					String baseLocation = getReaderContext().getResource().getURL().toString();
					// 通过绝对路径来加载BeanDefinition
					importCount = getReaderContext().getReader().loadBeanDefinitions(
							StringUtils.applyRelativePath(baseLocation, location)/*计算绝对路径*/, actualResources);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Imported " + importCount + " bean definitions from relative location [" + location + "]");
				}
			} catch (IOException ex) {
				getReaderContext().error("Failed to resolve current resource location", ele, ex);
			} catch (BeanDefinitionStoreException ex) {
				getReaderContext().error("Failed to import bean definitions from relative location [" + location + "]",
						ele, ex);
			}
		}
		// 解析成功后，进行监听器激活处理
		Resource[] actResArray = actualResources.toArray(new Resource[0]);
		getReaderContext().fireImportProcessed(location, actResArray, extractSource(ele));
	}

	/**
	 * Process the given alias element, registering the alias with the registry.
	 */
	protected void processAliasRegistration(Element ele) {
		String name = ele.getAttribute(NAME_ATTRIBUTE);
		String alias = ele.getAttribute(ALIAS_ATTRIBUTE);
		boolean valid = true;
		if (!StringUtils.hasText(name)) {
			getReaderContext().error("Name must not be empty", ele);
			valid = false;
		}
		if (!StringUtils.hasText(alias)) {
			getReaderContext().error("Alias must not be empty", ele);
			valid = false;
		}
		if (valid) {
			try {
				getReaderContext().getRegistry().registerAlias(name, alias);
			} catch (Exception ex) {
				getReaderContext().error("Failed to register alias '" + alias +
						"' for bean with name '" + name + "'", ele, ex);
			}
			getReaderContext().fireAliasRegistered(name, alias, extractSource(ele));
		}
	}

	/**
	 * bean标签解析，为核心解析函数<br/>
	 * Process the given bean element, parsing the bean definition
	 * and registering it with the registry.
	 */
	protected void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
		// 进行bean标签解析
		// 如果解析成功，则返回BeanDefinitionHolder，BeanDefinitionHolder为name和alias的BeanDefinition对象
		// 如果解析失败，则返回null
		BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
		if (bdHolder != null) {
			// 进行标签处理，主要对bean标签的子节点属性进行处理 如： p:name="测试用例"
			bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);
			try {
				// 将解析装配好的BeanDefinition注册到IOC容器中
				// Register the final decorated instance.
				BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());
			} catch (BeanDefinitionStoreException ex) {
				getReaderContext().error("Failed to register bean definition with name '" +
						bdHolder.getBeanName() + "'", ele, ex);
			}
			// 发出响应时间，通知监听器，已完成该bean标签的解析
			// Send registration event.
			getReaderContext().fireComponentRegistered(new BeanComponentDefinition(bdHolder));
		}
	}


	/**
	 * Allow the XML to be extensible by processing any custom element types first,
	 * before we start to process the bean definitions. This method is a natural
	 * extension point for any other custom pre-processing of the XML.
	 * <p>The default implementation is empty. Subclasses can override this method to
	 * convert custom elements into standard Spring bean definitions, for example.
	 * Implementors have access to the parser's bean definition reader and the
	 * underlying XML resource, through the corresponding accessors.
	 *
	 * @see #getReaderContext()
	 */
	protected void preProcessXml(Element root) {
	}

	/**
	 * Allow the XML to be extensible by processing any custom element types last,
	 * after we finished processing the bean definitions. This method is a natural
	 * extension point for any other custom post-processing of the XML.
	 * <p>The default implementation is empty. Subclasses can override this method to
	 * convert custom elements into standard Spring bean definitions, for example.
	 * Implementors have access to the parser's bean definition reader and the
	 * underlying XML resource, through the corresponding accessors.
	 *
	 * @see #getReaderContext()
	 */
	protected void postProcessXml(Element root) {
	}

}
