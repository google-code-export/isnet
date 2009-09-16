/**
 * @(#)EncodingFilter.java  
 *
 *Copyright (c) Metafour (UK) Ltd 2008
 *
 */
package com.intrigueit.myc2i.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This class is a filter implementation of NetCourier Agent System.
 * It set the application character encoding system.
 *
 * @version 	1.00 June 11 2008
 * @author 	Shamim Ahmmed
 */
public class EncodingFilter implements Filter {
	
	private String encoding = "UTF-8";
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");  
	  
	    //System.out.println("Setting character encoding to:"+encoding);
	    filterChain.doFilter(request, response);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	    String encodingParam = filterConfig.getInitParameter("encoding");
		if (encodingParam != null) {
		encoding = encodingParam;
		}

	}

}
