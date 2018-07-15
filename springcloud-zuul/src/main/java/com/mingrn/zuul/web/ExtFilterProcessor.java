package com.mingrn.zuul.web;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 扩展核心过滤器,在抛出异常时在上下文中增加 FAILED_FILTER 参数，方便后续获取当前过滤器所处阶段
 *
 * @author MinGRn <br > 2018/7/15 14:24
 * @see ErrorExtFilter#shouldFilter()
 */
public class ExtFilterProcessor extends FilterProcessor {

	@Override
	public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
		try {
			return super.processZuulFilter(filter);
		} catch (ZuulException ze) {
			RequestContext context = RequestContext.getCurrentContext();
			context.set("FAILED_FILTER", filter);
			throw ze;
		}
	}
}
