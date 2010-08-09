/**
 * @(#)MessageViewHanlder.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.view;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.message.domain.Message;
import com.intrigueit.myc2i.message.domain.MessageConstant;
import com.intrigueit.myc2i.message.service.MessageService;


/**
 * This is is responsible interacting with the JSF Page
 * 
 * @version 1.00 August 05, 2010
 * @author Shamim Ahmmed
 * 
 */
/**
 * @author Shamim
 *
 */
@Component("messageHanlder")
@Scope("session")
public class MessageViewHanlder extends BasePage{

	/** Current Message stored */
	private Message currentMessage;
	
	/** Message Service reference */
	private MessageService messageService;
	
	/** Member Service reference */
	private MemberService memberService;

	/** Error message */
	private String errMessage;
	
	public void initMessageDialog(){
		this.currentMessage = new Message();

		this.currentMessage.setSubject("Test message ");
		this.currentMessage.setBody("Test Message Body");
		
	}
	public void addNewMessage(){
		try{
			
			Member to = this.memberService.findById(this.getMember().getMemberId());
			this.currentMessage.setSenderId(this.getMember().getMemberId());
			Set<Member> receivers = new HashSet<Member>();
			receivers.add(to);
			
			this.currentMessage.setReceiver(receivers);
			this.currentMessage.setStatus(MessageConstant.MESSAGE_STATUS);
			this.currentMessage.setCreatedTime(new Date());
			
			this.addAuditField();
			
			this.messageService.save(this.currentMessage);
		}
		catch(Exception ex){
			this.errMessage = ex.getMessage();
			ex.printStackTrace();
		}
	}
	
	/**
	 * Generate Audit fields
	 */
	private void addAuditField(){
		this.currentMessage.setRecordCreatedDate(new Date());
		this.currentMessage.setRecordLastUpdatedDate(new Date());
		this.currentMessage.setRecordCreator(this.getMember().getMemberId().toString());
		this.currentMessage.setRecordLastUpdater(this.getMember().getMemberId().toString());
		
	}
	public Message getCurrentMessage() {
		return currentMessage;
	}

	public void setCurrentMessage(Message currentMessage) {
		this.currentMessage = currentMessage;
	}

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
}
