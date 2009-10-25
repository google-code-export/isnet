package com.intrigueit.myc2i.scheduler.view;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.story.domain.MemberStory;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("storyTriggerViewHandler")
public class StoryTriggerViewHandler extends BasePage{
	
	/** Services ref */
	private UDValuesService udService;
	
	private StoryService storyService;
	
	private MemberService memberService;
	
	private String myc2iAccountEmailAccount;
	
	private MemberStory mentorStory;
	private MemberStory protegeStory;
	
	
	
	/**
	 * @param udService
	 * @param storyService
	 * @param memberService
	 */
	@Autowired
	public StoryTriggerViewHandler(UDValuesService udService,
			StoryService storyService, MemberService memberService) {
		this.udService = udService;
		this.storyService = storyService;
		this.memberService = memberService;
	}

	public void execute(){
		try{
			String accountEmail = this.getMyc2iAccountEmailAccount();
			log.debug("Calling scheduler....");
			
			this.mentorStory = this.storyService.getWeeklyMentorWiningStory();
			if(this.mentorStory != null){
				this.mentorStory.setWeekWinnerIndicator(new Date());
				this.storyService.update(this.mentorStory);
				
				log.debug(this.mentorStory.getStoryTitle());
				
				/** Send member email */
				String sub = "MyC2i weekly story competition winning notification";
				String winner = this.mentorStory.getMember().getFirstName()+" "+ this.mentorStory.getMember().getLastName();
				String body = "MyC2i weekly story competition result: <br/><br/>Winner Name: "+ winner +" <br/><br/>Prize Money:<br/><br/>$500<br/><br/>Thanks <br/>MyC2i support team.";

				this.sendConfirmationEmail(sub, body, this.mentorStory.getMember().getEmail());
				/** Send account email */
				this.sendConfirmationEmail(sub, body, accountEmail);
			}

			this.protegeStory = this.storyService.getWeeklyProtegeWiningStory();
			if(this.protegeStory != null){
				this.protegeStory.setWeekWinnerIndicator(new Date());
				this.storyService.update(this.protegeStory);
				log.debug(this.protegeStory.getStoryTitle());
				
				/** Send member email */
				String sub = "MyC2i weekly story competition winning notification";
				String winner = this.protegeStory.getMember().getFirstName()+" "+ this.protegeStory.getMember().getLastName();
				String body = "MyC2i weekly story competition result: <br/><br/>Winner Name: "+ winner +" <br/><br/>Prize Money:<br/><br/>$500<br/><br/>Thanks <br/>MyC2i support team.";

				this.sendConfirmationEmail(sub, body, this.protegeStory.getMember().getEmail());
				/** Send account email */
				this.sendConfirmationEmail(sub, body, accountEmail);			
				
			}

			
			

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/** Send confirmation email to member */
	private void sendConfirmationEmail(String emailSubject,String msgBody, String email)
			throws Exception {

		/** Send email notification */
		Emailer emailer = new Emailer(email, msgBody, emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();
	}
	public String getMyc2iAccountEmailAccount() {
		try{
			List<UserDefinedValues> values = this.udService.findByProperty("udValuesCategory", "MYC2I_ACCOUNT_EMAIL");
			if(values != null && values.size() > 0){
				this.myc2iAccountEmailAccount = values.get(0).getUdValuesValue();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}		
		return myc2iAccountEmailAccount;
	}

	public void setMyc2iAccountEmailAccount(String myc2iAccountEmailAccount) {
		this.myc2iAccountEmailAccount = myc2iAccountEmailAccount;
	}
}
