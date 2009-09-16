package com.intrigueit.myc2i.memberlog.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;


@Entity
@Table(name = "MEMBER_LOG")
public class MemberLog implements java.io.Serializable {

	private static final long serialVersionUID = -6795838491539131542L;
	private MemberLogPK id;
	private UserDefinedValues userDefinedValues;
	private Member member;
	private String memberLogEntryDescription;
	private String topic;
	private String status;
	
	private String recordCreatorId;
	private Date recordCreatedDate;
	private String recordLastUpdaterId;
	private Date recordLastUpdatedDate;
	private Long eventDuration;

	// Constructors



	/** default constructor */
	public MemberLog() {
	}

	// Property accessors
	@EmbeddedId
	public MemberLogPK getId() {
		return this.id;
	}

	public void setId(MemberLogPK id) {
		this.id = id;
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

	@Column(name = "RECORD_CREATOR_ID")
	public String getRecordCreatorId() {
		return this.recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	@Column(name = "RECORD_CREATED_DATE")
	public Date getRecordCreatedDate() {
		return this.recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	@Column(name = "RECORD_LAST_UPDATER_ID")
	public String getRecordLastUpdaterId() {
		return this.recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(String recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
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

}