/**
 * @(#)MessageViewHanlder.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.view;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
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

	/** List of messages to view in a grid */
	private List<Message> messages;

	/** List of messages related to another message */
	private List<Message> relatedMessages;

	private Long detailMesssageId;

	/** Attachment view handler reference */
	private AttachmentUploadHandler attachmentHandler;

	private String toMemberNameList = "";
	
	private Map<Long, String> toMemberList = new HashMap<Long, String>();
	
	private String currentFolder = null;
	
	private String msgOtherParticipients;
	
	private boolean showOnlyUnreadMsg = false;
	
	/** No of unread message count in box */
	private int unReadMessageCount =0;
	
	private String memberLinks;

	
	private void loadUnreadMessageCount(){
		
		try{
			int count = this.messageService.getUnReadMessageByOwner(this.getMember().getMemberId());
			this.unReadMessageCount = count;

		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	/** Load the list of all messages */
	private void loadMessages() {
		try {
			String FOLDER = this.getParameter("folder");

			if (FOLDER == null || FOLDER.equals("")) {
				//this.currentFolder =  ;
			}else{
				this.currentFolder = FOLDER;
			}
			log.debug("Loading message from: "+ this.currentFolder);
			
			this.messages = messageService.getConversationByOwner(this.getMember().getMemberId(), this.currentFolder);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/** Load the list of all messages */
	public void showUnReadMessages() {
		try {

			log.debug("Loading message from: "+ this.currentFolder);
			
			this.messages = messageService.getUnReadConversationByOwner(this.getMember().getMemberId(), this.currentFolder);
			
			this.setShowOnlyUnreadMsg(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void showAllMessage(){
		try{
			this.setShowOnlyUnreadMsg(false);
			this.messages = null;
		}
		catch(Exception ex){
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
	 * Clear the error message flags
	 */
	private void resetErrorMessage(){
		this.hasError = false;
		this.errMsgs.clear();
	}
	/**
	 * Initialize message dialog
	 */
	public void initMessageDialog() {
		this.currentMessage = new Message();

		this.currentMessage.setSubject("");
		this.currentMessage.setBody("");
		this.toMemberList.clear();
		this.toMemberNameList = "";
		this.resetErrorMessage();

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
				}
			}
			/** reset the all the messages */
			this.messages = null;
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
			/** reset the all the messages */
			this.messages = null;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void selectAllMessages(){
		try{
			for(Message message : this.messages){
				message.setChecked(true);
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public void deSelectAllMessages(){
		try{
			for(Message message : this.messages){
				message.setChecked(false);
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public String markMessageAsUnRead(){
		try {
			String id = this.getParameter("ID");

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
			//System.out.println(id);
			if (id == null || id.equals("")) {
				return null;
			}
			Message message = this.changeReadingStatus(Long.parseLong(id), MessageReadingStatus.READ.toString());
			
			this.prepareReplyMessage(message);
			
			/** reset the all the messages */
			this.messages = null;

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
	public void removeMessage() {
		try {
			String id = this.getParameter("ID");
			if (id == null || id.equals("")) {
				return ;
			}
			this.messageService.removeMessage(Long.parseLong(id));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean checkError(){
		
		/** Check to member is empty */
		if(this.toMemberNameList == null || this.toMemberNameList.equals("")){
			this.errMsgs.add("Message destination is empty");
		}
		/** Check subject fields */
		if(this.currentMessage.getSubject() == null || this.currentMessage.getSubject().equals("")){
			this.errMsgs.add("Message subject is empty");
		}
		/** Check the message body */
		if(this.currentMessage.getBody() == null || this.currentMessage.getBody().equals("") ){
			this.errMsgs.add("Message body is empty");
		}
		return this.errMsgs.size()> 0;
	}
	/**
	 * Create a new message
	 */
	public void addNewMessage() {
		try {
			
			this.resetErrorMessage();
			if(this.checkError()){
				this.hasError = true;
				return;
			}
			this.currentMessage.setSenderId(this.getMember().getMemberId());
			Set<Member> receivers = this.getReceivers();

			this.currentMessage.setOwnerId(this.getMember().getMemberId());
			this.currentMessage.setReceiver(receivers);
			this.currentMessage.setStatus(MessageStatus.SENT.toString());
			this.currentMessage.setCreatedTime(new Date());
			this.currentMessage.setReadStatus(MessageReadingStatus.READ
					.toString());

			this.addAuditField();

			this.messageService.save(this.currentMessage);
		} catch (Exception ex) {
			this.errMsgs.add(ex.getMessage());
			this.hasError = true;
			ex.printStackTrace();
		}
	}
	private Set<Member> getReceivers()throws Exception{
		Set<Member> receivers = new HashSet<Member>();
		
		Set<Entry<Long, String>>  enty = this.getToMemberList().entrySet();
		for(Entry<Long, String> ent : enty){
			//log.debug(ent.getKey() +" ---------- " + ent.getValue());
			Member member = this.memberService.findById(ent.getKey());
			
			if(member != null){
				receivers.add(member);
			}
		}
		/** If to member list is empty */
		if( this.getToMemberList().size() < 1){
			receivers = this.getMembersFromEmail();
		}
		if( receivers == null || receivers.size() < 1){
			throw new Exception("No valid destination address");
		}
		return receivers;
	}
	
	/**
	 * Populate the to member list from the email addresses
	 * @return Set of to member list
	 */
	private Set<Member> getMembersFromEmail(){
		if(this.toMemberNameList == null || this.toMemberNameList.equals("")){
			return null;
		}
		String emailList[] = this.toMemberNameList.split(",");
		if(emailList.length < 1){
			return null;
		}
		Set<Member> receivers = new HashSet<Member>();
		for(String email: emailList){
			log.debug("-------->"+ email);
			Member member = this.memberService.loadMemberByEmail(email.trim());
			if(member != null){
				receivers.add(member);
			}
		}
		return receivers;
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
			
			this.currentMessage.setBody("");
			
			/** generate conversation recipients name list for detail page */
			this.msgOtherParticipients = "";
			this.msgOtherParticipients = "["+this.currentMessage.getSender().getFirstName() + " "+ this.currentMessage.getSender().getLastName()+"]";
			if(this.currentMessage.isHasMultipleRecipient()){
				this.msgOtherParticipients = this.msgOtherParticipients + " and Others";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Set<Member> getReceiversList(Message message) {
		Set<Member> receivers = new HashSet<Member>();

		if (this.isValidReceiver(message.getSenderId())) {
			Member sender = this.memberService.findById(message.getSenderId());
			if(sender != null){
				receivers.add(sender);
			}
		}

		for (Member member : message.getReceiver()) {
			/**  */
			if (this.isValidReceiver(member.getMemberId())) {
				Member mem = this.memberService.findById(member.getMemberId());
				if(mem != null){
					receivers.add(mem);
				}
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
			String serverName = InetAddress.getLocalHost().getHostName();//"localhost";// 

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



	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public synchronized List<Message> getMessages() {
		
		if(this.messages == null){
			this.loadMessages();
		}
		
		/** If folder name change then load message again */
		String folder = this.getParameter("folder");
		if(folder != null && !folder.equals(this.currentFolder)){
			log.debug("Folder changed loading message again...");
			this.loadMessages();
		}
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
	public List<Member> autocompleteMemberName(Object suggest){
		String pref = (String)suggest;
		
		log.debug(pref);
		
		List<Member> members = new ArrayList<Member>();
		//Member member = this.memberService.findById(this.getMember().getMemberId());
		
		//for(KnownMember mem: member.getKnownMembers()){
		//	members.add(mem.getFriend());
		//}
		try{
			members = memberService.getMyFriendList(this.getMember().getMemberId());
			log.debug(" frieds size: "+ members.size());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return members;
	}
	public void test(){
		try{
			log.debug("Item selected event :");
			String prm1 = this.getParameter("PRM1");
			String prm2 = this.getParameter("PRM2");
			log.debug("++++"+prm1 + " ++++++++ "+ prm2);
			if(prm1 == null || prm1.equals("")){
				return;
			}
			Long memberId = Long.parseLong(prm1);
			if(!toMemberList.containsKey(memberId)){
				this.toMemberList.put(memberId, prm2);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public Map<Long, String> getToMemberList() {
		return toMemberList;
	}

	public void setToMemberList(Map<Long, String> toMemberList) {
		this.toMemberList = toMemberList;
	}

	public String getToMemberNameList() {
		return toMemberNameList;
	}

	public void setToMemberNameList(String toMemberNameList) {
		this.toMemberNameList = toMemberNameList;
	}

	public String getCurrentFolder() {
		return currentFolder;
	}
	public void setCurrentFolder(String currentFolder) {
		this.currentFolder = currentFolder;
	}
	public String getMsgOtherParticipients() {
		return msgOtherParticipients;
	}
	public void setMsgOtherParticipients(String msgOtherParticipients) {
		this.msgOtherParticipients = msgOtherParticipients;
	}
	public boolean isShowOnlyUnreadMsg() {
		return showOnlyUnreadMsg;
	}
	public void setShowOnlyUnreadMsg(boolean showOnlyUnreadMsg) {
		this.showOnlyUnreadMsg = showOnlyUnreadMsg;
	}
	public int getUnReadMessageCount() {
		this.loadUnreadMessageCount();
		return unReadMessageCount;
	}
	public void setUnReadMessageCount(int unReadMessageCount) {
		this.unReadMessageCount = unReadMessageCount;
	}
	public String getMemberLinks() {
		if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
			this.memberLinks = "protegeMessage.faces";
		}else{
			this.memberLinks = "memberMessage.faces";
		}
		return memberLinks;
	}
	public void setMemberLinks(String memberLinks) {
		this.memberLinks = memberLinks;
	}


}
