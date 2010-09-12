package com.intrigueit.myc2i.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.intrigueit.myc2i.utility.ContextInfo;

public class EmailConfiguration {

    /** mail configuration properties file*/
	private static final String MAIL_CONFIG_FILE="WEB-INF"+ System.getProperty( "file.separator" ) +"email.properties";
	
	private static EmailConfiguration config;
	
	protected static final Logger logger = Logger.getLogger( EmailConfiguration.class );
	
	private Properties emailProperty;
	
	public static EmailConfiguration getEmailConfiguration(){
		if(config == null){
			config = new EmailConfiguration();
		}
		return config;
	}
	private EmailConfiguration() {
		loadProps();
	}
	
	private Properties loadProps(){
		Boolean setProperties = true;
		emailProperty = new Properties();
		try{
			InputStream in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(MAIL_CONFIG_FILE);
			emailProperty.load(in);
		}catch(Exception ex){
			setProperties = false;
		}
		if (!setProperties) {
			try {				
				String filePath = ContextInfo.getContextRealPath() + MAIL_CONFIG_FILE;
				FileInputStream in = new FileInputStream(new File(filePath));
				emailProperty.load(in);
				setProperties = true;
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}		
		return emailProperty;
	}
	public Properties getEmailProperty() {
		return emailProperty;
	}
}
