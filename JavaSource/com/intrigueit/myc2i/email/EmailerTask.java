package com.intrigueit.myc2i.email;



import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.intrigueit.myc2i.utility.Emailer;

public class EmailerTask implements Runnable{

	protected Emailer emailer;
	
	protected static final Logger logger = Logger.getLogger( EmailerTask.class );
	
	public EmailerTask(final Emailer emailer) {
		this.emailer = emailer;
	}


	public void run() {
		try{
			Properties conf = EmailConfiguration.getEmailConfiguration().getEmailProperty();
			String port = conf.getProperty("mail.port");
			String host = conf.getProperty("mail.host");
			String username = conf.getProperty("mail.user");
			String password = conf.getProperty("mail.password");
			String from = conf.getProperty("mail.from");
			
			Email email = new SimpleEmail();
			email.setSmtpPort(Integer.parseInt(port));
			email.setAuthenticator(new DefaultAuthenticator(username, password));
			email.setDebug(false);
			email.setHostName(host);
			email.setFrom(from);
			email.setSubject(this.emailer.getSubject());
			email.setMsg(this.emailer.getMessageBody());
			email.addTo(this.emailer.getTo());
			email.setTLS(true);
			email.send();

		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage(),ex);
		}
		
	}

}
