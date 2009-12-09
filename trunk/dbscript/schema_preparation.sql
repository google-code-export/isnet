DROP TABLE APPLICATION_PAGES CASCADE CONSTRAINTS;
CREATE TABLE APPLICATION_PAGES ( 
PAGE_ID NUMBER NOT NULL, 
PAGE_URL VARCHAR(255 BYTE) NOT NULL 
) ;
alter table APPLICATION_PAGES add constraint "APPLICATION_PAGES_PK" primary key ("PAGE_ID");

DROP TABLE ROLE_PAGE_ACCESS CASCADE CONSTRAINTS;
CREATE TABLE ROLE_PAGE_ACCESS ( 
ROLE_PAGE_ACCESS_ID NUMBER NOT NULL,
MEMBER_ROLE_ID NUMBER NOT NULL, 
MEMBER_TYPE_ID NUMBER NOT NULL, 
PAGE_ID NUMBER NOT NULL 
);
alter table ROLE_PAGE_ACCESS add constraint "ROLE_PAGE_ACCESS_PK" primary key ("ROLE_PAGE_ACCESS_ID");

CREATE TABLE MEMBER_STORY
(
  MEMBER_STORY_ID               NUMBER  NOT NULL,
  MEMBER_ID                     NUMBER  NOT NULL,
  MEMBER_STORY_DATE             DATE   NOT NULL,
  MEMBER_STORY_DESCRIPTION      VARCHAR2(500 BYTE) NOT NULL,
  STORY_TITLE                   VARCHAR2(200 BYTE) NOT NULL,
  MEMBER_STORY_AUDIO_VIDEO      BLOB,
  RECORD_CREATOR_ID             VARCHAR2(20 BYTE) NOT NULL,
  RECORD_CREATE_DATE            DATE,
  DELETED_BY_ADMIN_USER_IND     VARCHAR2(20 BYTE),
  RECORD_LAST_UPDATER_ID        VARCHAR2(20 BYTE) NOT NULL,
  SCREENED_BY_MEMBER_ID         VARCHAR2(20 BYTE),
  RECORD_LAST_UPDATED_DATE      DATE,
  APPROVED_FOR_PUBLISH_IND      VARCHAR2(20 BYTE) NOT NULL,
  MEMBER_PERMISSION_TO_PUBLISH  VARCHAR2(20 BYTE) NOT NULL,
  APPROVAL_DATE                 DATE,
  NUMBER_OF_VOTES_RECEIVED      NUMBER,
  WEEK_WINNER_INDICATOR         DATE,
  STORY_CATEGORY 				VARCHAR2(20 BYTE) NOT NULL
);

--Sequences
CREATE SEQUENCE   MEMBER_STORY_ID_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE;

	
CREATE TABLE MEMBER_LOG
(
  MEMBER_LOG_ID					NUMBER		NOT NULL,
  MEMBER_LOG_TYPE_ID			NUMBER			NOT NULL,
  FROM_MEMBER_ID                NUMBER          NOT NULL,
  TO_MEMBER_ID                  NUMBER          NOT NULL,
  MEMBER_LOG_DATE_TIME          TIMESTAMP        NOT NULL,
  MEMBER_LOG_TOPIC				VARCHAR2(250 BYTE)	 NOT NULL,
  MEMBER_LOG_ENTRY_DESCRIPTION  VARCHAR2(500 BYTE) NOT NULL,
  RECORD_CREATOR_ID             VARCHAR2 (20 BYTE)NOT NULL,
  RECORD_CREATED_DATE           DATE,
  RECORD_LAST_UPDATER_ID        VARCHAR2(20 BYTE) NOT NULL,
  RECORD_LAST_UPDATED_DATE      DATE,
  EVENT_DURATION                NUMBER,
  STATUS						VARCHAR2(20 BYTE) NOT NULL
);

--Sequences
CREATE SEQUENCE   MEMBER_LOG_ID_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE;

CREATE SEQUENCE UDVALUE_ID_SEQ  START WITH 1  MAXVALUE 999999999999999999999999999  MINVALUE 1  NOCYCLE  NOCACHE  NOORDER;
  
CREATE TABLE PAYPAL_IPN_LOG
(
  IPN_LOG_ID      NUMBER                        NOT NULL,
  PAYPAL_TXN_ID   VARCHAR2(50)                  NOT NULL,
  ITEM_NAME       VARCHAR2(250)                 NOT NULL,
  PAYMENT_STATUS  VARCHAR2(100)                 NOT NULL,
  MC_GROSS        NUMBER                        NOT NULL,
  MC_CURRENCY     VARCHAR2(50)                  NOT NULL,
  PAYER_EMAIL     VARCHAR2(250)                 NOT NULL,
  TXN_TYPE        VARCHAR2(100)                 NOT NULL,
  PAYMENT_DATE    DATE                          NOT NULL,
  NOTIFY_VERSION  NUMBER                        NOT NULL,
  MEMBER_ID       NUMBER                        NOT NULL
);


CREATE SEQUENCE IPN_LOG_ID_SEQSTART WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE NOCYCLE NOORDER;

DROP SEQUENCE MEMBER_ID_SEQ;

CREATE SEQUENCE MEMBER_ID_SEQ  START WITH 1  MAXVALUE 999999999999999999999999999  MINVALUE 1  NOCYCLE  NOCACHE  NOORDER;

CREATE TABLE ZIPCODEDATA
(
  ZIPCODE    VARCHAR2(10 BYTE),
  CITY       VARCHAR2(100 BYTE),
  STATE      VARCHAR2(100 BYTE),
  LATITUDE   NUMBER,
  LONGITUDE  NUMBER,
  TIMEZONE   NUMBER,
  DST        NUMBER
);

--- Start  
DROP TABLE ITEM_VENDOR CASCADE CONSTRAINTS;
CREATE TABLE ITEM_VENDOR
(
  VENDOR_ID                 NUMBER              NOT NULL,
  VENDOR_NAME               VARCHAR2(50 BYTE)   NOT NULL,
  VENDOR_EMAIL              VARCHAR2(50 BYTE)   NOT NULL,
  VENDOR_ADDRESS            VARCHAR2(100 BYTE)  NOT NULL,
  VENDOR_PHONE              NUMBER,
  RECORD_CREATOR_ID         VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_CREATE_DATE        DATE,
  RECORD_LAST_UPDATER_ID    VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_LAST_UPDATED_DATE  DATE
);

DROP TABLE LOCAL_CHAPTER CASCADE CONSTRAINTS;
CREATE TABLE LOCAL_CHAPTER
(
  LOCAL_CHAPTER_ID              NUMBER          NOT NULL,
  CHAPTER_LEAD_MEMBER_ID        NUMBER,
  LOCAL_CHAPTER_NAME            VARCHAR2(100 BYTE),
  LOCAL_CHAPTER_STREET_ADDRESS  VARCHAR2(100 BYTE),
  LOCAL_CHAPTER_CITY            VARCHAR2(50 BYTE),
  LOCAL_CHAPTER_STATE           VARCHAR2(50 BYTE),
  LOCAL_CHAPTER_ZIP             VARCHAR2(20 BYTE),
  LOCAL_CHAPTER_EMAIL_ADDRESS   VARCHAR2(50 BYTE),
  LOCAL_CHAPTER_URL             VARCHAR2(50 BYTE),
  LOCAL_CHAPTER_PHONE           VARCHAR2(20 BYTE),
  LOCAL_CHAPTER_FAX             VARCHAR2(20 BYTE),
  LOCAL_CHAPTER_COUNTRY         VARCHAR2(20 BYTE) NOT NULL,
  RECORD_CREATOR_ID             VARCHAR2(20 BYTE) NOT NULL,
  RECORD_CREATED_DATE           DATE,
  RECORD_LAST_UPDATER_ID        VARCHAR2(20 BYTE) NOT NULL,
  RECORD_LAST_UPDATED_DATE      DATE
);

DROP TABLE TEST_TUTORIAL_DOCUMENT CASCADE CONSTRAINTS;
CREATE TABLE TEST_TUTORIAL_DOCUMENT
(
  TEST_TUTORIAL_DOCUMENT_ID  NUMBER             NOT NULL,
  DOCUMENT_NAME              VARCHAR2(100 BYTE),
  DOCUMENT_INTRO_AUDIO       BLOB,
  DOCUMENT_INTRO_VIDEO       BLOB,
  RECORD_CREATOR_ID          VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_CREATE_DATE         DATE,
  RECORD_LAST_UPDATER_ID     VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_LAST_UPDATED_DATE   DATE
);

DROP TABLE TEST_TUTORIAL_MODULES CASCADE CONSTRAINTS;
CREATE TABLE TEST_TUTORIAL_MODULES
(
  TEST_TUTORIAL_MODULES_ID   NUMBER             NOT NULL,
  TEST_TUTORIAL_DOCUMENT_ID  NUMBER             NOT NULL,
  TEST_INDICATOR             CHAR(1 BYTE)       NOT NULL,
  MEMBER_TYPE_INDICATOR      NUMBER             NOT NULL,
  MODULE_NAME                VARCHAR2(100 BYTE) NOT NULL,
  MODULE_INTRO_AUDIO         BLOB,
  MODULE_INTRO_VIDEO         BLOB,
  MODULE_TEXT                VARCHAR2(250 BYTE) NOT NULL,
  RECORD_CREATOR_ID          VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_CREATE_DATE         DATE,
  RECORD_LAST_UPDATER_ID     VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_LAST_UPDATED_DATE   DATE
);

DROP TABLE TEST_TUTORIAL_QUESTION_ANS CASCADE CONSTRAINTS;
CREATE TABLE TEST_TUTORIAL_QUESTION_ANS
(
  TEST_TUTORIAL_QUESTION_ANS_ID  NUMBER         NOT NULL,
  TEST_TUTORIAL_MODULES_ID       NUMBER         NOT NULL,
  PAGE_NUMBER                    NUMBER         NOT NULL,
  PAGE_TEXT                      VARCHAR2(250 BYTE) NOT NULL,
  PAGE_AUDIO                     BLOB,
  PAGE_VIDEO                     BLOB,
  QUESTION                       VARCHAR2(150 BYTE) NOT NULL,
  QUESTION_CORRECT_ANSWER        VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_A       VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_B       VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_C       VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_D       VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_E       VARCHAR2(100 BYTE) NOT NULL,
  QUESTION_OPTION_ANSWER_F       VARCHAR2(100 BYTE) NOT NULL,
  RECORD_CREATOR_ID              VARCHAR2(20 BYTE) NOT NULL,
  RECORD_CREATE_DATE             DATE,
  RECORD_LAST_UPDATER_ID         VARCHAR2(20 BYTE) NOT NULL,
  RECORD_LAST_UPDATED_DATE       DATE
);

DROP TABLE TRAINING_ITEM CASCADE CONSTRAINTS;
CREATE TABLE TRAINING_ITEM
(
  ITEM_ID                   NUMBER              NOT NULL,
  VENDOR_ID                 NUMBER              NOT NULL,
  ITEM_DESCRIPTION          VARCHAR2(150 BYTE)  NOT NULL,
  ITEM_E_INDICATOR          VARCHAR2(20 BYTE)   NOT NULL,
  ITEM_SUBSCRIPTION_IND     VARCHAR2(20 BYTE)   NOT NULL,
  ITEM_E_STORAGE_LOCATI     VARCHAR2(20 BYTE)   NOT NULL,
  ITEM_PURCHASE_COST        NUMBER,
  ITEM_SALES_PRICE          NUMBER,
  RECORD_CREATOR_ID         VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_CREATE_DATE        DATE,
  RECORD_LAST_UPDATER_ID    VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_LAST_UPDATED_DATE  DATE,
  ITEM_VERSION              VARCHAR2(20 BYTE)   NOT NULL,
  ITEM_LANGUAGE             VARCHAR2(20 BYTE)   NOT NULL,
  ITEM_IMAGE                BLOB,
  ITEM_AVAILABILITY         CHAR(1 CHAR)
);

DROP TABLE USER_DEFINED_VALUES CASCADE CONSTRAINTS;
CREATE TABLE USER_DEFINED_VALUES
(
  USER_DEFINED_VALUES_ID          NUMBER        NOT NULL,
  USER_DEFINED_VALUES_DESCRIPTIO  VARCHAR2(50 BYTE),
  RECORD_CREATOR_ID               VARCHAR2(20 BYTE) NOT NULL,
  RECORD_CREATED_DATE             DATE,
  RECORD_LAST_UPDATER_ID          VARCHAR2(20 BYTE) NOT NULL,
  RECORD_LAST_UPDATED_DATE        DATE,
  USER_DEFINED_VALUES_CATEGORY    VARCHAR2(20 BYTE) NOT NULL,
  USER_DEFINED_VALUES_VALUE       VARCHAR2(20 BYTE) NOT NULL
);

--Sequence
DROP SEQUENCE CHAPTER_ID_SEQ;
CREATE SEQUENCE CHAPTER_ID_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE TRAINING_ITEM_SEQ;
CREATE SEQUENCE TRAINING_ITEM_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE VENDOR_ID_SEQ;
CREATE SEQUENCE VENDOR_ID_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE QUESTION_ANS_SEQ;
CREATE SEQUENCE QUESTION_ANS_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE MODULES_ID_SEQ;
CREATE SEQUENCE MODULES_ID_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE DOCUMENT_ID_SEQ;
CREATE SEQUENCE DOCUMENT_ID_SEQ INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE NOORDER;

DROP SEQUENCE UDVALUE_ID_SEQ;
CREATE SEQUENCE UDVALUE_ID_SEQ INCREMENT BY 1 START WITH 100 NOCYCLE NOCACHE NOORDER;

-- End