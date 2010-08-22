/**
 * @(#)MessageServiceImpl.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.message.dao.MessageDao;
import com.intrigueit.myc2i.message.domain.Message;
import com.intrigueit.myc2i.message.domain.MessageConstant.MessageReadingStatus;
import com.intrigueit.myc2i.message.domain.MessageConstant.MessageStatus;

/**
 * This Class is the Service implementation of MessageService interface
 * 
 * @version 1.00 August 05, 2010
 * @author Shamim Ahmmed
 * 
 */

@Service
@Transactional
public final class MessageServiceImpl implements MessageService {

	/** Message Dao Reference */
	private MessageDao messageDao;
	
	@Override
	public void delete(Message entity) {
		this.messageDao.delete(entity);
	}

	@Override
	public Message findById(Long messageId) {
		return this.messageDao.loadById(messageId);
	}

	@Override
	public List<Message> getConversation(Long senderId) {
		return this.messageDao.getConversation(senderId);
	}

	@Override
	public List<Message> getConversationByReferenceMessage(Long ownerId,Long refMessageId) {
		return this.messageDao.getConversationByReferenceMessage(ownerId,refMessageId);
	}

	@Override
	public void save(Message entity) {
		/** Save the first copy of this message */
		this.messageDao.persist(entity);
		
		/** Now send the CC to each user */
		Set<Member> list = entity.getReceiver();
		for(Member member: list){
			
			Message msg = entity.copy();
			msg.setMessageId(null);
			msg.setOwnerId(member.getMemberId());
			msg.setStatus(MessageStatus.RECEIVED.toString());
			msg.setReadStatus(MessageReadingStatus.UNREAD.toString());
			this.messageDao.persist(msg);
		}
	}

	@Override
	public void update(Message entity) {
		this.messageDao.update(entity);
	}
	
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public Boolean removeMessage(Long messageId) {
		int rowEffected = this.messageDao.deleteMessageById(messageId);
		return rowEffected > 0;
	}

	@Override
	public List<Message> getConversationByOwner(Long ownerId,String status) {
		return this.messageDao.getConversationByOwnerId(ownerId,status);
	}

	@Override
	public List<Message> getUnReadConversationByOwner(Long ownerId,
			String status) {
		return this.messageDao.getUnReadConversationByOwnerId(ownerId,status,MessageReadingStatus.UNREAD.toString());
	}

	@Override
	public int getUnReadMessageByOwner(Long ownerId) {
		return this.messageDao.getMessageCount(ownerId, MessageReadingStatus.UNREAD.toString());
	}

}
