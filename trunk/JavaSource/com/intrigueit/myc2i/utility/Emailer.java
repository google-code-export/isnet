
package com.intrigueit.myc2i.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;


public class Emailer {
    /** mail configuration properties file*/
	private final String MAIL_CONFIG_FILE="/WEB-INF/mail.properties";
	/** email subject*/
	private String subject;
	private String messageBody;
	private String to;
	private String contentType;
	private String attachmentFilePath = null;
	
	protected static final Logger logger = Logger.getLogger( Emailer.class );
	
	public Emailer(String to, String messageBody, String subject){
		this.to = to;
		this.subject = subject;
		this.messageBody = messageBody;
		this.contentType = "text/plain";
	}
	public Emailer attachFile(String filePath){
		this.attachmentFilePath = filePath;
		return this;
	}
	private Properties loadProps(){
		Boolean setProperties = true;
		Properties props = new Properties();
		try{
			InputStream in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(MAIL_CONFIG_FILE);
			props.load(in);
		}catch(Exception ex){
			setProperties = false;
		}
		if (!setProperties) {
			try {				
				String filePath = ContextInfo.getContextRealPath() + MAIL_CONFIG_FILE;
				FileInputStream in = new FileInputStream(new File(filePath));
				props.load(in);
				setProperties = true;
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}		
		return props;
	}
	private void sendEmail() throws Exception
	{
		Properties conf = loadProps();
		String host = conf.getProperty("mail.host");
		String username = conf.getProperty("mail.user");
		String password = conf.getProperty("mail.password");
		
		Logger.getLogger(this.getClass()).debug("Info  host;"+host+" user: "+username+" pass: "+password);
		
		Properties props = new Properties();
		props.put("mail.smtps.auth", "true");
		Session session = Session.getDefaultInstance(props);
 
		MimeMessage msg = new MimeMessage(session);
		// set the message content here
		Transport transport = session.getTransport("smtps");

		msg.setContent(this.messageBody, this.contentType);
		transport.connect(host, username, password);
		Logger.getLogger(this.getClass()).warn("Before send message invocation.");
		msg.setSubject(this.subject);
		msg.setRecipients(Message.RecipientType.TO, this.to);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


		/**
	 * @return the messageBody
	 */
	public String getMessageBody() {
		return messageBody;
	}


	/**
	 * @param messageBody the messageBody to set
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}


	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}


	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the attachmentFilePath
	 */
	public String getAttachmentFilePath() {
		return attachmentFilePath;
	}

}
