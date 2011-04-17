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


	public List<Message> getConversation(Long senderId) {
		String clause = " t.sender.memberId = ?1 ORDER BY t.createdTime ASC ";
		List<Message> messages = loadByClause(clause, new Object[] { senderId });
		return messages;
	}


	public List<Message> getConversationByReferenceMessage(Long ownerId,Long refMessageId) {
		String clause = "t.ownerId = ?1 and ( t.refMessageId = ?2 OR t.messageId = ?3) ORDER BY t.createdTime ASC ";
		List<Message> messages = loadByClause(clause, new Object[] {ownerId, refMessageId, refMessageId });
		return messages;
	}


	public int deleteMessageById(Long messageId) {
		String clause = " t.messageId = ?1 ";
		int rowEffected = deleteByClause(clause, new Object[]{messageId});
		return rowEffected;
	}


	public List<Message> getConversationByOwnerId(Long ownerId,String status,int startIndex, int pageSize) {
		String clause = " where t.ownerId = ?1 and t.status = ?2 ORDER BY t.createdTime DESC ";
		List<Message> messages = loadTopResultsByConditions(startIndex,pageSize, clause, new Object[] { ownerId,status });
		return messages;
	}


	public List<Message> getUnReadConversationByOwnerId(Long ownerId,
			String status, String readStatus,int startIndex, int pageSize) {
		String clause = " where t.ownerId = ?1 and t.status = ?2 and t.readStatus =?3 ORDER BY t.createdTime DESC ";
		List<Message> messages = loadTopResultsByConditions(startIndex,pageSize,clause, new Object[] { ownerId,status, readStatus });
		return messages;
	}


	public int getMessageCount(Long ownerId, String status) {
		String clause = " t.ownerId = ?1 and t.readStatus= ?2  ";
		int rowsCount = this.getRowCount(clause, new Object[] { ownerId,status });
		return rowsCount;
	}

}
