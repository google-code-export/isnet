package com.intrigueit.myc2i.media;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;
import com.intrigueit.myc2i.tutorialtest.service.TestResultService;



@Component("tutorialViewHandler")
@Scope("request")
public class TutorialViewHandler extends BasePage {
	
	private MemberService memberService;
	
	private TestResultService testResultService;
	  
	private boolean completedMentorTutorial;
	  
	public String navigateToPayment() {
		try{
			TestResult test = this.testResultService.loadUserModuleResult(this.getMember().getMemberId(),8L);
			if(test != null){
				//navigate to payment page
				return "PAY_NOW";
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		return "";
	}

	  @Autowired
	  public void setTestResultService(TestResultService testResltService) {
		this.testResultService = testResltService;
	  }
	  
	public boolean isCompletedMentorTutorial() {
		try{
			TestResult test = this.testResultService.loadUserModuleResult(this.getMember().getMemberId(),8L);
			if(test == null){
				this.completedMentorTutorial = false;
			}else{
				this.completedMentorTutorial = true;
			}
			log.debug("User completed all the mentor tutorials nnow, save this information");
			/** If the tutorial is completed then update the member */
			if(this.completedMentorTutorial){
				Member member = this.memberService.findById(this.getMember().getMemberId());
				member.setHasCompletedTutorial("YES");
				this.memberService.update(member);
			}
			
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());
			
			Calendar exp = Calendar.getInstance();
			if(this.getMember().getMemberShipExpiryDate()== null){
				exp.setTime(new Date());
				exp.set(Calendar.YEAR, 1900);
			}
			else{
				exp.setTime(this.getMember().getMemberShipExpiryDate());
			}
			
			
			/** Member did not paid subscription fee but completed tutorial */
			if(this.completedMentorTutorial && exp.before(today)){
				return true;
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}		
		return false;
	}

	public MemberService getMemberService() {
		return memberService;
	}
	
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
