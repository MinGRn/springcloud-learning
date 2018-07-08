package com.mingrn.zuul.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul 过滤器
 *
 * @author MinGRn <br > 2018/7/8 17:26
 */
public class AccessFilter extends ZuulFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

	/**
	 * 过滤器类型
	 * "pre": 请求被路由之前执行
	 * "route": 转发请求被路由
	 * "post": 请求被路由之后执行
	 * "error": 请求在路由发生错误时执行
	 * </p>
	 * 官网地址:https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 过滤器执行顺序
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 该过滤器是否被执行
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 过滤器具体业务逻辑
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		LOGGER.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

		Object token = request.getParameter("token");
		if (token == null) {
			LOGGER.warn("token is empty");
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(401);
		}
		return null;
	}
}
