package com.intrigueit.myc2i.scheduler.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

@Component("storyTriggerViewHandler")
@Scope("session")
public class StoryTriggerViewHandler {
	
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
