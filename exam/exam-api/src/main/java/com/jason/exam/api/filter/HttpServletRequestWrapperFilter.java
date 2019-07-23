package com.jason.exam.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.jason.exam.api.interceptor.BodyReaderHttpServletRequestWrapper;


@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class HttpServletRequestWrapperFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
 
        chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
		
	}

}
