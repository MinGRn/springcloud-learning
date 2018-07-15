package com.mingrn.zuul.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

/**
 * 自定义错误异常扩展类,只过滤 "POST" 类型过滤器
 *
 * @author MinGRn <br > 2018/7/12 20:51
 */
@Component
public class ErrorExtFilter extends SendErrorFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(ErrorExtFilter.class);

	@Override
	public String filterType() {
		return super.filterType();
	}

	/**
	 * 该值要大于 `ErrorFilter` 类定义的值
	 * @see ErrorFilter
	 */
	@Override
	public int filterOrder() {
		return 30;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext context = RequestContext.getCurrentContext();
		ZuulFilter zuulFilter = (ZuulFilter) context.get("FAILED_FILTER");
		LOGGER.info("THE FILTER TYPE IS: {}", zuulFilter.filterType());

		return (zuulFilter != null && "post".equals(zuulFilter.filterType()));
	}
}
