package com.intrigueit.myc2i.dashboard.view;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;
import com.intrigueit.myc2i.tutorialtest.service.TestResultService;

@Component("memberToDoItem")
@Scope("session")
public class MemberToDoItem extends BasePage{

	private boolean isMentor;
	
	private boolean isCompletedMentorTutorial;
	
	private boolean isCompletedProtegeTutorial;
	
	private boolean isPaidSubscription;
	
	private boolean isMentorCertified;
	
	private boolean hasProtege;
	
	/** Services ref */
	private StoryService storyService;
	private MemberService memberService;
	private TestResultService testResultService;
	
	
	@Autowired
	public MemberToDoItem(StoryService storyService, MemberService memberService,TestResultService testResultService) {
		super();
		this.storyService = storyService;
		this.memberService = memberService;
		this.testResultService = testResultService;
	}

	public boolean isMentor() {
		if(CommonConstants.MENTOR.equals(this.getMember().getTypeId())){
			return true;
		}
		return false;
	}

	public void setMentor(boolean isMentor) {
		this.isMentor = isMentor;
	}

	public boolean isCompletedMentorTutorial() {
		try{
			TestResult test = this.testResultService.loadUserModuleResult(this.getMember().getMemberId(),8L);
			if(test == null){
				this.isCompletedMentorTutorial= false;
			}else{
				this.isCompletedMentorTutorial = true;
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}		
		return isCompletedMentorTutorial;
	}

	public void setCompletedMentorTutorial(boolean isCompletedMentorTutorial) {
		this.isCompletedMentorTutorial = isCompletedMentorTutorial;
	}

	public boolean isCompletedProtegeTutorial() {
		
		try{
			TestResult test = this.testResultService.loadUserModuleResult(this.getMember().getMemberId(),9L);
			if(test == null){
				this.isCompletedProtegeTutorial= false;
			}else{
				this.isCompletedProtegeTutorial = true;
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}		
		return isCompletedProtegeTutorial;
	}

	public void setCompletedProtegeTutorial(boolean isCompletedProtegeTutorial) {
		this.isCompletedProtegeTutorial = isCompletedProtegeTutorial;
	}

	public boolean isPaidSubscription() {
		try{
			Member member = this.memberService.findById(this.getMember().getMemberId());
			Date expiry = member.getMemberShipExpiryDate();
			if(expiry == null){
				return false;
			}
			Date now = new Date();
			if(expiry.before(now)){
				this.isPaidSubscription = false;
			}
			else{
				this.isPaidSubscription = true;
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}			
		return isPaidSubscription;
	}

	public void setPaidSubscription(boolean isPaidSubscription) {
		this.isPaidSubscription = isPaidSubscription;
	}

	public boolean isMentorCertified() {
		try{
			Member member = this.memberService.findById(this.getMember().getMemberId());
			if(member == null){
				this.isMentorCertified = false;
			}else{
				this.isMentorCertified = member.getIsMemberCertified().equals("YES");
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}			
		return isMentorCertified;
	}

	public void setMentorCertified(boolean isMentorCertified) {
		this.isMentorCertified = isMentorCertified;
	}

	public boolean isHasProtege() {
		int protegeCount = this.memberService.getMentorProtegeCout(this.getMember().getMemberId());
		this.hasProtege =  protegeCount >= 5;
		return this.hasProtege;
	}

	public void setHasProtege(boolean hasProtege) {
		this.hasProtege = hasProtege;
	}
	
	
	
	
}
