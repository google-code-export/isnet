package com.intrigueit.myc2i.dashboard.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

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

@Component("dashboardViewHandler")
@Scope("session")
public class DashboardViewHandler extends BasePage implements Serializable{

	/** Most voted story */
	private String mostVotedStory;
	
	/** Wining story */
	private String winningStory;
	
	/** Top ten stories */
	private List<MemberStory> topTenStories;
	
	/** Get all pending request of member */
	private List<MemberLog> pendingRequests;
	
	/** What new list */
	private List<UserDefinedValues> whatNewList;
	private List<UserDefinedValues> islamicTerms;
	private List<UserDefinedValues> importantLinks;
	
	/** Services ref */
	private StoryService storyService;
	private MemberLogService logService;
	private UDValuesService udService;
	private MemberService memberService;
	private List<MemberReport> mentorReport;
	private List<Member> idleMentors;

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
	
	private MemberLog updateRequestStatus(String status){
		String requestId = this.getParameter("REQUEST_ID");
		if(requestId == null || requestId.equals("")){
			return null;
		}		
		try{
			MemberLog request = this.logService.findById(Long.parseLong(requestId));
			request.setStatus(status);
			return request;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	private void updateProtege(Long protegeId){
		try{
			Member member = this.memberService.findById(protegeId);
			member.setMentoredByMemberId(this.getMember().getMemberId());
			this.memberService.update(member);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void acceptProtegeRequest(){
		MemberLog log = this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.ACCEPTED.toString());
		if(log != null){
			this.updateProtege(log.getRecordCreatorId());
		}
	}
	public void rejectProtegeRequest(){
		this.updateRequestStatus(CommonConstants.ACTIVITY_STATUS.REJECTED.toString());
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
		this.winningStory = "Winner: Ahmmed Ibne Jafer Quoting a witness from \"Balkh\", in his famous work entitled \"Kafi\", the celebrated scholar \"Kulayni\" relates the following ";
		
	}

	public String getMostVotedStory() {
		return mostVotedStory;
	}

	public void setMostVotedStory(String mostVotedStory) {
		this.mostVotedStory = mostVotedStory;
	}

	public String getWinningStory() {
		return winningStory;
	}

	public void setWinningStory(String winningStory) {
		this.winningStory = winningStory;
	}

	public List<MemberStory> getTopTenStories() {
		this.topTenStories = this.storyService.findTopTenStories();
		return topTenStories;
	}

	public void setTopTenStories(List<MemberStory> topTenStories) {
		this.topTenStories = topTenStories;
	}

	private void loadAllPendingRequest(){
		try{
			this.pendingRequests = this.logService.getAllPendingLog(this.getMember().getMemberId());
		}
		catch(Exception ex){
			ex.printStackTrace();
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
			this.populateMentorReports(this.getMember());
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

	
	
}
