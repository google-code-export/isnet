package com.intrigueit.myc2i.scheduler.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.service.MemberService;
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
			String email = this.getMyc2iAccountEmailAccount();
			log.debug(email);
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
