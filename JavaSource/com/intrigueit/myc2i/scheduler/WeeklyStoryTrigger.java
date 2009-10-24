package com.intrigueit.myc2i.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("weeklyStoryTrigger")
@Scope("session")
public class WeeklyStoryTrigger extends QuartzJobBean {
	
	protected static final Logger log = Logger.getLogger( WeeklyStoryTrigger.class );
	/** Services ref */
	private UDValuesService udService;
	
	private StoryService storyService;
	
	private MemberService memberService;
	
	private String myc2iAccountEmailAccount;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		try{
			
			List<UserDefinedValues> values = this.udService.findByProperty("udValuesCategory", "MYC2I_ACCOUNT_EMAIL");
			if(values != null && values.size() > 0){
				this.myc2iAccountEmailAccount = values.get(0).getUdValuesValue();
			}			
			
			log.debug("Email: "+myc2iAccountEmailAccount);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	/** Send confirmation email to member */
	private void sendConfirmationEmail(String type, String email)
			throws Exception {

		String msgBody = "";
		String emailSubject = "";
		/** Send email notification */
		Emailer emailer = new Emailer(email, msgBody, emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();
	}
	
	public UDValuesService getUdService() {
		return udService;
	}
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	public StoryService getStoryService() {
		return storyService;
	}
	@Autowired
	public void setStoryService(StoryService storyService) {
		this.storyService = storyService;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public String getMyc2iAccountEmailAccount() {
		return myc2iAccountEmailAccount;
	}
	public void setMyc2iAccountEmailAccount(String myc2iAccountEmailAccount) {
		this.myc2iAccountEmailAccount = myc2iAccountEmailAccount;
	}	

}
