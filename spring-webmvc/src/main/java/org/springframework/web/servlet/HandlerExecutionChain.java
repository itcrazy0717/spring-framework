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

package org.springframework.web.servlet;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * Handler execution chain, consisting of handler object and any handler interceptors.
 * Returned by HandlerMapping's {@link HandlerMapping#getHandler} method.
 *
 * @author Juergen Hoeller
 * @since 20.06.2003
 * @see HandlerInterceptor
 */
// 处理器执行链
public class HandlerExecutionChain {

	private static final Log logger = LogFactory.getLog(HandlerExecutionChain.class);

	/**
	 * 处理器
	 */
	private final Object handler;

	/**
	 * 拦截器数组
	 */
	@Nullable
	private HandlerInterceptor[] interceptors;

	/**
	 * 拦截器数组
	 * 在实际使用时，会调用{@link #getInterceptors()}方法，初始化到{@link #interceptors}中
	 */
	@Nullable
	private List<HandlerInterceptor> interceptorList;

	/**
	 * 已执行{@link HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)}的位置
	 * 主要用于实现{@link #triggerAfterCompletion(HttpServletRequest, HttpServletResponse, Exception)}的逻辑
	 */
	private int interceptorIndex = -1;


	/**
	 * Create a new HandlerExecutionChain.
	 * @param handler the handler object to execute
	 */
	public HandlerExecutionChain(Object handler) {
		this(handler, (HandlerInterceptor[]) null);
	}

	/**
	 * Create a new HandlerExecutionChain.
	 * @param handler the handler object to execute
	 * @param interceptors the array of interceptors to apply
	 * (in the given order) before the handler itself executes
	 */
	public HandlerExecutionChain(Object handler, @Nullable HandlerInterceptor... interceptors) {
		if (handler instanceof HandlerExecutionChain) {
			HandlerExecutionChain originalChain = (HandlerExecutionChain) handler;
			this.handler = originalChain.getHandler();
			this.interceptorList = new ArrayList<>();
			CollectionUtils.mergeArrayIntoCollection(originalChain.getInterceptors(), this.interceptorList);
			CollectionUtils.mergeArrayIntoCollection(interceptors, this.interceptorList);
		}
		else {
			this.handler = handler;
			this.interceptors = interceptors;
		}
	}


	/**
	 * Return the handler object to execute.
	 */
	public Object getHandler() {
		return this.handler;
	}

	public void addInterceptor(HandlerInterceptor interceptor) {
		initInterceptorList().add(interceptor);
	}

	public void addInterceptors(HandlerInterceptor... interceptors) {
		if (!ObjectUtils.isEmpty(interceptors)) {
			CollectionUtils.mergeArrayIntoCollection(interceptors, initInterceptorList());
		}
	}

	private List<HandlerInterceptor> initInterceptorList() {
		// 如果interceptorList为空，则初始化ArrayList
		if (this.interceptorList == null) {
			this.interceptorList = new ArrayList<>();
			// 如果interceptors非空，则添加到interceptorList中
			if (this.interceptors != null) {
				// An interceptor array specified through the constructor
				CollectionUtils.mergeArrayIntoCollection(this.interceptors, this.interceptorList);
			}
		}
		// 置空 interceptors
		this.interceptors = null;
		return this.interceptorList;
	}

	/**
	 * Return the array of interceptors to apply (in the given order).
	 * @return the array of HandlerInterceptors instances (may be {@code null})
	 */
	@Nullable
	public HandlerInterceptor[] getInterceptors() {
		// 将interceptorList初始化到interceptors中
		if (this.interceptors == null && this.interceptorList != null) {
			this.interceptors = this.interceptorList.toArray(new HandlerInterceptor[0]);
		}
		// 返回interceptors数组
		return this.interceptors;
	}


	/**
	 * 应用拦截器的前置处理<br/>
	 * Apply preHandle methods of registered interceptors.
	 * @return {@code true} if the execution chain should proceed with the
	 * next interceptor or the handler itself. Else, DispatcherServlet assumes
	 * that this interceptor has already dealt with the response itself.
	 */
	boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获得拦截器数组
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			// 遍历  拦截器的前置处理为顺序执行的
			for (int i = 0; i < interceptors.length; i++) {
				HandlerInterceptor interceptor = interceptors[i];
				// 前置处理
				if (!interceptor.preHandle(request, response, this.handler)) {
					// 触发已完成处理
					// 注意这里不是触发当前拦截器已完成的逻辑，而是触发[0,interceptorIndex]这几个拦截器已完成
					// 的逻辑（不包括当前这个拦截器）,并且是按照倒序执行的
					triggerAfterCompletion(request, response, null);
					// 有拦截器处理失败，则返回false，从而使handle处理不能继续进行下去，实现拦截
					return false;
				}
				// 标记interceptor的位置
				this.interceptorIndex = i;
			}
		}
		// 只有拦截器全部执行成功，才会返回true
		return true;
	}

	/**
	 * 应用拦截器的后置处理<br/>
	 * Apply postHandle methods of registered interceptors.
	 */
	void applyPostHandle(HttpServletRequest request, HttpServletResponse response, @Nullable ModelAndView mv)
			throws Exception {
        // 获得拦截器数组 后置处理倒序执行
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			for (int i = interceptors.length - 1; i >= 0; i--) {
				HandlerInterceptor interceptor = interceptors[i];
				// 后置处理
				interceptor.postHandle(request, response, this.handler, mv);
			}
		}
	}

	/**
	 * Trigger afterCompletion callbacks on the mapped HandlerInterceptors.
	 * Will just invoke afterCompletion for all interceptors whose preHandle invocation
	 * has successfully completed and returned true.
	 */
	void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Exception ex)
			throws Exception {
        // 获得拦截器数组
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			// 遍历，这里是倒序遍历，因为拦截器执行成功才会记录interceptorIndex的值
			for (int i = this.interceptorIndex; i >= 0; i--) {
				HandlerInterceptor interceptor = interceptors[i];
				try {
					// 已完成处理
					interceptor.afterCompletion(request, response, this.handler, ex);
				}
				catch (Throwable ex2) { // 如果执行失败，不会结束循环
					logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
				}
			}
		}
	}

	/**
	 * Apply afterConcurrentHandlerStarted callback on mapped AsyncHandlerInterceptors.
	 */
	void applyAfterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response) {
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			for (int i = interceptors.length - 1; i >= 0; i--) {
				if (interceptors[i] instanceof AsyncHandlerInterceptor) {
					try {
						AsyncHandlerInterceptor asyncInterceptor = (AsyncHandlerInterceptor) interceptors[i];
						asyncInterceptor.afterConcurrentHandlingStarted(request, response, this.handler);
					}
					catch (Throwable ex) {
						logger.error("Interceptor [" + interceptors[i] + "] failed in afterConcurrentHandlingStarted", ex);
					}
				}
			}
		}
	}


	/**
	 * Delegates to the handler and interceptors' {@code toString()}.
	 */
	@Override
	public String toString() {
		Object handler = getHandler();
		StringBuilder sb = new StringBuilder();
		sb.append("HandlerExecutionChain with [").append(handler).append("] and ");
		if (this.interceptorList != null) {
			sb.append(this.interceptorList.size());
		}
		else if (this.interceptors != null) {
			sb.append(this.interceptors.length);
		}
		else {
			sb.append(0);
		}
		return sb.append(" interceptors").toString();
	}

}
