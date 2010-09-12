ALTER TABLE TEST_TUTORIAL_DOCUMENT
 DROP PRIMARY KEY CASCADE;
DROP TABLE TEST_TUTORIAL_DOCUMENT CASCADE CONSTRAINTS;

CREATE TABLE TEST_TUTORIAL_DOCUMENT
(
  TEST_TUTORIAL_DOCUMENT_ID  NUMBER             NOT NULL,
  DOCUMENT_NAME              VARCHAR2(500 BYTE),
  DOCUMENT_INTRO_AUDIO       BLOB,
  DOCUMENT_INTRO_VIDEO       BLOB,
  RECORD_CREATOR_ID          VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_CREATE_DATE         DATE,
  RECORD_LAST_UPDATER_ID     VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_LAST_UPDATED_DATE   DATE,
  AUDIO_FILE_NAME            VARCHAR2(255 BYTE),
  VIDEO_FILE_NAME            VARCHAR2(255 BYTE)
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;



ALTER TABLE TEST_TUTORIAL_DOCUMENT ADD (
  CONSTRAINT TEST_TUTORIAL_DOCUMENT_PK
 PRIMARY KEY
 (TEST_TUTORIAL_DOCUMENT_ID));

ALTER TABLE TEST_TUTORIAL_DOCUMENT ADD (
  CONSTRAINT TEST_TUTORIAL_DOCUMENT_U01
 UNIQUE (DOCUMENT_NAME));



ALTER TABLE TEST_TUTORIAL_MODULES
 DROP PRIMARY KEY CASCADE;
DROP TABLE TEST_TUTORIAL_MODULES CASCADE CONSTRAINTS;

CREATE TABLE TEST_TUTORIAL_MODULES
(
  TEST_TUTORIAL_MODULES_ID   NUMBER             NOT NULL,
  TEST_TUTORIAL_DOCUMENT_ID  NUMBER             NOT NULL,
  TEST_INDICATOR             CHAR(1 BYTE)       NOT NULL,
  MEMBER_TYPE_INDICATOR      NUMBER             NOT NULL,
  MODULE_NAME                VARCHAR2(500 BYTE) NOT NULL,
  MODULE_INTRO_AUDIO         BLOB,
  MODULE_INTRO_VIDEO         BLOB,
  MODULE_TEXT                VARCHAR2(2000 BYTE) NOT NULL,
  RECORD_CREATOR_ID          VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_CREATE_DATE         DATE,
  RECORD_LAST_UPDATER_ID     VARCHAR2(20 BYTE)  NOT NULL,
  RECORD_LAST_UPDATED_DATE   DATE,
  AUDIO_FILE_NAME            VARCHAR2(255 BYTE),
  VIDEO_FILE_NAME            VARCHAR2(255 BYTE),
  MODULE_TITLE               VARCHAR2(500 BYTE),
  SERIAL                     NUMBER,
  TEST_QUESTIONS_COUNT       NUMBER             DEFAULT 10
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


ALTER TABLE TEST_TUTORIAL_MODULES ADD (
  CONSTRAINT TEST_TUTORIAL_MODULES_PK
 PRIMARY KEY
 (TEST_TUTORIAL_MODULES_ID));


ALTER TABLE TEST_TUTORIAL_MODULES ADD (
  CONSTRAINT TEST_TUTORIAL_MODULES_R01 
 FOREIGN KEY (TEST_TUTORIAL_DOCUMENT_ID) 
 REFERENCES TEST_TUTORIAL_DOCUMENT (TEST_TUTORIAL_DOCUMENT_ID));
 
 
 
 
 ALTER TABLE TEST_TUTORIAL_QUESTION_ANS
 DROP PRIMARY KEY CASCADE;
DROP TABLE TEST_TUTORIAL_QUESTION_ANS CASCADE CONSTRAINTS;

CREATE TABLE TEST_TUTORIAL_QUESTION_ANS
(
  TEST_TUTORIAL_QUESTION_ANS_ID  NUMBER         NOT NULL,
  TEST_TUTORIAL_MODULES_ID       NUMBER         NOT NULL,
  PAGE_NUMBER                    NUMBER,
  PAGE_TEXT                      VARCHAR2(2000 BYTE),
  PAGE_AUDIO                     BLOB,
  PAGE_VIDEO                     BLOB,
  QUESTION                       VARCHAR2(500 BYTE),
  QUESTION_CORRECT_ANSWER        VARCHAR2(100 BYTE),
  QUESTION_OPTION_ANSWER_A       VARCHAR2(500 BYTE),
  QUESTION_OPTION_ANSWER_B       VARCHAR2(500 BYTE),
  QUESTION_OPTION_ANSWER_C       VARCHAR2(500 BYTE),
  QUESTION_OPTION_ANSWER_D       VARCHAR2(500 BYTE),
  QUESTION_OPTION_ANSWER_E       VARCHAR2(500 BYTE),
  QUESTION_OPTION_ANSWER_F       VARCHAR2(500 BYTE),
  RECORD_CREATOR_ID              VARCHAR2(20 BYTE),
  RECORD_CREATE_DATE             DATE,
  RECORD_LAST_UPDATER_ID         VARCHAR2(20 BYTE),
  RECORD_LAST_UPDATED_DATE       DATE,
  AUDIO_FILE_NAME                VARCHAR2(255 BYTE),
  VIDEO_FILE_NAME                VARCHAR2(255 BYTE),
  PAGE_TITLE                     VARCHAR2(500 BYTE),
  ISQUESTION                     VARCHAR2(5 BYTE)
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;



ALTER TABLE TEST_TUTORIAL_QUESTION_ANS ADD (
  CONSTRAINT TEST_TUTORIAL_QUESTION_ANS_PK
 PRIMARY KEY
 (TEST_TUTORIAL_QUESTION_ANS_ID));

ALTER TABLE TEST_TUTORIAL_QUESTION_ANS ADD (
  CONSTRAINT TEST_TUTORIAL_QUESTION_ANS_U01
 UNIQUE (TEST_TUTORIAL_MODULES_ID, PAGE_NUMBER));


ALTER TABLE TEST_TUTORIAL_QUESTION_ANS ADD (
  CONSTRAINT TEST_TUTORIAL_QUESTION_ANS_R01 
 FOREIGN KEY (TEST_TUTORIAL_MODULES_ID) 
 REFERENCES TEST_TUTORIAL_MODULES (TEST_TUTORIAL_MODULES_ID));
 
 ALTER TABLE TUTORIAL_TEST
 DROP PRIMARY KEY CASCADE;
DROP TABLE TUTORIAL_TEST CASCADE CONSTRAINTS;

CREATE TABLE TUTORIAL_TEST
(
  TUTORIAL_TEST_ID          NUMBER              NOT NULL,
  DOCUMENT_ID               NUMBER              NOT NULL,
  MODULE_ID                 NUMBER              NOT NULL,
  MEMBER_ID                 NUMBER              NOT NULL,
  TEST_START_TIME           DATE,
  TEST_END_TIME             DATE,
  TOTAL_QUESTIONS           NUMBER,
  TOTAL_CORRECT             NUMBER,
  IS_PASSED                 VARCHAR2(5 BYTE),
  RECORD_CREATOR_ID         VARCHAR2(20 BYTE),
  RECORD_CREATE_DATE        DATE,
  RECORD_LAST_UPDATER_ID    VARCHAR2(20 BYTE),
  RECORD_LAST_UPDATED_DATE  DATE,
  IS_COMPLETED              VARCHAR2(100 BYTE),
  LAST_ACCESS_PAGE          NUMBER
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE UNIQUE INDEX TUTORIAL_TEST_PK ON TUTORIAL_TEST
(TUTORIAL_TEST_ID)
LOGGING
NOPARALLEL;


ALTER TABLE TUTORIAL_TEST ADD (
  CONSTRAINT TUTORIAL_TEST_PK
 PRIMARY KEY
 (TUTORIAL_TEST_ID));


ALTER TABLE TUTORIAL_TEST ADD (
  CONSTRAINT TUTORIAL_TEST_R01 
 FOREIGN KEY (MODULE_ID) 
 REFERENCES TEST_TUTORIAL_MODULES (TEST_TUTORIAL_MODULES_ID));

ALTER TABLE TUTORIAL_TEST ADD (
  CONSTRAINT TUTORIAL_TEST_R02 
 FOREIGN KEY (MEMBER_ID) 
 REFERENCES MEMBER (MEMBER_ID));
 
 ALTER TABLE TUTORIAL_TEST_DETAILS
 DROP PRIMARY KEY CASCADE;
DROP TABLE TUTORIAL_TEST_DETAILS CASCADE CONSTRAINTS;

CREATE TABLE TUTORIAL_TEST_DETAILS
(
  ID                        NUMBER              NOT NULL,
  TUTORIAL_TEST_ID          NUMBER              NOT NULL,
  QUESTION_ID               NUMBER              NOT NULL,
  MENTOR_ANSWER             VARCHAR2(20 BYTE)   NOT NULL,
  IS_CORRECT                VARCHAR2(5 BYTE),
  RECORD_CREATOR_ID         VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_CREATE_DATE        DATE,
  RECORD_LAST_UPDATER_ID    VARCHAR2(20 BYTE)   NOT NULL,
  RECORD_LAST_UPDATED_DATE  DATE
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;



ALTER TABLE TUTORIAL_TEST_DETAILS ADD (
  CONSTRAINT TUTORIAL_TEST_DETAILS_PK
 PRIMARY KEY
 (ID));

ALTER TABLE TUTORIAL_TEST_DETAILS ADD (
  CONSTRAINT TEST_QUESTION_UK1
 UNIQUE (TUTORIAL_TEST_ID, QUESTION_ID));


ALTER TABLE TUTORIAL_TEST_DETAILS ADD (
  CONSTRAINT TUTORIAL_TEST_TEST_DETAILS_FK1 
 FOREIGN KEY (TUTORIAL_TEST_ID) 
 REFERENCES TUTORIAL_TEST (TUTORIAL_TEST_ID));
 