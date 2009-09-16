package com.intrigueit.myc2i.utility;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This class provide Context Information.
 * This class contains some of common functionality for
 * manage bean class and JSF interface.
 *
 * @author Mithun 2007/07/07 Created.
 * @version $Revision: 1.1 $ $Date: 2008/01/18 12:23:33 $
 */

public class ContextInfo {
	private static String contextRealPath;
	private String errorMessage = "";
	protected FacesContext facesContext = null;

	public static final String jstlBundleParam = "javax.servlet.jsp.jstl.fmt.localizationContext";

	/** Default constructor */
	public ContextInfo() { }


	/**
	 * @param errorMessage the errorMessage to set
	 */
	public String getErrorMessage() {
		if(errorMessage == null) {
			errorMessage = "";
		}
		return errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Call setErrorMessage function by empty string for hiding error panel.
	 */
  public void hideErrorPanel() {
		setErrorMessage("");
	}

 /**
	 * @return the contextRealPath
	 */
	public static String getContextRealPath() {
		return contextRealPath;
	}

	/**
	 * @param contextRealPath the contextRealPath to set
	 */
	static void setContextRealPath(String path) {
		contextRealPath = path;
	}

	/**
  * Set the face context to the class scope.
  * @param facesContext
  */
	public void setFacesContext(FacesContext facesContext) {
     this.facesContext = facesContext;
	}

	/**
  * Return the face context
  * @return
  */
	public FacesContext getFacesContext() {
		if (facesContext != null){
			return facesContext;
    }
     return FacesContext.getCurrentInstance();
	}

	/**
  * The the parameter value for HttpRequest
  * @param name
  * @return the parameter
  */
   public String getParameter(String name) {
  	 return getRequest().getParameter(name);
   }

  /**
   * Get name of resource bundle from JSTL settings
   * @return
   */
  public String getBundleName() {
  	return getServletContext().getInitParameter(jstlBundleParam);
  }

  /**
   * Return resource bundles contain locale-specific objects.
   * When program needs a locale-specific resource.
   * @return
   */
  public ResourceBundle getBundle() {
  	return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale());
  }

  /**
   * Servlet API Convenience method
   * @return
   */
  protected HttpServletRequest getRequest() {
  	return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
  }

  /**
   * Servlet API Convenience method
   * @return
   */
  protected HttpSession getSession() {
  	return getRequest().getSession();
  }

  /**
   * Servlet API Convenience method
   * @return
   */
  protected HttpServletResponse getResponse() {
  	return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
  }

  /**
   * Servlet API Convenience method
   * @return
   */
  public ServletContext getServletContext() {
  	return (ServletContext) getFacesContext().getExternalContext().getContext();
  }


}
