package com.intrigueit.myc2i.memberlog.domain;

import java.sql.Timestamp;
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
import javax.persistence.Transient;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;


@Entity
@Table(name = "MEMBER_LOG")
public class MemberLog implements java.io.Serializable {

	private static final long serialVersionUID = -6795838491539131542L;
	private Long memberLogId;
	
	private Timestamp memberLogDateTime;
	private Long memberId;
	private Long memberActivityType;	
	private UserDefinedValues userDefinedValues;
	private Member member;
	private String memberLogEntryDescription;
	private String topic;
	private String status;
	private String memberDesc;
  private Long recordCreatorId;
	private Member recordCreator;
	private Date recordCreatedDate;
	private Long recordLastUpdaterId;
	private Member recordLastUpdater;
	private Date recordLastUpdatedDate;
	private Long eventDuration;

	/** default constructor */
	public MemberLog() {
	}
	
	@Id 
	@Column(name = "MEMBER_LOG_ID")
	@GeneratedValue(generator="MemberLogSeq")
	@SequenceGenerator(name="MemberLogSeq",sequenceName="MEMBER_LOG_ID_SEQ", allocationSize=1,initialValue=1)	
	public Long getMemberLogId() {
		return memberLogId;
	}
	  
	public void setMemberLogId(Long memberLogId) {
		this.memberLogId = memberLogId;
	}
	
	@Column(name = "MEMBER_LOG_DATE_TIME", nullable = false)
	public Date getMemberLogDateTime() {
		return this.memberLogDateTime;
	}

	public void setMemberLogDateTime(Timestamp memberLogDateTime) {
		this.memberLogDateTime = memberLogDateTime;
	}

	@Column(name = "MEMBER_ID", nullable = false, precision = 22, scale = 0)
	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	 /**
   * @return the memberDesc
   */
  @Transient
	public String getMemberDesc() {
    return memberDesc;
  }

  /**
   * @param memberDesc the memberDesc to set
   */
  public void setMemberDesc(String memberDesc) {
    this.memberDesc = memberDesc;
  }


	@Column(name = "MEMBER_LOG_TYPE_ID", nullable = false, precision = 22, scale = 0)
	public Long getMemberActivityType() {
		return memberActivityType;
	}

	public void setMemberActivityType(Long memberActivityType) {
		this.memberActivityType = memberActivityType;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_LOG_TYPE_ID", nullable = false,insertable = false,updatable = false)
	public UserDefinedValues getUserDefinedValues() {
		return this.userDefinedValues;
	}

	public void setUserDefinedValues(UserDefinedValues userDefinedValues) {
		this.userDefinedValues = userDefinedValues;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false,insertable = false,updatable = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "MEMBER_LOG_ENTRY_DESCRIPTION")
	public String getMemberLogEntryDescription() {
		return this.memberLogEntryDescription;
	}

	public void setMemberLogEntryDescription(String memberLogEntryDescription) {
		this.memberLogEntryDescription = memberLogEntryDescription;
	}
	@Column( name = "MEMBER_LOG_TOPIC")
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORD_CREATOR_ID", nullable = false,insertable = false,updatable = false)	
	public Member getRecordCreator() {
		return this.recordCreator;
	}

	public void setRecordCreator(Member recordCreator) {
		this.recordCreator = recordCreator;
	}

	@Column(name = "RECORD_CREATED_DATE")
	public Date getRecordCreatedDate() {
		return this.recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORD_LAST_UPDATER_ID", nullable = false,insertable = false,updatable = false)
	public Member getRecordLastUpdater() {
		return this.recordLastUpdater;
	}

	public void setRecordLastUpdater(Member recordLastUpdater) {
		this.recordLastUpdater = recordLastUpdater;
	}

	@Column(name = "RECORD_LAST_UPDATED_DATE")
	public Date getRecordLastUpdatedDate() {
		return this.recordLastUpdatedDate;
	}

	public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
		this.recordLastUpdatedDate = recordLastUpdatedDate;
	}

	@Column(name = "EVENT_DURATION")
	public Long getEventDuration() {
		return this.eventDuration;
	}

	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}
		
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column( name = "RECORD_CREATOR_ID")
	public Long getRecordCreatorId() {
		return recordCreatorId;
	}

	public void setRecordCreatorId(Long recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}
	@Column( name = "RECORD_LAST_UPDATER_ID")
	public Long getRecordLastUpdaterId() {
		return recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(Long recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
	}

}