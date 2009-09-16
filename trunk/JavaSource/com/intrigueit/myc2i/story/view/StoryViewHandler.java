package com.intrigueit.myc2i.story.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.story.domain.MemberStory;
import com.intrigueit.myc2i.story.service.StoryService;


@Component("storyViewHandler")
@Scope("session")
public class StoryViewHandler extends BasePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6745509962650776624L;
	
	private static  final String ID_PRM = "storyId";
	private static final String ACTION_PRM = "action";
	
	private StoryService storyService;
	
	private List<MemberStory> storyList;
	
	private MemberStory currentStory;
	
	private String ACTION = "";
	
	

	@Autowired
	public StoryViewHandler(StoryService storyService) {
		this.storyService = storyService;
		this.currentStory = new MemberStory();
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
	
	/** Add/Edit/Delete success story */
	public void saveStory(){
		try{
			if(this.ACTION.equals(CommonConstants.ADD)){
				this.storyService.save(this.currentStory);
			}
			else if(this.ACTION.equals(CommonConstants.EDIT)){
				this.storyService.update(this.currentStory);
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
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
		this.currentStory = new MemberStory();
		this.currentStory.setMemberPermissionToPublish(CommonConstants.STATUS.No.toString());
		this.currentStory.setApprovedForPublishInd(CommonConstants.STATUS.No.toString());
		Date dt = new Date();
		this.currentStory.setMemberStoryDate( new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordCreatedDate(new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordLastUpdatedDate(new java.sql.Timestamp(dt.getTime()));
		this.currentStory.setRecordCreatorId(this.getMember().getMemberId());
		this.currentStory.setRecordLastUpdaterId(this.getMember().getMemberId());
		
		this.currentStory.setMember(this.getMember());
	}
	/** Initialize modal dialog */
	public void initializeDialog(){
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

}
