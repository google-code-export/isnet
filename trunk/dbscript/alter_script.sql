-- alter table MEMBER_STORY
alter table MEMBER_STORY add constraint "MEMBER_STORY_PK" primary key ("MEMBER_STORY_ID");

alter table MEMBER_STORY add constraint "MEMBER_STORY_UK1" unique ("MEMBER_ID","MEMBER_STORY_DATE");

-- alter table MEMBER_LOG
ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_PK PRIMARY KEY (MEMBER_LOG_ID) ENABLE VALIDATE;
 
ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_UK1 UNIQUE (FROM_MEMBER_ID, MEMBER_LOG_TYPE_ID, MEMBER_LOG_DATE_TIME) ENABLE VALIDATE;

ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_MEMBER_FK1 FOREIGN KEY (FROM_MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ENABLE VALIDATE;

ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_MEMBER_FK3 FOREIGN KEY (TO_MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ENABLE VALIDATE;

ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_USER_DEF_VAL_FK2 FOREIGN KEY (MEMBER_LOG_TYPE_ID) REFERENCES USER_DEFINED_VALUES (USER_DEFINED_VALUES_ID) ENABLE VALIDATE;
 
-- alter table USER_DEFINED_VALUES
alter table "USER_DEFINED_VALUES" add constraint "USER_DEFINED_VALUES_PK" primary key ("USER_DEFINED_VALUES_ID");

ALTER TABLE USER_DEFINED_VALUES MODIFY(USER_DEFINED_VALUES_VALUE VARCHAR2(250 BYTE));

ALTER TABLE USER_DEFINED_VALUES MODIFY(USER_DEFINED_VALUES_DESCRIPTIO VARCHAR2(250 BYTE));

ALTER TABLE USER_DEFINED_VALUES MODIFY(USER_DEFINED_VALUES_DESCRIPTIO VARCHAR2(100 BYTE));

ALTER TABLE USER_DEFINED_VALUES MODIFY(USER_DEFINED_VALUES_VALUE VARCHAR2(100 BYTE));

-- Alter table PAYPAL_IPN_LOG
ALTER TABLE PAYPAL_IPN_LOG ADD (  PRIMARY KEY (IPN_LOG_ID));

--Alter table MEMBER;
ALTER TABLE MEMBER ADD CONSTRAINT MEMBER_PK PRIMARY KEY (MEMBER_ID) ENABLE VALIDATE;
  
ALTER TABLE MEMBER MODIFY(MEMBER_STREET_ADDRESS  NULL);

ALTER TABLE MEMBER MODIFY(MEMBER_CITY  NULL);

ALTER TABLE MEMBER MODIFY(MEMBER_STATE  NULL);

ALTER TABLE MEMBER MODIFY(MEMBER_COUNTRY  NULL);

ALTER TABLE MEMBER MODIFY(MEMBER_MARITAL_STATUS  NULL);

ALTER TABLE MEMBER MODIFY(MEMBER_MAZHAB  NULL);

ALTER TABLE MEMBER ADD (MENTORED_BY_DATE DATE);

ALTER TABLE MEMBER MODIFY(MEMBER_STATE NUMBER);

ALTER TABLE MEMBER MODIFY(MEMBER_COUNTRY NUMBER);

ALTER TABLE MEMBER MODIFY(MEMBER_CELL_PHONE_NUMBER VARCHAR2(20 BYTE));

ALTER TABLE MEMBER MODIFY(MEMBER_WORK_PHONE_NUMBER VARCHAR2(20 BYTE));

ALTER TABLE MEMBER MODIFY(MEMBER_HOME_PHONE_NUMBER VARCHAR2(20 BYTE));

ALTER TABLE MEMBER ADD CONSTRAINT MEMBER_STATE_FK1
 FOREIGN KEY (MEMBER_STATE) REFERENCES USER_DEFINED_VALUES (USER_DEFINED_VALUES_ID) ENABLE VALIDATE;
 
ALTER TABLE MEMBER ADD CONSTRAINT MEMBER_COUNTRY_FK2
 FOREIGN KEY (MEMBER_COUNTRY) REFERENCES USER_DEFINED_VALUES (USER_DEFINED_VALUES_ID) ENABLE VALIDATE;

alter table member modify ( MEMBER_GENDER_INDICATOR varchar2(18));

alter table member add (email_notification  varchar2(20));

--Alter table TEST_TUTORIAL_MODULES
alter table TEST_TUTORIAL_MODULES add (audio_file_name  varchar2(255));

alter table TEST_TUTORIAL_MODULES add (video_file_name  varchar2(255));

--Alter table TEST_TUTORIAL_DOCUMENT
alter table TEST_TUTORIAL_DOCUMENT add (audio_file_name  varchar2(255));

alter table TEST_TUTORIAL_DOCUMENT add (video_file_name  varchar2(255));

--Alter table TEST_TUTORIAL_QUESTION_ANS
alter table TEST_TUTORIAL_QUESTION_ANS add (audio_file_name  varchar2(255));

alter table TEST_TUTORIAL_QUESTION_ANS add (video_file_name  varchar2(255));

alter table TEST_TUTORIAL_QUESTION_ANS add (page_title  varchar2(500));

--Alter table TEST_TUTORIAL_MODULES
alter table TEST_TUTORIAL_MODULES add (module_title  varchar2(500));

alter table TEST_TUTORIAL_QUESTION_ANS modify (page_text  varchar2(2000));

alter table TEST_TUTORIAL_MODULES modify (module_text  varchar2(2000));

--Alter table ZIPCODEDATA
ALTER TABLE ZIPCODEDATA MODIFY(ZIPCODE  NOT NULL);

ALTER TABLE ZIPCODEDATA ADD CONSTRAINT ZIPCODE_PK  PRIMARY KEY (ZIPCODE) ENABLE VALIDATE;

--Alter table ITEM_VENDOR
alter table "ITEM_VENDOR" add constraint "ITEM_VENDOR_PK" primary key ("VENDOR_ID");

alter table "LOCAL_CHAPTER" add constraint "LOCAL_CHAPTER_PK" primary key ("LOCAL_CHAPTER_ID");

alter table "TEST_TUTORIAL_DOCUMENT" add constraint "TEST_TUTORIAL_DOCUMENT_PK" primary key ("TEST_TUTORIAL_DOCUMENT_ID");

alter table "TEST_TUTORIAL_MODULES" add constraint "TEST_TUTORIAL_MODULES_PK" primary key ("TEST_TUTORIAL_MODULES_ID");

alter table "TEST_TUTORIAL_QUESTION_ANS" add constraint "TEST_TUTORIAL_QUESTION_ANS_PK" primary key ("TEST_TUTORIAL_QUESTION_ANS_ID");

alter table "TRAINING_ITEM" add constraint "TRAINING_ITEM_PK" primary key ("ITEM_ID");
