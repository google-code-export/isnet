/**
 * @(#)SecurityCheckFilter.java  
 *
 *Copyright (c)
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intrigueit.myc2i.common.CommonConstants;


 
/**
 * This class is responsible for implementing the security of MyC2i.
 * it check the security when the user request form some resources.
 *
 * @version 	1.00 June 11 2008
 * @author 	Shamim Ahmmed
 */
public class SecurityCheckFilter implements Filter {
    
    private static final String LOGIN_PAGNE = "login.faces";
    private static final String LOGIN_URL = "/login.faces";
    private static final String FILE_EXTENTION = ".faces";

    public SecurityCheckFilter() { 
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig conf) throws ServletException {
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }
    
    /** Creates a new instance of SecurityCheckFilter */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        
        HttpServletRequest hreq = (HttpServletRequest)request;
        HttpServletResponse hres = (HttpServletResponse)response;
        HttpSession session = hreq.getSession();
        
        String url = hreq.getRequestURL().toString();

        boolean notLoginPage = url.endsWith(LOGIN_PAGNE) == false;
        boolean isFaces = url.endsWith(FILE_EXTENTION);
         
        ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE7"); 
        
        /** Don't filter login page because otherwise an endless loop.
        * & only filter .faces otherwise it will filter all images etc as well.
        *
        */
        if (notLoginPage && isFaces)
        {
        /** There is no User attribute so redirect to login page */
	        if(session.getAttribute(CommonConstants.SESSION_MEMBER_EMAIL) == null){
	        	String path = hreq.getContextPath() + LOGIN_URL;
		        hres.sendRedirect(path);
		        return;
	        }
        }
        /** deliver request to next filter */
        chain.doFilter(request, response);
        }

    }
