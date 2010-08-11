/**
 * @(#)MessageViewHanlder.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.message.domain.Message;
import com.intrigueit.myc2i.message.domain.MessageConstant;
import com.intrigueit.myc2i.message.domain.MessageConstant.MessageReadingStatus;
import com.intrigueit.myc2i.message.domain.MessageConstant.MessageStatus;
import com.intrigueit.myc2i.message.service.MessageService;


/**
 * This is is responsible interacting with the JSF Page
 * 
 * @version 1.00 August 05, 2010
 * @author Shamim Ahmmed
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
	
	/** List of messages to view in a grid */
	private List<Message> messages;
	
	/** List of messages related to another message */
	private List<Message> relatedMessages;
	
	
	/** Load the list of all messages */
	private void loadMessages(){
		try{
			this.messages = messageService.getConversationByOwner(this.getMember().getMemberId(),MessageStatus.RECEIVED.toString());
			System.out.println("---------: "+this.messages.size());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void loadRlatedMessage(){
		
		try{
			this.relatedMessages = this.messageService.getConversationByReferenceMessage(this.getMember().getMemberId(), this.currentMessage.getMessageId());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	/**
	 * Initialize message dialog
	 */
	public void initMessageDialog(){
		this.currentMessage = new Message();

		this.currentMessage.setSubject("Test message ");
		this.currentMessage.setBody("Test Message Body");
		
	}
	/**
	 * 
	 */
	public void markAsUnRead(){
		try{
			for(Message message: this.messages){
				if(message.isChecked()){
					message.setReadStatus(MessageReadingStatus.UNREAD.toString());
					this.messageService.update(message);
					System.out.println(message.getReadStatus());
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void removeAllMessages(){
		try{
			for(Message message: this.messages){
				if(message.isChecked()){
					
					this.messageService.removeMessage(message.getMessageId());
					
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}	
	public String readMessage(){
		try{

			String id = this.getParameter("ID");
			if( id == null || id.equals("")){
				return null;
			}
			
			Message message = this.messageService.findById(Long.parseLong(id));
			message.setReadStatus(MessageReadingStatus.READ.toString());
			
			this.messageService.update(message);
			
			this.prepareReplyMessage(message);
			

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return MessageConstant.BACK_TO_DETAIL_PAGE;
	}
	public void removeMessage(ActionEvent event){
		try{
			Long id = (Long) event.getComponent().getAttributes().get("ID");
			this.messageService.removeMessage(id);

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Create a new message 
	 */
	public void addNewMessage(){
		try{
			
			Member to = this.memberService.findById(1L);
			this.currentMessage.setSenderId(this.getMember().getMemberId());
			Set<Member> receivers = new HashSet<Member>();
			receivers.add(to);
			
			this.currentMessage.setOwnerId(this.getMember().getMemberId());
			this.currentMessage.setReceiver(receivers);
			this.currentMessage.setStatus(MessageStatus.SENT.toString());
			this.currentMessage.setCreatedTime(new Date());
			this.currentMessage.setReadStatus(MessageReadingStatus.READ.toString());
			
			this.addAuditField();
			
			this.messageService.save(this.currentMessage);
		}
		catch(Exception ex){
			this.errMessage = ex.getMessage();
			ex.printStackTrace();
		}
	}
	public void prepareReplyMessage(Message message){
		try{
			
			this.currentMessage = message.copy();
			this.currentMessage.setRefMessageId(message.getMessageId());
			

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void replyMessage(){
		try{
			

			Set<Member> receivers = new HashSet<Member>();
			
			Member sender = this.memberService.findById(this.currentMessage.getSenderId());
			receivers.add(sender);
			
			for(Member member: this.currentMessage.getReceiver()){
				/** Remove the sender name */
				if( ! member.getMemberId().equals(this.getMember().getMemberId())){
					Member mem = this.memberService.findById(member.getMemberId());
					receivers.add(mem);
					System.out.println(member.getMemberId() + " --- "+ this.getMember().getMemberId());
				}
			}
			this.currentMessage.setReceiver(receivers);
			
			this.currentMessage.setMessageId(null);
			
			this.currentMessage.setOwnerId(this.getMember().getMemberId());
			this.currentMessage.setSenderId(this.getMember().getMemberId());
			
			this.currentMessage.setStatus(MessageStatus.SENT.toString());
			this.currentMessage.setCreatedTime(new Date());
			this.currentMessage.setReadStatus(MessageReadingStatus.READ.toString());
			
			this.addAuditField();
			
			this.messageService.save(this.currentMessage);			
		}
		catch(Exception ex)
		{
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
	public synchronized List<Message> getMessages() {
		//if(this.messages == null){
		this.loadMessages();
		//}
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<Message> getRelatedMessages() {
		this.loadRlatedMessage();
		return relatedMessages;
	}
	public void setRelatedMessages(List<Message> relatedMessages) {
		this.relatedMessages = relatedMessages;
	}
	
	
}
