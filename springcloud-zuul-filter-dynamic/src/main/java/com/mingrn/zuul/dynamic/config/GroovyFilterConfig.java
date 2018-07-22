package com.mingrn.zuul.dynamic.config;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GroovyFilterConfig {

	private static Logger LOGGER = LoggerFactory.getLogger(GroovyFilterConfig.class);

	@Value("${zuul.filter.root}")
	private String scriptRoot;

	@Value("${zuul.filter.refreshInterval}")
	private Integer refreshInterval;


	@Bean
	public FilterLoader filterLoader() {
		FilterLoader filterLoader = FilterLoader.getInstance();
		filterLoader.setCompiler(new GroovyCompiler());
		FilterFileManager.setFilenameFilter(new GroovyFileFilter());
		try {
			FilterFileManager.init(
					refreshInterval,
					scriptRoot + "/pre", scriptRoot + "/post"
			);
		} catch (Exception e) {
			LOGGER.error("FilterFileManager Init Exception ==>", e);
		}
		return filterLoader;
	}
}
