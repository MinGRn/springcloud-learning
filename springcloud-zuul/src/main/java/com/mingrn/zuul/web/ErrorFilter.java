package com.mingrn.zuul.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 路由错误异常处理
 *
 * @author MinGRn <br > 2018/7/15 11:03
 */
@Component
public class ErrorFilter extends ZuulFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(ErrorFilter.class);

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		Throwable throwable = context.getThrowable();
		LOGGER.error("this is a ErrorFilter{}", throwable.getCause().getMessage());
		return null;
	}
}
