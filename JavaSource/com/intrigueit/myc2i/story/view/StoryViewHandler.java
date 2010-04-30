package com.intrigueit.myc2i.story.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.story.domain.MemberStory;
import com.intrigueit.myc2i.story.service.StoryService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;


@Component("storyViewHandler")
@Scope("session")
public class StoryViewHandler extends BasePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6745509962650776624L;
	
	private static  final String ID_PRM = "storyId";
	private static final String ACTION_PRM = "action";
	
	private List<String> curseWords;
	

	private StoryService storyService;
	
	private UDValuesService udService;
	
	private List<MemberStory> storyList;
	
	private List<MemberStory> adminStoryList;
	
	private MemberStory currentStory;
	
	private String ACTION = "";
	
	private String ddDay = "0";
	
	private List<MemberStory> voteStoryList;
	private Boolean hasError;
	
	private int currStoryIndex;
	
	

	@Autowired
	public StoryViewHandler(StoryService storyService,UDValuesService udService) {
		this.storyService = storyService;
		this.udService = udService;
		this.currentStory = new MemberStory();
		this.currStoryIndex = 0;
	}
	
	public void previous(){
		try{
			this.currStoryIndex -= 5;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}		
	}
	public void next(){
		try{
			this.currStoryIndex += 5;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public void approveStory(){
		String storyId = this.getParameter("storyId");
		if(storyId == null){
			return;
		}	
		String action = this.getParameter("action");
		if(action == null){
			return;
		}
		try{
			if(action.equals("APPROVE")){
				MemberStory story = this.storyService.findById(Long.parseLong(storyId));
				story.setApprovedForPublishInd("Yes");
				story.setApprovalDate(new Date());
				story.setRecordLastUpdatedDate(new Date());
				story.setRecordLastUpdaterId(this.getMember().getMemberId()+"");
				story.setScreenedByMemberId(this.getMember().getMemberId()+"");
				this.storyService.update(story);
			}
			if(action.equals("REJECT")){
				MemberStory story = this.storyService.findById(Long.parseLong(storyId));
				story.setApprovedForPublishInd("No");
				story.setMemberPermissionToPublish("No");
				story.setRecordLastUpdatedDate(new Date());
				story.setRecordLastUpdaterId(this.getMember().getMemberId()+"");
				this.storyService.update(story);
			}			
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public void voteForStory(){
		String storyId = this.getParameter("storyId");
		if(storyId == null){
			return;
		}
		try{
			
			MemberStory story = this.storyService.findById(Long.parseLong(storyId));
			if(hasVotingRight(story)){
				long voteCount = 1;
				if(story.getNumberOfVotesReceived() != null){
					voteCount = story.getNumberOfVotesReceived()+1;
				}
				story.setNumberOfVotesReceived(voteCount);
				this.storyService.update(story);
				String cookie = this.getCookieName(story);
				this.storeCokie(cookie, "1");
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	
	private String getCookieName(MemberStory story){
		return this.getMember().getMemberId()+""+ story.getMemberStoryId()+"";
	}
	
	private Boolean hasVotingRight(MemberStory story){
		String cookieName = getCookieName(story);
		String cookieValue = this.getCookieValue(cookieName);
		if(cookieValue == null || !cookieValue.equals("1")){
			return true;
		}
		
		return false;

	}
	private void loadVoteStoryList(){

		try{

			UserDefinedValues dayFrom = this.udService.getUDValue("udValuesCategory", "STORY_RANGE");
			
			int range = CommonValidator.isEmpty(dayFrom.getUdValuesValue())? 7 : Integer.parseInt(dayFrom.getUdValuesValue());
			
			this.voteStoryList = this.storyService.findMostVotedAndLatestStories(range);

		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	/** Load all my stories*/
	private void loadStoryList(){
		try{
			
			this.storyList = this.storyService.findMyAllStories(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private boolean validate(){
		if(CommonValidator.isEmpty(this.currentStory.getStoryTitle())){
			return false;
		}
		if(CommonValidator.isEmpty(this.currentStory.getMemberStoryDescription())){
			return false;
		}
		return true;
	}
	/** Add/Edit/Delete success story */
	public void saveStory(){
		this.hasError = false;
		try{
			if(!validate()){
				this.hasError = true;
				return;
			}
			if(this.ACTION.equals(CommonConstants.ADD)){
				
				this.currentStory.setCategory(CommonConstants.STORY_MENTOR);
				if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
					this.currentStory.setCategory(CommonConstants.STORY_PROTEGE);
				}
				this.storyService.save(this.currentStory);
			}
			else if(this.ACTION.equals(CommonConstants.EDIT)){
				this.storyService.update(this.currentStory);
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
			this.hasError = true;
		}
	}
	public void removeObject(){
		String storyId = this.getParameter(ID_PRM);
		String action = this.getParameter(ACTION_PRM);
		if(storyId == null || action == null){
			return;
		}		
		try{
			this.currentStory = this.storyService.findById(Long.parseLong(storyId));
			this.storyService.delete(this.currentStory);
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		
	}
	private void initNewDialog(){
		this.hasError = false;
		this.currentStory = new MemberStory();
		this.currentStory.setMemberPermissionToPublish(CommonConstants.STATUS.No.toString());
		this.currentStory.setApprovedForPublishInd(CommonConstants.STATUS.No.toString());
		Date dt = new Date();
		this.currentStory.setMemberStoryDate( new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordCreatedDate(new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordLastUpdatedDate(new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordCreatorId(this.getMember().getMemberId().toString());
		this.currentStory.setRecordLastUpdaterId(this.getMember().getMemberId().toString());
		this.currentStory.setNumberOfVotesReceived(0L);
		this.currentStory.setMember(this.getMember());
	}
	/** Initialize modal dialog */
	public void initializeDialog(){
		this.hasError = false;
		try{
			String storyId = this.getParameter(ID_PRM);
			String action = this.getParameter(ACTION_PRM);
			if(storyId == null || action == null){
				return;
			}
			if(action.equals(CommonConstants.ADD)){
				this.initNewDialog();
			}
			else{
				this.currentStory = this.storyService.findById(Long.parseLong(storyId));
			}
			this.ACTION = action;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	
	public List<MemberStory> getStoryList() {
		this.loadStoryList();
		return storyList;
	}
	
	public void setStoryList(List<MemberStory> storyList) {
		this.storyList = storyList;
	}
	public MemberStory getCurrentStory() {
		return currentStory;
	}
	public void setCurrentStory(MemberStory currentStory) {
		this.currentStory = currentStory;
	}
	public List<MemberStory> getVoteStoryList() {
		this.loadVoteStoryList();
		return voteStoryList;
	}
	public void setVoteStoryList(List<MemberStory> voteStoryList) {
		this.voteStoryList = voteStoryList;
	}

	public String getDdDay() {
		return ddDay;
	}

	public void setDdDay(String ddDay) {
		this.ddDay = ddDay;
	}
	private void loadAdminStory(){
		try{
			int dayCount = Integer.parseInt(this.ddDay);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			if(dayCount == 0){
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 1);
			}else{
				cal.add(Calendar.DATE, dayCount);
			}
				
			
			this.adminStoryList = this.storyService.findUnpublishProtegeStories(cal.getTime());
			for(MemberStory story: this.adminStoryList){
				if(isContainCurseWord(story)){
					story.setStoryError(true);
				}
						
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private boolean isContainCurseWord(MemberStory story){
		List<String> words = this.getCurseWords();
		for(String word: words){
			if(story.getMemberStoryDescription().toLowerCase().contains(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	public List<MemberStory> getAdminStoryList() {
		this.loadAdminStory();
		return adminStoryList;
	}

	public void setAdminStoryList(List<MemberStory> adminStoryList) {
		this.adminStoryList = adminStoryList;
	}

	public List<String> getCurseWords() {
		if(this.curseWords == null){
			this.initCurseWordsList();
		}
		return curseWords;
	}

	public void setCurseWords(List<String> curseWords) {
		this.curseWords = curseWords;
	}
	private void initCurseWordsList(){
		try{
			this.curseWords = new ArrayList<String>();
			List<UserDefinedValues> words = this.udService.findByProperty("udValuesCategory", "CURSE_WORDS");
			for(UserDefinedValues ud: words){
				curseWords.add(ud.getUdValuesValue());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public Boolean getHasError() {
		return hasError;
	}
	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

}
