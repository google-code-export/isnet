/**
 * @(#)UserPrivilegeCheckFilter.java  
 *
 *Copyright (c)
 *
 */
package com.intrigueit.myc2i.common.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.intrigueit.myc2i.common.CommonConstants;

/**
 * This class is responsible for implementing user privileges of MyC2i.
 * it check the privileges when the user request any application pages.
 *
 * @version 	1.00 December 7 2009
 * @author 	Shahinur Islam (Mithun)
 */
public class UserPrivilegeCheckFilter implements Filter {
    
    private static final String PRIVILEGE_URL = "/userPrivilegeErrorMsg.faces";
    private static final String FILE_EXTENTION = ".faces";
    protected final Log log = LogFactory.getLog(getClass());
    
    public UserPrivilegeCheckFilter() { 
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
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        
        HttpServletRequest hreq = (HttpServletRequest)request;
        HttpServletResponse hres = (HttpServletResponse)response;
        HttpSession session = hreq.getSession();
        String path = hreq.getContextPath() + PRIVILEGE_URL;
        String url = hreq.getRequestURL().toString();         
        String[] data = url.split("/");        
        String page = data[data.length -1];
        
        boolean isFaces = url.endsWith(FILE_EXTENTION);
        
        Object pageAccess = session.getAttribute(CommonConstants.USER_PRIVILEGE_PAGES);
        if(isFaces && pageAccess != null){
        	 ArrayList privilegePages = (ArrayList) pageAccess;
             if (!privilegePages.contains(page)) {   
            	 log.debug("User has no right to acess page: "+ page);
                 hres.sendRedirect(path);
                 return;
             }         	 
        }
        
        /** deliver request to next filter */
        chain.doFilter(request, response);
      }

    }
