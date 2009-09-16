package com.intrigueit.myc2i.common;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import com.intrigueit.myc2i.common.view.BasePage;

public class ControllerBean extends BasePage implements Serializable {
	private static final long serialVersionUID = -2399884208294434812L;
	private static final String SKIN_NAME  = "skinName";
	private String skin;
	private String defaultSkin = "DeFault";
	private String imagesPath;
	private String cssPath;
	private String contextPath;
	private String tagURL;
  public ControllerBean () {

	}
  public void changeTheme() {
		String skinName = this.getParameter(SKIN_NAME);
		if(skinName!=null) {
			setSkin(skinName);
		}
	}

	public void prepareEmailURL() {
		System.out.println("action for open url");
	}

	public String getSkin() {
		if (skin == null) {
			skin = defaultSkin;
		}
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getImagesPath() {
		if(imagesPath == null){
			imagesPath = "/images";
		}
		return imagesPath;
	}
	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public String getCssPath() {
		if(cssPath == null){
			cssPath = "css";
		}
		return cssPath;
	}
	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getContextPath() {
		if(contextPath == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			contextPath = (String) facesContext.getExternalContext().getRequestContextPath();
		}
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getTagURL() {
		return tagURL;
	}
	public void setTagURL(String tagURL) {
		this.tagURL = tagURL;
	}
}
