/**
 * @(#)Message.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.intrigueit.myc2i.member.domain.Member;

/**
 * This class represent the MyC2i Message entity
 * 
 * @version 1.00 August 1,2010
 * @author Shamim Ahmmed
 * 
 */
@Entity
@Table(name = "MESSAGE")
public class Message implements java.io.Serializable {

	/** Generated serialized version Id	 */
	private static final long serialVersionUID = 4300580908152410175L;

	/** Unique Id of each Message*/
	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(generator = "MessageSeq")
	@SequenceGenerator(name = "MessageSeq", sequenceName = "MESSAGE_ID_SEQ", allocationSize = 1, initialValue = 1)	
	private Long messageId;

	/** Message reference message Id */
	@Column(name = "REF_MESSAGE_ID", nullable = true)
	private Long refMessageId;
	
	/** Message created time stamp */
	@Column(name = "CREATED_TIME", nullable = false)
	private Date createdTime;
	
	/** Message sender  */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SENDER_ID", nullable = false, insertable = false, updatable = false)
	private Member sender;
	
	/** Sender Id */
	@Column(name = "SENDER_ID", nullable = false)
	private Long senderId;
		
	/** Message receivers */
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinTable(name = "MESSAGE_RECEIVER", uniqueConstraints={},
            joinColumns = {@JoinColumn(name = "MESSAGE_ID", unique=false,updatable= true)},
            inverseJoinColumns = {@JoinColumn(name = "RECEIVER_MEMBER_ID", unique= false,updatable=true)})
	private Set<Member> receiver;
	
	/** Message subject */
	@Column(name = "SUBJECT", nullable = false, length=300)
	private String subject;
	
	/** Message body */
	@Column(name = "BODY", nullable = false, length=2000)
	private String body;
	
	/** Message status , sent or pending */
	@Column(name = "STATUS", nullable = false, length=10)
	private String status;
	
	/** Message reading status */
	@Column( name = "READ_STATUS", nullable= false, length=10)
	private String readStatus;
	
	/** Record creator */
	@Column(name = "RECORD_CREATOR_ID", nullable = true, length=100)
	private String recordCreator;
	
	/** Record updater */
	@Column(name = "RECORD_UPDATER_ID", nullable = true, length=100)
	private String recordLastUpdater;

	/** Record created on */
	@Column(name = "RECORD_CREATED", nullable = true)
	private Date recordCreatedDate;
	
	/** Record last updated */
	@Column(name = "RECORD_LAST_UPDATED", nullable = true)
	private Date recordLastUpdatedDate;
	


	/** default constructor */
	public Message() {
	}
	

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public Long getRefMessageId() {
		return refMessageId;
	}

	public void setRefMessageId(Long refMessageId) {
		this.refMessageId = refMessageId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
	public Member getSender() {
		return sender;
	}

	public void setSender(Member sender) {
		this.sender = sender;
	}
	
	
	public Set<Member> getReceiver() {
		return receiver;
	}

	public void setReceiver(Set<Member> receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecordCreator() {
		return recordCreator;
	}

	public void setRecordCreator(String recordCreator) {
		this.recordCreator = recordCreator;
	}

	public String getRecordLastUpdater() {
		return recordLastUpdater;
	}

	public void setRecordLastUpdater(String recordLastUpdater) {
		this.recordLastUpdater = recordLastUpdater;
	}

	public Date getRecordCreatedDate() {
		return recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	public Date getRecordLastUpdatedDate() {
		return recordLastUpdatedDate;
	}

	public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
		this.recordLastUpdatedDate = recordLastUpdatedDate;
	}


	public Long getSenderId() {
		return senderId;
	}


	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}


	public String getReadStatus() {
		return readStatus;
	}


	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}



}