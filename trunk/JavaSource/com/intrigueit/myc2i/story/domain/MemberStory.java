package com.intrigueit.myc2i.story.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.intrigueit.myc2i.member.domain.Member;


@Entity
@Table(name = "MEMBER_STORY")
public class MemberStory implements java.io.Serializable {
	private Long memberStoryId;
	private Member member;
	private Date memberStoryDate;
	private String memberStoryDescription;
	private String memberStoryAudioVideo;
	private String deletedByAdminUserInd;
	private String screenedByMemberId;
	private String approvedForPublishInd;
	private String memberPermissionToPublish;
	private Date approvalDate;
	private Long numberOfVotesReceived;
	private Date weekWinnerIndicator;
	private String recordCreatorId;
	private Date recordCreatedDate;
	private String recordLastUpdaterId;
	private Date recordLastUpdatedDate;
	private String storyTitle;
	private String category;
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "MEMBER_STORY_ID")
	@GeneratedValue(generator="StorySeq")
	@SequenceGenerator(name="StorySeq",sequenceName="MEMBER_STORY_ID_SEQ", allocationSize=1,initialValue=1)		
	public Long getMemberStoryId() {
		return memberStoryId;
	}

	public void setMemberStoryId(Long memberStoryId) {
		this.memberStoryId = memberStoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "MEMBER_STORY_DATE", nullable = false)
	public Date getMemberStoryDate() {
		return this.memberStoryDate;
	}

	public void setMemberStoryDate(Date memberStoryDate) {
		this.memberStoryDate = memberStoryDate;
	}

	@Column(name = "MEMBER_STORY_DESCRIPTION", nullable = false, length = 500)
	public String getMemberStoryDescription() {
		return this.memberStoryDescription;
	}

	public void setMemberStoryDescription(String memberStoryDescription) {
		this.memberStoryDescription = memberStoryDescription;
	}
	

	@Column(name = "MEMBER_STORY_AUDIO_VIDEO")
	public String  getMemberStoryAudioVideo() {
		return this.memberStoryAudioVideo;
	}

	public void setMemberStoryAudioVideo(String memberStoryAudioVideo) {
		this.memberStoryAudioVideo = memberStoryAudioVideo;
	}

	@Column(name = "DELETED_BY_ADMIN_USER_IND",  length = 20)
	public String getDeletedByAdminUserInd() {
		return this.deletedByAdminUserInd;
	}

	public void setDeletedByAdminUserInd(String deletedByAdminUserInd) {
		this.deletedByAdminUserInd = deletedByAdminUserInd;
	}

	@Column(name = "SCREENED_BY_MEMBER_ID", precision = 22, scale = 0)
	public String getScreenedByMemberId() {
		return this.screenedByMemberId;
	}

	public void setScreenedByMemberId(String screenedByMemberId) {
		this.screenedByMemberId = screenedByMemberId;
	}

	@Column(name = "APPROVED_FOR_PUBLISH_IND", nullable = false, length = 20)
	public String getApprovedForPublishInd() {
		return this.approvedForPublishInd;
	}

	public void setApprovedForPublishInd(String approvedForPublishInd) {
		this.approvedForPublishInd = approvedForPublishInd;
	}

	@Column(name = "MEMBER_PERMISSION_TO_PUBLISH", nullable = false, length = 20)
	public String getMemberPermissionToPublish() {
		return this.memberPermissionToPublish;
	}

	public void setMemberPermissionToPublish(String memberPermissionToPublish) {
		this.memberPermissionToPublish = memberPermissionToPublish;
	}

	@Column(name = "APPROVAL_DATE")
	public Date getApprovalDate() {
		return this.approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Column(name = "NUMBER_OF_VOTES_RECEIVED")
	public Long getNumberOfVotesReceived() {
		return this.numberOfVotesReceived;
	}

	public void setNumberOfVotesReceived(Long numberOfVotesReceived) {
		this.numberOfVotesReceived = numberOfVotesReceived;
	}

	@Column(name = "WEEK_WINNER_INDICATOR")
	public Date getWeekWinnerIndicator() {
		return this.weekWinnerIndicator;
	}

	public void setWeekWinnerIndicator(Date weekWinnerIndicator) {
		this.weekWinnerIndicator = weekWinnerIndicator;
	}

	@Column(name = "RECORD_CREATOR_ID", nullable = false)
	public String getRecordCreatorId() {
		return this.recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	@Column(name = "RECORD_CREATE_DATE", nullable = false)
	public Date getRecordCreatedDate() {
		return this.recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	@Column(name = "RECORD_LAST_UPDATER_ID", nullable = false)
	public String getRecordLastUpdaterId() {
		return this.recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(String recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
	}


	@Column(name = "RECORD_LAST_UPDATED_DATE", nullable = false)
	public Date getRecordLastUpdatedDate() {
		return this.recordLastUpdatedDate;
	}

	public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
		this.recordLastUpdatedDate = recordLastUpdatedDate;
	}
	@Column( name = "STORY_TITLE")
	public String getStoryTitle() {
		return storyTitle;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	
	@Column( name = "STORY_CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}