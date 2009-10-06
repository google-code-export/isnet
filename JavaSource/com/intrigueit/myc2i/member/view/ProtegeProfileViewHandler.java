package com.intrigueit.myc2i.member.view;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("protegeProfileViewHandler")
@Scope("session")
public class ProtegeProfileViewHandler extends BasePage{
	private MemberService memberService;
	private MemberLogService logService;
	private Member currentMember;
	
	private List<Member> myProtegeList;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4277383197550797956L;
	
	@Autowired
	public ProtegeProfileViewHandler(MemberService memberService, MemberLogService logService) {
		this.memberService = memberService;
		this.logService = logService;
		this.currentMember = new Member();
	}
	
	public void releaseProtege(){
		String memberId = this.getParameter("MEMBER_ID");
		if(memberId == null || memberId.equals("")){
			return;
		}		
		try{
			Member protege = this.memberService.findById(Long.parseLong(memberId));
			if(protege == null){
				return;
			}
			
			protege.setMentoredByMemberId(null);
			this.memberService.update(protege);
			
			this.createReleaseLog(protege);
			
			this.sendConfirmationEmail(protege.getEmail(), this.getMember().getFirstName() +" "+ this.getMember().getLastName());
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	private void createReleaseLog(Member protege){
		try{
			MemberLog log = new MemberLog();
			log.setFromMemberId(this.getMember().getMemberId());
			log.setToMemberId(protege.getMemberId());
			log.setMemberActivityType(CommonConstants.ACTIVITY_TYPE_PROTEGE_RELEASE);
			log.setTopic(this.getText("activity_log_release_sub"));
			log.setMemberLogEntryDescription(this.getText("activity_log_release_body"));
			

			log.setRecordCreatorId(this.getMember().getMemberId()+"");
			log.setRecordCreatedDate(new Date());
			log.setRecordLastUpdatedDate(new Date());
			log.setRecordLastUpdaterId(this.getMember().getMemberId()+"");
			log.setStatus(CommonConstants.ACTIVITY_STATUS.COMPLETED.toString());
			
			Date dt = new Date();
			log.setMemberLogDateTime(new Timestamp(dt.getTime()));	
			
			this.logService.save(log);
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	/** Send release email to protege */
	private void sendConfirmationEmail(String email,String mentorName)throws Exception{
		
		String msgBody = this.getText("email_protege_release_body");
		String emailSubject = this.getText("email_protege_release_subject",new String[]{mentorName});
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
	}
	
	/** Load the selected member */
	public void loadMember(){
		String memberId = this.getParameter("MEMBER_ID");
		if(memberId == null || memberId.equals("")){
			return;
		}
		this.currentMember = this.memberService.findById(Long.parseLong(memberId));
	}
	
	private void loadMyProtege(){
		try{
			Long mentorId = this.getMember().getMemberId();
			this.myProtegeList = this.memberService.getMentorProtege(mentorId);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public Member getCurrentMember() {
		return currentMember;
	}

	public void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
	}

	public List<Member> getMyProtegeList() {
		this.loadMyProtege();
		return myProtegeList;
	}

	public void setMyProtegeList(List<Member> myProtegeList) {
		this.myProtegeList = myProtegeList;
	}
	
	
	
	
}
