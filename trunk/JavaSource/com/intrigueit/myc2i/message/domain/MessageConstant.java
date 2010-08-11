package com.intrigueit.myc2i.message.domain;

public class MessageConstant {

	/** Message status */
	public enum MessageStatus {
	    SENT, RECEIVED, DRAFT
	}
	
	/** No of messages per page in message page */
	public static final Integer MESSAGE_PER_PAGE = 20;

	/** No of messages per page in message page */
	public static final Integer MESSAGE_PREVIEW_LENGTH = 80;
	
	/** Message reading status */
	public enum MessageReadingStatus {
	    READ, UNREAD
	}

}

