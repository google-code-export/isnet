/**
 * @(#)MessageViewHanlder.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.view;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.message.domain.AttachedFile;
import com.intrigueit.myc2i.message.domain.Message;
import com.intrigueit.myc2i.message.domain.MessageAttachment;
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
public class MessageViewHanlder extends BasePage {

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

	private Long detailMesssageId;

	/** Attachment view handler reference */
	private AttachmentUploadHandler attachmentHandler;

	// private List<MessageAttachment> attachments;

	/** Load the list of all messages */
	private void loadMessages() {
		try {
			this.messages = messageService.getConversationByOwner(this
					.getMember().getMemberId(), MessageStatus.RECEIVED
					.toString());
			System.out.println("---------: " + this.messages.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadRlatedMessage() {

		try {
			this.relatedMessages = this.messageService
					.getConversationByReferenceMessage(this.getMember()
							.getMemberId(), this.detailMesssageId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Initialize message dialog
	 */
	public void initMessageDialog() {
		this.currentMessage = new Message();

		this.currentMessage.setSubject("Test message ");
		this.currentMessage.setBody("Test Message Body");

	}

	/**
	 * 
	 */
	public void markAsUnRead() {
		try {
			for (Message message : this.messages) {
				if (message.isChecked()) {
					message.setReadStatus(MessageReadingStatus.UNREAD
							.toString());
					this.messageService.update(message);
					//System.out.println(message.getReadStatus());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void removeAllMessages() {
		try {
			for (Message message : this.messages) {
				if (message.isChecked()) {

					this.messageService.removeMessage(message.getMessageId());

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String markMessageAsUnRead(){
		try {
			String id = this.getParameter("ID");
			System.out.println(id);
			if (id == null || id.equals("")) {
				return null;
			}
			this.changeReadingStatus(Long.parseLong(id), MessageReadingStatus.UNREAD.toString());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MessageConstant.BACK_TO_MSG_MAIN_PAGE;
	}
	private Message changeReadingStatus(Long messageId,String status)throws Exception{
		Message message = this.messageService.findById(messageId);
		message.setReadStatus(status);
		this.messageService.update(message);
		return message;
	}
	
	/** Mark a message as read and navigate to detail page */
	public String readMessage() {
		try {

			HttpServletRequest req = this.getRequest();
			String id = req.getParameter("ID");
			System.out.println(id);
			if (id == null || id.equals("")) {
				return null;
			}
			Message message = this.changeReadingStatus(Long.parseLong(id), MessageReadingStatus.READ.toString());
			
			this.prepareReplyMessage(message);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MessageConstant.BACK_TO_DETAIL_PAGE;
	}

	/** Message details page remove function */
	public String deleteMessage(){
		try {
			String id = this.getParameter("ID");
			if (id == null || id.equals("")) {
				return null;
			}
			this.messageService.removeMessage(Long.parseLong(id));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MessageConstant.BACK_TO_MSG_MAIN_PAGE;
	}
	
	/** Message main page message remove function */
	public void removeMessage(ActionEvent event) {
		try {
			Long id = (Long) event.getComponent().getAttributes().get("ID");
			this.messageService.removeMessage(id);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Create a new message
	 */
	public void addNewMessage() {
		try {

			Member to = this.memberService.findById(1L);
			this.currentMessage.setSenderId(this.getMember().getMemberId());
			Set<Member> receivers = new HashSet<Member>();
			receivers.add(to);

			this.currentMessage.setOwnerId(this.getMember().getMemberId());
			this.currentMessage.setReceiver(receivers);
			this.currentMessage.setStatus(MessageStatus.SENT.toString());
			this.currentMessage.setCreatedTime(new Date());
			this.currentMessage.setReadStatus(MessageReadingStatus.READ
					.toString());

			this.addAuditField();

			this.messageService.save(this.currentMessage);
		} catch (Exception ex) {
			this.errMessage = ex.getMessage();
			ex.printStackTrace();
		}
	}

	public void prepareReplyMessage(Message message) {
		try {

			this.currentMessage = message.copy();
			if(this.currentMessage.getRefMessageId() == null){
				this.currentMessage.setRefMessageId(message.getMessageId());
			}
			this.detailMesssageId = message.getMessageId();
			this.currentMessage.getAttachments().clear();
			this.currentMessage.getTmpAttachments().clear();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Set<Member> getReceiversList(Message message) {
		Set<Member> receivers = new HashSet<Member>();

		if (this.isValidReceiver(message.getSenderId())) {
			Member sender = this.memberService.findById(message.getSenderId());
			receivers.add(sender);
		}

		for (Member member : message.getReceiver()) {
			/**  */
			if (this.isValidReceiver(member.getMemberId())) {
				Member mem = this.memberService.findById(member.getMemberId());
				receivers.add(mem);
			}
		}

		return receivers;
	}

	private Boolean isValidReceiver(final Long memberId) {
		if (memberId.equals(this.getMember().getMemberId())) {
			return false;
		}
		return true;
	}

	public void replyMessage() {
		try {

			this.detailMesssageId = this.currentMessage.getRefMessageId();

			Set<Member> receivers = this.getReceiversList(this.currentMessage);

			this.currentMessage.setReceiver(receivers);

			this.currentMessage.setMessageId(null);

			this.currentMessage.setOwnerId(this.getMember().getMemberId());
			this.currentMessage.setSenderId(this.getMember().getMemberId());

			this.currentMessage.setStatus(MessageStatus.SENT.toString());
			this.currentMessage.setCreatedTime(new Date());
			this.currentMessage.setReadStatus(MessageReadingStatus.READ
					.toString());

			this.addAuditField();

			this.messageService.save(this.currentMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private MessageAttachment createAttachment(Message message,
			String fileName, String path) {
		MessageAttachment attachment = new MessageAttachment();
		attachment.setFileName(fileName);
		attachment.setFilePath(path);
		attachment.setMessage(message);
		return attachment;
	}

	public void attachFile() {

		if (this.attachmentHandler == null
				|| this.attachmentHandler.getUploadedFile() == null) {
			return;
		}
		if (this.currentMessage.getAttachments() == null) {
			this.currentMessage
					.setAttachments(new HashSet<MessageAttachment>());
		}
/*		if (this.currentMessage.getTmpAttachments() == null) {
			this.currentMessage
					.setTmpAttachments(new ArrayList<MessageAttachment>());
		}*/
		try {

			AttachedFile file = this.getAttachmentHandler().getUploadedFile();

			MessageAttachment attachment = this.createAttachment(
					this.currentMessage, file.getName(),
					AttachmentUploadHandler.DEFAULT_FILE_LOCATION);
			this.currentMessage.getAttachments().add(attachment);
			//this.currentMessage.getTmpAttachments().add(attachment);

			System.out.println("Uploaded file ::" + file.getName());
			this.attachmentHandler.clearUploadData();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Generate Audit fields
	 */
	private void addAuditField() {
		this.currentMessage.setRecordCreatedDate(new Date());
		this.currentMessage.setRecordLastUpdatedDate(new Date());
		this.currentMessage.setRecordCreator(this.getMember().getMemberId()
				.toString());
		this.currentMessage.setRecordLastUpdater(this.getMember().getMemberId()
				.toString());

	}

	public void downloadAttachedFile() {

		String fileName = this.getParameter("FILE");
		if (fileName == null || fileName.equals("")) {
			return;
		}

		try {

			String url = "/export/ExportServlet?name=" + fileName;
			String serverName = "localhost";// InetAddress.getLocalHost().getHostName();

			final int port = this.getRequest().getServerPort();
			url = "http://" + serverName + ":" + port + ""
					+ this.getRequest().getContextPath() + url;

			// url = "http://"+ serverName
			// +":8080"+this.getRequest().getContextPath()+url;
			this.getResponse().sendRedirect(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void removeAttachedFile() {
		String fileName = this.getParameter("FILE");
		if (fileName == null || fileName.equals("")) {
			return;
		}
		MessageAttachment attch = this.getAttachmentObj(fileName);
		if (attch == null) {
			return;
		}
		try {
			this.currentMessage.getAttachments().remove(attch);
			this.currentMessage.getTmpAttachments().remove(attch);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private MessageAttachment getAttachmentObj(String fileName) {

		for (MessageAttachment attch : this.currentMessage.getAttachments()) {
			if (attch.getFileName().equals(fileName)) {
				return attch;
			}
		}
		return null;
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
		// if(this.messages == null){
		this.loadMessages();
		// }
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

	public Long getDetailMesssageId() {
		return detailMesssageId;
	}

	public void setDetailMesssageId(Long detailMesssageId) {
		this.detailMesssageId = detailMesssageId;
	}

	public AttachmentUploadHandler getAttachmentHandler() {
		if (this.attachmentHandler == null) {
			this.attachmentHandler = new AttachmentUploadHandler();
		}
		return attachmentHandler;
	}

	public void setAttachmentHandler(AttachmentUploadHandler attachmentHandler) {
		this.attachmentHandler = attachmentHandler;
	}

	/*
	 * public List<MessageAttachment> getAttachments() { if(this.attachments ==
	 * null){ this.attachments = new ArrayList<MessageAttachment>(); } return
	 * attachments; }
	 * 
	 * public void setAttachments(List<MessageAttachment> attachments) {
	 * this.attachments = attachments; }
	 */

}
