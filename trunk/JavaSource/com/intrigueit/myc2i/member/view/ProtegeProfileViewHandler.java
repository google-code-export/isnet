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
import com.intrigueit.myc2i.zipcode.ZipCodeUtil;
import com.intrigueit.myc2i.zipcode.domain.ZipCode;
import com.intrigueit.myc2i.zipcode.service.ZipCodeService;

@Component("protegeProfileViewHandler")
@Scope("session")
public class ProtegeProfileViewHandler extends BasePage{
	private MemberService memberService;
	private MemberLogService logService;
	private ZipCodeService zipCodeService;
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
	public ProtegeProfileViewHandler(MemberService memberService, MemberLogService logService,ZipCodeService zipCodeService) {
		this.memberService = memberService;
		this.logService = logService;
		this.zipCodeService = zipCodeService;
		this.currentMember = new Member();
	}
	
	private void loadMentorAroundMe(){
		String clause = null;
		clause = " t.typeId =15";
		try{
			List<String> zipCodes = this.getZipCodes();
			if(zipCodes.size() > 0){
				String codes = "";
				for(String str: zipCodes){
					codes = codes.equals("")? str : codes + ","+ str;
				}
				clause = clause.equals("") ? clause+" t.zip in ("+codes +")" : clause+" and t.zip in ("+codes +")";
			}

			this.mentorsAraoundProtege = this.memberService.getMemberByDynamicHsql(clause);
		
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	private List<String> getZipCodes(){
		List<String> zipCodes = new ArrayList<String>();
		
		try{
			//String memberZipCode = this.getMember().getZip().toString();
			ZipCode srcZip = this.zipCodeService.findById(this.getMember().getZip()+"");
			List<ZipCode> desZipCodes = this.zipCodeService.findAll();
			ZipCodeUtil util = new ZipCodeUtil();
			for(ZipCode zip: desZipCodes){
				Double distance = util.getDistance(srcZip, zip);
				if(distance <= 20.0){
					log.debug("From: "+srcZip.getZipCode() + " Des: "+ zip.getZipCode()+" dis(M): "+ distance);
					zipCodes.add(zip.getZipCode());
				}
				
			}
		}
		catch(Exception ex){
			
		}
		return zipCodes;
	}
	
	public void releaseMentor(){
		String memberId = this.getParameter("MEMBER_ID");
		if(memberId == null || memberId.equals("")){
			return;
		}		
		try{
			Member mentor = this.memberService.findById(Long.parseLong(memberId));
			Member protege = this.memberService.findById(this.getMember().getMemberId());
			if(mentor == null){
				return;
			}
			
			protege.setMentoredByMemberId(null);
			this.memberService.update(protege);
			this.getSession().setAttribute(CommonConstants.SESSION_MEMBER_KEY, protege);
			
			this.createMentorReleaseLog(mentor);
			
			String msgBody = this.getText("email_mentor_release_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName()});
			String emailSubject = this.getText("email_mentor_release_subject");
			
			this.sendConfirmationEmail(mentor.getEmail(),msgBody,emailSubject);
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	
	private void createMentorReleaseLog(Member mentor){
		try{
			MemberLog log = new MemberLog();
			log.setFromMemberId(this.getMember().getMemberId());
			log.setToMemberId(mentor.getMemberId());
			log.setMemberActivityType(CommonConstants.ACTIVITY_TYPE_MENTOR_RELEASE);
			log.setTopic(this.getText("activity_log_release_mentor_sub"));
			log.setMemberLogEntryDescription(this.getText("activity_log_release_mentor_body"));
			
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
			
			String msgBody = this.getText("email_protege_release_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName()});
			String emailSubject = this.getText("email_protege_release_subject");
			
			this.sendConfirmationEmail(protege.getEmail(), msgBody,emailSubject);
			
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
	private void sendConfirmationEmail(String email,String msgBody,String emailSubject)throws Exception{
		
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
	}
	
	/** Load the selected member */
	public void loadMember(){
		try{
			String memberId = this.getParameter("MEMBER_ID");
			if(memberId == null || memberId.equals("")){
				return;
			}
			this.currentMember = this.memberService.findById(Long.parseLong(memberId));
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
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
		this.protegeCurrentMentor = new ArrayList<Member>();		
		try{
			this.protegeCurrentMentor.clear();
			Member me = this.memberService.findById(this.getMember().getMemberId());
			Member mem = this.memberService.findById(me.getMentoredByMemberId());
			this.protegeCurrentMentor.add(mem);
		}
		catch(Exception ex){
			log.error(ex.getMessage());
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
		if(mentorsAraoundProtege == null){
			this.loadMentorAroundMe();
		}
		
		return mentorsAraoundProtege;
	}

	public void setMentorsAraoundProtege(List<Member> mentorsAraoundProtege) {
		this.mentorsAraoundProtege = mentorsAraoundProtege;
	}

	private void loadLogs(){
		try{
			this.protegeLogs = this.logService.getMemberConversation(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public List<MemberLog> getProtegeLogs() {
		this.loadLogs();
		return protegeLogs;
	}

	public void setProtegeLogs(List<MemberLog> protegeLogs) {
		this.protegeLogs = protegeLogs;
	}
	
	
	
	
}
