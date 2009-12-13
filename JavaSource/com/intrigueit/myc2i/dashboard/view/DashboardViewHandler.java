package com.intrigueit.myc2i.dashboard.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.dashboard.report.MemberReport;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;
import com.intrigueit.myc2i.story.domain.MemberStory;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("dashboardViewHandler")
@Scope("session")
public class DashboardViewHandler extends BasePage implements Serializable{

	private static final long serialVersionUID = -418368951400766088L;

	/** Most voted story */
	private String mostVotedStory;
	
	/** Wining story */
	private String winningStory;
	private String winner;
	
	/** Top ten stories */
	private List<MemberStory> topTenStories;
	
	/** Get all pending request of member */
	private List<MemberLog> pendingRequests;
	private List<MemberLog> protegePendingActivity;
	private List<MemberLog> protegePendingMentorRequests;
	
	private List<MemberLog> mentorPendingActivity;
	private List<MemberLog> mentorPendingProtegerRequests;
	private List<MemberLog> mentorPendingMentorRequests;
	
	/** What new list */
	private List<UserDefinedValues> whatNewList;
	private List<UserDefinedValues> islamicTerms;
	private List<UserDefinedValues> importantLinks;
	
	private List<Member> mentorsAraoundProtege;
	
	/** Services ref */
	private StoryService storyService;
	private MemberLogService logService;
	private UDValuesService udService;
	private MemberService memberService;

	private boolean showReportDetails;
	
	private List<MemberReport> mentorReport;
	private List<Member> idleMentors;
	private String note;
	private String logId;
	private String action;

	/** Send release email to protege */
	private void sendConfirmationEmail(String email,String msgBody,String emailSubject)throws Exception{
		
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
	}
	
	private void populateIdleMentorList(){
		this.idleMentors = new ArrayList<Member>();
		if(this.mentorReport == null){
			this.getMentorReport();
		}
		for(MemberReport report: mentorReport){
			if(this.logService.isInActiveMember(report.getMentor().getMemberId())){
				this.idleMentors.add(report.getMentor());
			}
		}
		
	}
	private void populateMentorReports(Member leadMentor){
		this.mentorReport = new ArrayList<MemberReport>();
		
		List<Member> members = this.memberService.getMentorProtege(leadMentor.getMemberId());
		for(Member mem: members){
			MemberReport report = new MemberReport();
			report.setMentor(mem);
			List<Member> proteges = this.memberService.getMentorProtege(mem.getMemberId());
			report.setProteges(proteges);
			this.mentorReport.add(report);
		}
	}
	private void populateProtegeMentorReports(Member leadMentor){
		this.mentorReport = new ArrayList<MemberReport>();
		
		MemberReport report = new MemberReport();
		if(leadMentor == null){
			if(this.getMember().getMentoredByMemberId() != null){
				Member member = this.memberService.findById(this.getMember().getMentoredByMemberId());
				report.setMentor(member);				
			}

		}
		else{
			report.setMentor(leadMentor);
			List<Member> members = this.memberService.getMentorProtege(leadMentor.getMemberId());
			report.setProteges(members);
		}
		this.mentorReport.add(report);

	}
	private Member getProtegeTopLevelMentor(Member member){
		
		if(member.getMentoredByMemberId() != null){
			Member mentor = this.memberService.findById(member.getMentoredByMemberId());
			if(mentor != null && mentor.getMentoredByMemberId() != null){
				Member leadMentor =this.memberService.findById(mentor.getMentoredByMemberId());
				if(leadMentor != null){
					return leadMentor;
				}
			}
		}
		return null;
	}
	
	private MemberLog updateRequestStatus(String status){
		
		String requestId = this.logId;//this.getParameter("REQUEST_ID");
		if(requestId == null || requestId.equals("")){
			return null;
		}		
		try{
			MemberLog request = this.logService.findById(Long.parseLong(requestId));
			request.setStatus(status);
			request.setRecordLastUpdaterId(this.getMember().getMemberId()+"");
			request.setRecordLastUpdatedDate(new Date());
			
			this.logService.update(request);
			return request;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	private void updateProtege(Long protegeId,Long mentorId){
		try{
			Member member = this.memberService.findById(protegeId);
			member.setMentoredByMemberId(mentorId);
			this.memberService.update(member);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void execute(){
		String act = this.action;
		if(act == null || act.equals("")){
			return;
		}		
		MemberLog mLog = this.getLog();
		
		try{
			if(act.equals("1")){
				if(mLog.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase())){
					this.acceptMentorRequest();
				}
				else if(mLog.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_PROTEGE_REQUEST.toLowerCase())){
					this.acceptProtegeRequest();
				}
				else{
					this.acceptOtherRequest();
				}				
			}
			if(act.equals("2")){
				
				this.rejectRequest();
			}			

		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}

	private MemberLog getLog(){
		String requestId = this.logId;
		if(requestId == null || requestId.equals("")){
			return null;
		}		
		try{
			return this.logService.findById(Long.parseLong(requestId));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	public void acceptOtherRequest(){
		try{

			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.ACCEPTED.toString());
			
	
			String msgBody = this.getText("email_other_request_accept_body",new String[]{ log.getUserDefinedValues().getUdValuesDesc(),this.getMember().getFirstName() +" "+ this.getMember().getLastName(), this.getNote()});
			String emailSubject = this.getText("email_other_request_subject", new String[]{ log.getUserDefinedValues().getUdValuesDesc()});
			
			this.sendConfirmationEmail(log.getFromMember().getEmail(), msgBody,emailSubject);
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	public void acceptMentorRequest(){
		try{

			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.ACCEPTED.toString());
			
			int memberProtege = this.memberService.getMentorProtegeCout(log.getFromMemberId());
			if (memberProtege >= 5) return;
			
			if(log != null){
				this.updateProtege(log.getToMemberId(),log.getFromMemberId());
			}		
			String msgBody = this.getText("email_mentor_request_accept_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName(), this.getNote()});
			String emailSubject = this.getText("email_mentor_request_accepted_subject");
			
			this.sendConfirmationEmail(log.getFromMember().getEmail(), msgBody,emailSubject);
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	public void acceptProtegeRequest(){
		try{
			int memberProtege = this.memberService.getMentorProtegeCout(this.getMember().getMemberId());
			if (memberProtege >= 5) return;
			
			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.ACCEPTED.toString());
			if(log != null){
				this.updateProtege(log.getFromMemberId(),log.getToMemberId());
			}		
			String msgBody = this.getText("email_protege_request_accept_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName(),this.getNote()});
			String emailSubject = this.getText("email_protege_request_accepted_subject");
			this.log.debug(log.getFromMember().getEmail());
			this.sendConfirmationEmail(log.getFromMember().getEmail(), msgBody,emailSubject);			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	
	}
	public void rejectRequest(){
		try{
			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.REJECTED.toString());
			
			String msgBody = this.getText("email_request_reject_body",new String[]{log.getUserDefinedValues().getUdValuesDesc(), this.getMember().getFirstName() +" "+ this.getMember().getLastName(),this.getNote()});
			String emailSubject = this.getText("email_request_rejected_subject", new String[]{log.getUserDefinedValues().getUdValuesDesc()});
			
			this.sendConfirmationEmail(log.getFromMember().getEmail(), msgBody,emailSubject);					
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	public void rejectProtegeRequest(){
		try{
			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.REJECTED.toString());
			
			String msgBody = this.getText("email_protege_request_accept_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName(),this.getNote()});
			String emailSubject = this.getText("email_protege_request_accepted_subject");
			
			this.sendConfirmationEmail(log.getToMember().getEmail(), msgBody,emailSubject);					
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public void rejectMentorRequest(){
		try{
			MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.REJECTED.toString());
			
			String msgBody = this.getText("email_mentor_request_reject_body",new String[]{ this.getMember().getFirstName() +" "+ this.getMember().getLastName(),this.getNote()});
			String emailSubject = this.getText("email_mentor_request_rejected_subject");
			
			this.sendConfirmationEmail(log.getToMember().getEmail(), msgBody,emailSubject);					
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	public StoryService getStoryService() {
		return storyService;
	}
	@Autowired
	public void setStoryService(StoryService storyService) {
		this.storyService = storyService;
	}
	public UDValuesService getUdService() {
		return udService;
	}
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	public MemberLogService getLogService() {
		return logService;
	}
	@Autowired
	public void setLogService(MemberLogService logService) {
		this.logService = logService;
	}	
	public MemberService getMemberService() {
		return memberService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}	
	
	/**
	 * 
	 */
	public DashboardViewHandler() {
		this.mostVotedStory = "Quoting a witness from \"Balkh\", in his famous work entitled \"Kafi\", the celebrated scholar \"Kulayni\" relates the following";
		this.winningStory = "Quoting a witness from \"Balkh\", in his famous work entitled \"Kafi\", the celebrated scholar \"Kulayni\" relates the following ";
		this.winner = "Winner: Ahmmed Ibne Jafer";
	}
	private void loadWinningStroy(){
		try{
			String type = "MENTOR";
			if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
				type = "PROTEGE";
			}
			MemberStory winStory = this.storyService.getWiningStory(type);
			if(winStory != null){
				this.winningStory = winStory.getMemberStoryDescription();
				this.winner = "Winner:"+ winStory.getMember().getFirstName() +" "+ winStory.getMember().getLastName();
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public String getMostVotedStory() {
		return mostVotedStory;
	}

	public void setMostVotedStory(String mostVotedStory) {
		this.mostVotedStory = mostVotedStory;
	}

	public String getWinningStory() {
		this.loadWinningStroy();
		return winningStory;
	}

	public void setWinningStory(String winningStory) {
		this.winningStory = winningStory;
	}

	public List<MemberStory> getTopTenStories() {
		try{
			String type = "MENTOR";
			if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
				type = "PROTEGE";
			}			
			this.topTenStories = this.storyService.findTopTenStories(type);
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		return topTenStories;
	}

	public void setTopTenStories(List<MemberStory> topTenStories) {
		this.topTenStories = topTenStories;
	}

	private void loadAllPendingRequest(){
		try{
			this.pendingRequests = this.logService.getAllProtegePendingRequest(this.getMember().getMemberId());
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public List<MemberLog> getPendingRequests() {
		this.loadAllPendingRequest();
		return pendingRequests;
	}

	public void setPendingRequests(List<MemberLog> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	private void initWhatNewList(){
		try{
			this.whatNewList = this.udService.findByProperty("udValuesCategory", "WHAT_NEW");
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public List<UserDefinedValues> getWhatNewList() {
		this.initWhatNewList();
		return whatNewList;
	}
	
	public void setWhatNewList(List<UserDefinedValues> whatNewList) {
		this.whatNewList = whatNewList;
	}

	private void initIslamicTerms(){
		try{
			this.islamicTerms = this.udService.findByProperty("udValuesCategory", "ISLAMIC_TERM");
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public List<UserDefinedValues> getIslamicTerms() {
		this.initIslamicTerms();
		return islamicTerms;
	}

	public void setIslamicTerms(List<UserDefinedValues> islamicTerms) {
		this.islamicTerms = islamicTerms;
	}
	private void initImportantLinks(){
		try{
			this.importantLinks = this.udService.findByProperty("udValuesCategory", "IMPORTANT_LINK");
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public List<UserDefinedValues> getImportantLinks() {
		this.initImportantLinks();
		return importantLinks;
	}

	public void setImportantLinks(List<UserDefinedValues> importantLinks) {
		this.importantLinks = importantLinks;
	}

	public List<MemberReport> getMentorReport() {
		try{
			if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
				Member top = this.getProtegeTopLevelMentor(this.getMember());
				this.populateProtegeMentorReports(top);
				
			}else{
				this.populateMentorReports(this.getMember());
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return mentorReport;
	}

	public void setMentorReport(List<MemberReport> mentorReport) {
		this.mentorReport = mentorReport;
	}
	
	public List<Member> getIdleMentors() {
		try{
			this.populateIdleMentorList();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return idleMentors;
	}
	
	public void setIdleMentors(List<Member> idleMentors) {
		this.idleMentors = idleMentors;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public List<MemberLog> getProtegePendingActivity() {
		try{
			this.protegePendingActivity = this.logService.getProtegePendingActivities(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getCause());
		}
		return protegePendingActivity;
	}

	public void setProtegePendingActivity(List<MemberLog> protegePendingActivity) {
		this.protegePendingActivity = protegePendingActivity;
	}

	public List<MemberLog> getProtegePendingMentorRequests() {
		try{
			this.protegePendingMentorRequests= this.logService.getProtegePendingMentorRequests(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getCause());
		}		
		return protegePendingMentorRequests;
	}

	public void setProtegePendingMentorRequests(List<MemberLog> protegePendingMentorRequests) {
		this.protegePendingMentorRequests = protegePendingMentorRequests;
	}

	public boolean isShowReportDetails() {
		showReportDetails = (this.getMember().getMentoredByMemberId() == null) ? false: true;
		return showReportDetails;
	}

	public void setShowReportDetails(boolean showReportDetails) {
		this.showReportDetails = showReportDetails;
	}

	public List<MemberLog> getMentorPendingActivity() {
		try{
			this.mentorPendingActivity = this.logService.getMentorPendingActivities(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getCause());
		}		
		return mentorPendingActivity;
	}

	public void setMentorPendingActivity(List<MemberLog> mentorPendingActivity) {
		this.mentorPendingActivity = mentorPendingActivity;
	}

	public List<MemberLog> getMentorPendingProtegerRequests() {
		try{
			this.mentorPendingProtegerRequests = this.logService.getMentorPendingProtegeRequests(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getCause());
		}			
		return mentorPendingProtegerRequests;
	}

	public void setMentorPendingProtegerRequests(
			List<MemberLog> mentorPendingProtegerRequests) {
		this.mentorPendingProtegerRequests = mentorPendingProtegerRequests;
	}

	public List<MemberLog> getMentorPendingMentorRequests() {
		try{
			this.mentorPendingMentorRequests = this.logService.getMentorPendingMentorRequests(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getCause());
		}		
		return mentorPendingMentorRequests;
	}

	public void setMentorPendingMentorRequests(
			List<MemberLog> mentorPendingMentorRequests) {
		this.mentorPendingMentorRequests = mentorPendingMentorRequests;
	}


	
	
}
