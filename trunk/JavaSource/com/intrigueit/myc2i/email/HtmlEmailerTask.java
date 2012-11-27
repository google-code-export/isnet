package com.intrigueit.myc2i.email;

import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.utility.Emailer;

public class HtmlEmailerTask extends EmailerTask{

	public HtmlEmailerTask(final Emailer emailer) {
		super(emailer);
	}

	@Override
	public void run() {
		try{
			Properties conf = EmailConfiguration.getEmailConfiguration().getEmailProperty();
			String port = conf.getProperty("mail.port");
			String host = conf.getProperty("mail.host");
			String username = conf.getProperty("mail.user");
			String password = conf.getProperty("mail.password");
			String from = conf.getProperty("mail.from");
			

			password = CryptographicUtility.getInstance().getDeccryptedText(password);
			
		    HtmlEmail email = new HtmlEmail();
		    email.setHostName(host);
		    email.addTo(this.emailer.getTo());
		    email.setFrom(from, "MyC2i Team");
		    email.setSubject(this.emailer.getSubject());

		    // set the html message
		    email.setHtmlMsg(this.emailer.getMessageBody());

		    // set the alternative message
		    email.setTextMsg("Your email client does not support HTML messages");

		    email.setSmtpPort(Integer.parseInt(port));
	        email.setAuthenticator(new DefaultAuthenticator(username, password));
		    email.setDebug(false);		
		    email.setTLS(false);

		    email.send();


		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage(),ex);
		}
	}

}
