/*
 * @(#)BasePage.java  
 *
 *Copyright (c) Metafour (UK) Ltd 2007
 *
 */

package com.intrigueit.myc2i.common.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.member.domain.Member;


/**
 * This class consists some commone functionality for manage bean
 * class during the interaction with jsf interfaces.
 *
 * @version 	1.00 30/07/07
 * @author 	Shamim Ahmmed
 */
public class BasePage {
    
    protected final Log log = LogFactory.getLog(getClass());
    protected String templateName = null;
    protected FacesContext facesContext = null;
    public static final String jstlBundleParam = "javax.servlet.jsp.jstl.fmt.localizationContext";
    private static String contextRealPath;
    
	protected ArrayList<String> errMsgs = new ArrayList<String>();
	protected Boolean hasError;
	protected String successMessage;
	
    /* Common variable for managing this managebean and entity */
    private String actionType;
    private String reRenderIds; 
    private String msgType;
    private Integer rowIndex;    
    private String errorMessage = "";  
    private String secHeaderMsg;
    private boolean reloadBean = false;
    
    private Member member;
    
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
     * Return key value form resource bundle
     * @param key
     * @return key value
     */
    public String getText(String key) {
        String message;

        try {
            message = getBundle().getString(key);
        } catch (java.util.MissingResourceException mre) {
            log.warn("Missing key for '" + key + "'");

            return "???" + key + "???";
        }

        return message;
    }

    /**
     * Return key value from resource bundle with specifed
     * format
     * @param key
     * @param arg
     * @return the key value.
     */
    public String getText(String key, Object arg) {
        if (arg == null) {
            return getBundle().getString(key);
        }

        MessageFormat form = new MessageFormat(getBundle().getString(key));

        if (arg instanceof String) {
            return form.format(new Object[] { arg });
        } else if (arg instanceof Object[]) {
            return form.format(arg);
        } else {
            log.error("arg '" + arg + "' not String or Object[]");

            return "";
        }
    }

    /**
     * Add a meseage to the session scope 
     * @param key
     * @param arg
     */
    protected void addMessage(String key, Object arg) {

        List messages = (List) getSession().getAttribute("messages");

        if (messages == null) {
            messages = new ArrayList();
        }

        messages.add(getText(key, arg));
        getSession().setAttribute("messages", messages);
    }
    /**
     * Set the messege to the face context.
     * @param ctr
     * @param key
     */
    protected void setMessage(String ctr,String key){
    	String resourceMessage = this.getText(key);
    	FacesMessage facesMessage = new FacesMessage(resourceMessage);
    	facesContext.addMessage(ctr, facesMessage);
    }
    
    protected void addMessage(String key) {
        addMessage(key, null);
    }

    /**
     * Add error message to session scope
     * @param key
     * @param arg
     */
    protected void addError(String key, Object arg) {
        List errors = (List) getSession().getAttribute("errors");

        if (errors == null) {
            errors = new ArrayList();
        }

        errors.add(getText(key, arg));

        getSession().setAttribute("errors", errors);
    }

    protected void addError(String key) {
        addError(key, null);
    }
    
    /**
     * Convenience method for unit tests.
     * @return
     */
    public boolean hasErrors() {
        return (getSession().getAttribute("errors") != null);
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
    protected ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    protected Object getBean(String name) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object bean = fc.getApplication().getVariableResolver().resolveVariable(fc, name);    
        return bean;
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
  	public static void setContextRealPath(String path) {
  		contextRealPath = path;
  	}
  	/**
  	 * @return the reloadBean
  	 */
  	public boolean isReloadBean() {
  		return reloadBean;
  	}

  	/**
  	 * @param reloadBean the reloadBean to set
  	 */
  	public void setReloadBean(boolean reloadBean) {
  		this.reloadBean = reloadBean;
  	}

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
  		this.setMsgType("");
  	  this.errorMessage = errorMessage;
  	}

  	/**
  	 * Call setErrorMessage function by empty string for hiding error panel.
  	 */
    public void hideErrorPanel() {
  		setErrorMessage("");
  	}  
  	/**
  	 * @return the secHeaderMsg
  	 */
  	public String getSecHeaderMsg() {
  		return secHeaderMsg;
  	}

  	/**
  	 * @param secHeaderMsg the secHeaderMsg to set
  	 */
  	public void setSecHeaderMsg(String secHeaderMsg) {
  		this.secHeaderMsg = secHeaderMsg;
  	}
  	
  	/**
     * @return the actionType
     */
    public String getActionType() {
      return actionType;
    }

    /**
     * @param actionType the actionType to set
     */
    public void setActionType(String actionType) {
      this.actionType = actionType;
    }

    /**
     * @return the reRenderIds
     */
    public String getReRenderIds() {
      return reRenderIds;
    }

    /**
     * @param reRenderIds the reRenderIds to set
     */
    public void setReRenderIds(String reRenderIds) {
      this.reRenderIds = reRenderIds;
    }

    /**
     * @return the msgType
     */
    public String getMsgType() {
      return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(String msgType) {
      this.msgType = msgType;
    }

    /**
     * @return the rowIndex
     */
    public Integer getRowIndex() {
      return rowIndex;
    }

    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(Integer rowIndex) {
      this.rowIndex = rowIndex;
    }

	public ArrayList<String> getErrMsgs() {
		return errMsgs;
	}

	public void setErrMsgs(ArrayList<String> errMsgs) {
		this.errMsgs = errMsgs;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public void resetMessage(){
		this.hasError = false;
		this.errMsgs.clear();
		this.setSuccessMessage("");
	}

	public Member getMember() {
		Object obj = this.getSession().getAttribute(CommonConstants.SESSION_MEMBER_KEY);
		if(obj != null) this.member = (Member) obj;
    return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	protected void storeCokie(String name,String value){
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(60*60*24*30);
		this.getResponse().addCookie(cookie);
	}
	
	protected Cookie getCookie(String name){
		Cookie cookie[] = this.getRequest().getCookies();
		if(cookie != null && cookie.length > 0){
			for(int i = 0; i<cookie.length; i++){
		        if(cookie[i].getName().equals(name)){
		            return cookie[i];
		          }
			}
		}
		return null;
	}
	protected String getCookieValue(String name){
		Cookie cookie = this.getCookie(name);
		if(cookie != null){
			return cookie.getValue();
		}
		return null;
	}

}
