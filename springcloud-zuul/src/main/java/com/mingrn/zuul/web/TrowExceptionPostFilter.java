package com.mingrn.zuul.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 路由之后自定义异常
 *
 * @author MinGRn <br > 2018/7/10 20:02
 */
@Component
public class TrowExceptionPostFilter extends ZuulFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(TrowExceptionPostFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		LOGGER.info("This is a pre filter, it will throw a RuntimeException");
		doSomething();
		return null;
	}

	private void doSomething() {
		throw new RuntimeException("Exist some errors...");
	}
}
