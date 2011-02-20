package com.intrigueit.myc2i.memberlog.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	private Long fromMemberId;
	private Member fromMember;
	
	private Long toMemberId;
	private Member toMember;
	
	private Long memberActivityType;
	private UserDefinedValues userDefinedValues;

	private String memberLogEntryDescription;
	private String topic;
	private String status;
	private String memberDesc;
	
	private String recordCreatorId;
	private String recordLastUpdaterId;

	private Date recordCreatedDate;
	private Date recordLastUpdatedDate;
	
	private Long eventDuration;

	/** default constructor */
	public MemberLog() {
	}

	@Id
	@Column(name = "MEMBER_LOG_ID")
	@GeneratedValue(strategy=GenerationType.AUTO) 
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

	@Column(name = "FROM_MEMBER_ID", nullable = false, precision = 22, scale = 0)
	public Long getFromMemberId() {
		return this.fromMemberId;
	}

	public void setFromMemberId(Long fromMemberId) {
		this.fromMemberId = fromMemberId;
	}

	/**
	 * @return the memberDesc
	 */
	@Transient
	public String getMemberDesc() {
		return memberDesc;
	}

	/**
	 * @param memberDesc
	 *            the memberDesc to set
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
	@JoinColumn(name = "MEMBER_LOG_TYPE_ID", nullable = false, insertable = false, updatable = false)
	public UserDefinedValues getUserDefinedValues() {
		return this.userDefinedValues;
	}

	public void setUserDefinedValues(UserDefinedValues userDefinedValues) {
		this.userDefinedValues = userDefinedValues;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	public Member getFromMember() {
		return this.fromMember;
	}

	public void setFromMember(Member fromMember) {
		this.fromMember = fromMember;
	}

	@Column(name = "MEMBER_LOG_ENTRY_DESCRIPTION")
	public String getMemberLogEntryDescription() {
		return this.memberLogEntryDescription;
	}

	public void setMemberLogEntryDescription(String memberLogEntryDescription) {
		this.memberLogEntryDescription = memberLogEntryDescription;
	}

	@Column(name = "MEMBER_LOG_TOPIC")
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "RECORD_CREATED_DATE")
	public Date getRecordCreatedDate() {
		return this.recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
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

	@Column(name = "RECORD_CREATOR_ID")
	public String getRecordCreatorId() {
		return recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	@Column(name = "RECORD_LAST_UPDATER_ID")
	public String getRecordLastUpdaterId() {
		return recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(String recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
	}
	
	@Column(name = "TO_MEMBER_ID", nullable = false, precision = 22, scale = 0)
	public Long getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TO_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	public Member getToMember() {
		return toMember;
	}

	public void setToMember(Member toMember) {
		this.toMember = toMember;
	}

}