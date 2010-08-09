package com.intrigueit.myc2i.message.domain;

public class MessageConstant {

	public static final String MESSAGE_STATUS = "SENT";
	
	/** No of messages per page in message page */
	public static final Integer MESSAGE_PER_PAGE = 20;

	/** Message reading status */
	public enum MessageReadingStatus {
	    READ, UNREAD
	}

}

