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
import com.intrigueit.myc2i.membersearch.dao.MemberSearchDao;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;
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
	private MemberSearchDao memberSearchDao;
	private UDValuesService udService;
	
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
		clause = " t.typeId ="+CommonConstants.MENTOR+"";
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
			ZipCode srcZip = this.zipCodeService.findById(this.getMember().getZip()+"");
			zipCodes = this.memberSearchDao.fetchZipCode(srcZip.getLatitude(), srcZip.getLongitude(), 20.0);
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		return zipCodes;
	}
	
	/**
	 * create mentor release by protege
	 */
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
			
			MemberLog log =  this.createMentorReleaseLog(mentor);
			if( log != null){
				String msgBody = this.getText("email_mentor_release_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName()});
				String emailSubject = this.getText("email_mentor_release_subject");
				this.sendConfirmationEmail(mentor.getEmail(),msgBody,emailSubject);				
			}

			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}

	private UserDefinedValues getMyProtegeActivityUserDefinedValue(){
		List<UserDefinedValues> types = udService.findByProperty("udValuesValue", CommonConstants.ACTIVITY_TYPE_PROTEGE_RELEASE);
		for(UserDefinedValues val: types){
			if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
				if(val.getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_PROTEGE)){
					return val;
				}
			}
		}
		return null;
	}
	private UserDefinedValues getMyMentorActivityUserDefinedValue(){
		List<UserDefinedValues> types = udService.findByProperty("udValuesValue", CommonConstants.ACTIVITY_TYPE_MENTOR_RELEASE);
		for(UserDefinedValues val: types){
			if(this.getMember().getTypeId().equals(CommonConstants.MENTOR)){
				if(val.getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_MENTOR)){
					return val;
				}
			}
		}
		return null;
	}	
	
	private MemberLog createMentorReleaseLog(Member mentor){
		try{
			
			Long mentorReleaseTypeId = getMyProtegeActivityUserDefinedValue().getUdValuesId();
					
			MemberLog log = new MemberLog();
			log.setFromMemberId(this.getMember().getMemberId());
			log.setToMemberId(mentor.getMemberId());
			log.setMemberActivityType(mentorReleaseTypeId);
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
			return log;
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return null;
	}
	/**
	 * Release protege by mentor
	 */
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
			
			MemberLog log = this.createProtegeReleaseLog(protege);
			if(log != null){
				String msgBody = this.getText("email_protege_release_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName()});
				String emailSubject = this.getText("email_protege_release_subject");
				this.sendConfirmationEmail(protege.getEmail(), msgBody,emailSubject);
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	private MemberLog createProtegeReleaseLog(Member protege){
		try{
			
			Long mentorReleaseTypeId = getMyMentorActivityUserDefinedValue().getUdValuesId();
						
			MemberLog log = new MemberLog();
			log.setFromMemberId(this.getMember().getMemberId());
			log.setToMemberId(protege.getMemberId());
			log.setMemberActivityType(mentorReleaseTypeId);
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
			return log;
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return null;
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
			if(me.getMentoredByMemberId()!= null){
				Member mem = this.memberService.findById(me.getMentoredByMemberId());
				this.protegeCurrentMentor.add(mem);
			}
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
			Long protegeReleaseTypeId = this.udService.getUDValue("udValuesValue", CommonConstants.ACTIVITY_TYPE_PROTEGE_RELEASE).getUdValuesId();
			//Long mentorReleaseTypeId = this.udService.getUDValue("udValuesValue", CommonConstants.ACTIVITY_TYPE_MENTOR_RELEASE).getUdValuesId();
			
			String typIds = "select t from "+ UserDefinedValues.class.getName() +" t where upper(t.udValuesValue)='"+CommonConstants.ACTIVITY_TYPE_MENTOR_RELEASE.toUpperCase()+"' and upper(t.udValuesCategory)='"+ CommonConstants.ACTIVITY_LOG_MENTOR+"'";
			
			Long mentorReleaseTypeId = this.udService.getUDValues(typIds).get(0).getUdValuesId();
			
			List<MemberLog> logs1 = this.logService.getAllProtegeReleaseLog(this.getMember().getMemberId(),protegeReleaseTypeId);
			List<MemberLog> logs2 = this.logService.getAllMentorReleaseLog(this.getMember().getMemberId(),mentorReleaseTypeId);
			for(MemberLog log : logs1){
				ids.add(log.getToMemberId().toString());
			}
			for(MemberLog log: logs2){
				ids.add(log.getFromMemberId().toString());
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
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

	public MemberSearchDao getMemberSearchDao() {
		return memberSearchDao;
	}
	
	@Autowired
	public void setMemberSearchDao(MemberSearchDao memberSearchDao) {
		this.memberSearchDao = memberSearchDao;
	}

	public UDValuesService getUdService() {
		return udService;
	}
	
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	
	
	
	
}
