package com.jason.exam.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jason.exam.api.interceptor.ApiInterceptor;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	ApiInterceptor apiInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(apiInterceptor).addPathPatterns("/**").excludePathPatterns("/getTopic/*");
	}
}
