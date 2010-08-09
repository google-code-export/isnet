/**
 * @(#)MessageServiceImpl.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.message.dao.MessageDao;
import com.intrigueit.myc2i.message.domain.Message;

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
	public List<Message> getConversationByReferenceMessage(Long refMessageId) {
		return this.messageDao.getConversationByReferenceMessage(refMessageId);
	}

	@Override
	public void save(Message entity) {
		this.messageDao.persist(entity);
	}

	@Override
	public void update(Message entity) {
		this.messageDao.update(entity);
	}
	
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

}
