package com.intrigueit.myc2i.utility;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.intrigueit.myc2i.common.view.BasePage;

public class ContextProvider implements ServletContextListener {
	 private static ServletContext theServletContext;

	 public void contextInitialized(ServletContextEvent arg0) {
	     theServletContext = arg0.getServletContext();
	     System.out.println(".................myc2i is Starting....................");
	     BasePage.setContextRealPath(theServletContext.getRealPath("/"));
	 }

	 public void contextDestroyed(ServletContextEvent arg0) {
	     System.out.println(".................myc2i is Removed....................");
	 }
}
