package com.intrigueit.myc2i.message.domain;

public class MessageConstant {

	/** Message status */
	public enum MessageStatus {
	    SENT, RECEIVED, DRAFT
	}
	
	/** No of messages per page in message page */
	public static final Integer MESSAGE_PER_PAGE = 5;
	
	public static final String MESSAGE_INBOX = "INBOX";
	public static final String MESSAGE_SENT = "SENT";

	/** No of messages per page in message page */
	public static final Integer MESSAGE_PREVIEW_LENGTH = 80;
	
	public static final String BACK_TO_DETAIL_PAGE = "BACK_TO_DETAIL_PAGE";
	public static final String BACK_TO_MSG_MAIN_PAGE = "BACK_TO_MSG_MAIN_PAGE";
	
	/** Message reading status */
	public enum MessageReadingStatus {
	    READ, UNREAD
	}

}

