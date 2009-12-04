package com.intrigueit.myc2i.common;

public class CommonConstants {

	public static final String CURRENT_MEMMBER_ID = "CURRENT_MEMBER_ID";

	public static final String CURRENT_MEMMBER = "CURRENT_MEMBER";

	/** User session object key. */
    public static final String SESSION_MEMBER_KEY = "LOGIN_USER";
    
    /** User name session object key. */
    public static final String SESSION_MEMBER_EMAIL = "USER_EMAIL";	
    
    public enum ACTIVITY_STATUS { PENDING ,ACCEPTED,REJECTED,COMPLETED,SENT };
    
    public enum STATUS { Yes, No };
     
    public static final String ADD = "ADD";
    public static final String EDIT = "EDIT";
    public static final String DELETE = "DELETE";
    
    public static final Long MENTOR = 15L;
    public static final Long LEAD_MENTOR = 16L;
    public static final Long PROTEGE = 17L;
    public static final Long ADMIN = 18L;
    
    public static final Long ROLE_ADMINISTRATOR = 19L;
    public static final Long ROLE_SUPERVISOR = 20L;
    public static final Long ROLE_OPERATOR = 21L;
    public static final Long ROLE_GUEST = 22L;
    
    public static final Long ACTIVITY_TYPE_MESSAGE = 27L;
    public static final String ACTIVITY_TYPE_MENTOR_REQUEST = "Mentor Request";
    public static final String ACTIVITY_TYPE_PROTEGE_REQUEST = "Protege Request";
    public static final Long ACTIVITY_TYPE_MENTOR_RELEASE = 24L;
    public static final Long ACTIVITY_TYPE_PROTEGE_RELEASE = 23L;
    
    public static final String STORY_MENTOR = "MENTOR";
    public static final String STORY_PROTEGE = "PROTEGE";
    
    	
}
