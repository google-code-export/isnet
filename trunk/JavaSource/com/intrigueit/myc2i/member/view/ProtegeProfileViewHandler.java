package com.intrigueit.myc2i.member.view;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	
	private List<Member> protegeCurrentMentor;
	private List<Member> previousMentor;
	private List<Member> mentorsAraoundProtege;
	private List<MemberLog> protegeLogs;
	
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
		
		String msgBody = this.getText("email_protege_release_body",new String[]{mentorName});
		String emailSubject = this.getText("email_protege_release_subject");
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

	public List<Member> getProtegeCurrentMentor() {
		try{
			Member mem = this.memberService.findById(this.getMember().getMemberId());
			
			this.protegeCurrentMentor = new ArrayList<Member>();
			this.protegeCurrentMentor.add(mem);
		}
		catch(Exception ex){
			log.debug(ex.getMessage());
		}
		return protegeCurrentMentor;
	}

	public void setProtegeCurrentMentor(List<Member> protegeCurrentMentor) {
		this.protegeCurrentMentor = protegeCurrentMentor;
	}

	private List<String> getMentorsId(){
		List<String> ids = new ArrayList<String>();
		try{
			List<MemberLog> logs1 = this.logService.getAllProtegeReleaseLog(this.getMember().getMemberId());
			List<MemberLog> logs2 = this.logService.getAllMentorReleaseLog(this.getMember().getMemberId());
			for(MemberLog log : logs1){
				ids.add(log.getFromMemberId().toString());
			}
			for(MemberLog log: logs2){
				ids.add(log.getToMemberId().toString());
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return ids;
	}
	private void loadPreviousMentors(){
		try{
			this.previousMentor = this.memberService.findMentorByIds(this.getMentorsId());
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public List<Member> getPreviousMentor() {
		this.loadPreviousMentors();
		return previousMentor;
	}

	public void setPreviousMentor(List<Member> previousMentor) {
		this.previousMentor = previousMentor;
	}

	public List<Member> getMentorsAraoundProtege() {
		return mentorsAraoundProtege;
	}

	public void setMentorsAraoundProtege(List<Member> mentorsAraoundProtege) {
		this.mentorsAraoundProtege = mentorsAraoundProtege;
	}

	public List<MemberLog> getProtegeLogs() {
		return protegeLogs;
	}

	public void setProtegeLogs(List<MemberLog> protegeLogs) {
		this.protegeLogs = protegeLogs;
	}
	
	
	
	
}
