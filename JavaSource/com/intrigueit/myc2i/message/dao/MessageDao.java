/**
 * @(#)MessageDao.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.message.domain.Message;


/**
 * This interface represent the data access interface for Message entity
 * 
 * @version 1.00 August 05, 2010
 * @author Shamim Ahmmed
 * 
 */
public interface MessageDao extends GenericDao<Message,Long>{

	/** Get the list of all conversation belongs to the member */
	public List<Message> getConversation(Long senderId);
	
	/** Get the list of all conversation belongs to the member */
	public List<Message> getConversationByOwnerId(Long ownerId,String status);	
	
	/** Get the list of all conversion based on reference message */
	public List<Message> getConversationByReferenceMessage(Long ownerId,Long refMessageId);
	
	/** Remove message by message Id */
	public int deleteMessageById(Long messageId);
	
	
}
