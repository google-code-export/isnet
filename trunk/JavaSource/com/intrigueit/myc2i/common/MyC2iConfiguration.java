package com.intrigueit.myc2i.common;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.intrigueit.myc2i.utility.ContextInfo;

public class MyC2iConfiguration {
	
	private static MyC2iConfiguration config;
	
	private Properties props = new Properties();
	
	private static final String fileName = "WEB-INF"+ System.getProperty( "file.separator" ) +"app.properties";
	
	private static Logger log = Logger.getLogger(MyC2iConfiguration.class);

	private String appDomainURL;
	
	private String appDomainLabel;


	private MyC2iConfiguration() {
		this.init();
	}
	public static MyC2iConfiguration getInstance(){
		if(config == null){
			config = new MyC2iConfiguration();
		}
		return config;
	}
	/** Initialize the properties file*/	
	private void init(){
		try{
			String confFilePath = ContextInfo.getContextRealPath() + fileName;

			props.load(new FileInputStream(confFilePath));
			
			this.appDomainURL = getValue("app.domain");
			this.appDomainLabel = getValue("app.domain.label");

		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}		
	}
	private String getValue(String key){
		String value = props.getProperty(key); 
		return value;
	}
	public String getAppDomainURL() {
		return appDomainURL;
	}
	public String getAppDomainLabel() {
		return appDomainLabel;
	}
}
