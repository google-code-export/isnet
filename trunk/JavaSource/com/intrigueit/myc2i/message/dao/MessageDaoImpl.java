/**
 * @(#)MessageDaoImpl.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.message.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.message.domain.Message;

/**
 * This Class is the DAO implementation of MessageDao interface
 * 
 * @version 1.00 August 05, 2010
 * @author Shamim Ahmmed
 * 
 */

@Repository
public final class MessageDaoImpl  extends GenericDaoImpl<Message, Long> implements  MessageDao{

	@Override
	public List<Message> getConversation(Long senderId) {
		String clause = " t.sender.memberId = ?1 ORDER BY t.createdTime ASC ";
		List<Message> messages = loadByClause(clause, new Object[] { senderId });
		return messages;
	}

	@Override
	public List<Message> getConversationByReferenceMessage(Long refMessageId) {
		String clause = " t.refMessageId = ?1 ORDER BY t.createdTime ASC ";
		List<Message> messages = loadByClause(clause, new Object[] { refMessageId });
		return messages;
	}

}
