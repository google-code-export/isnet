package com.intrigueit.myc2i.tutorial.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="TEST_TUTORIAL_QUESTION_ANS")
public class TestTutorialQuestionAns implements Serializable {
  private static final long serialVersionUID = 2878691417147392110L;

  @Id
  @Column(name="TEST_TUTORIAL_QUESTION_ANS_ID")
  @GeneratedValue(generator="questionAnsSeq")
  @SequenceGenerator(name="questionAnsSeq",sequenceName="QUESTION_ANS_SEQ", allocationSize=1,initialValue=1)
  private long questionAnsId;

  @Column(name="TEST_TUTORIAL_MODULES_ID")
  private Long modulesId;
  
  @Column(name="PAGE_NUMBER")
  private Long pageNumber;
  
  @NotNull
  @NotEmpty
  @Length(min=1,max=2000)
  @Column(name="PAGE_TEXT",nullable = false, length = 2000)
  private String pageText;
  
  @NotNull
  @NotEmpty
  @Length(min=1,max=500)
  @Column(name="PAGE_TITLE",nullable = false, length = 500)
  private String pageTitle;
  
  @Column(name="PAGE_AUDIO")
  @Lob
  private byte[] pageAudio;

  @Column(name="PAGE_VIDEO")
  @Lob
  private byte[] pageVideo;
  
  @Column(name="AUDIO_FILE_NAME")
  private String audioFileName;
  
  @Column(name="VIDEO_FILE_NAME")
  private String videoFileName;
  
  @NotNull
  @NotEmpty
  @Length(max=150)
  @Column(name="QUESTION",length = 150)
  private String question;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_CORRECT_ANSWER",nullable = false, length = 100)
  private String questionCorrectAnswer;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_A",nullable = false, length = 100)
  private String questionOptionAnswerA;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_B",nullable = false, length = 100)
  private String questionOptionAnswerB;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_C",nullable = false, length = 100)
  private String questionOptionAnswerC;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_D",nullable = false, length = 100)
  private String questionOptionAnswerD;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_E",nullable = false, length = 100)
  private String questionOptionAnswerE;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="QUESTION_OPTION_ANSWER_F",nullable = false, length = 100)
  private String questionOptionAnswerF;

  @Column(name="RECORD_CREATOR_ID")
  private String recordCreatorId;

  @Column(name="RECORD_CREATE_DATE")
  private Date recordCreateDate;

  @Column(name="RECORD_LAST_UPDATER_ID")
  private String recordLastUpdaterId;

  @Column(name="RECORD_LAST_UPDATED_DATE")
  private Date recordLastUpdatedDate;
  
  public TestTutorialQuestionAns() {
    super();
  }
  
  public TestTutorialQuestionAns(long questionAnsId,Long pageNumber,String pageTitle) {
    this.questionAnsId = questionAnsId;
    this.pageNumber = pageNumber;
    this.pageTitle = pageTitle;
  }
  
  /**
   * @return the questionAnsId
   */
  public long getQuestionAnsId() {
    return questionAnsId;
  }

  /**
   * @param questionAnsId the questionAnsId to set
   */
  public void setQuestionAnsId(long questionAnsId) {
    this.questionAnsId = questionAnsId;
  }

  /**
   * @return the modulesId
   */
  public Long getModulesId() {
    return modulesId;
  }

  /**
   * @param modulesId the modulesId to set
   */
  public void setModulesId(Long modulesId) {
    this.modulesId = modulesId;
  }

  /**
   * @return the question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * @param question the question to set
   */
  public void setQuestion(String question) {
    this.question = question;
  }

  /**
   * @return the questionCorrectAnswer
   */
  public String getQuestionCorrectAnswer() {
    return questionCorrectAnswer;
  }

  /**
   * @param questionCorrectAnswer the questionCorrectAnswer to set
   */
  public void setQuestionCorrectAnswer(String questionCorrectAnswer) {
    this.questionCorrectAnswer = questionCorrectAnswer;
  }

  /**
   * @return the questionOptionAnswerA
   */
  public String getQuestionOptionAnswerA() {
    return questionOptionAnswerA;
  }

  /**
   * @param questionOptionAnswerA the questionOptionAnswerA to set
   */
  public void setQuestionOptionAnswerA(String questionOptionAnswerA) {
    this.questionOptionAnswerA = questionOptionAnswerA;
  }

  /**
   * @return the questionOptionAnswerB
   */
  public String getQuestionOptionAnswerB() {
    return questionOptionAnswerB;
  }

  /**
   * @param questionOptionAnswerB the questionOptionAnswerB to set
   */
  public void setQuestionOptionAnswerB(String questionOptionAnswerB) {
    this.questionOptionAnswerB = questionOptionAnswerB;
  }

  /**
   * @return the questionOptionAnswerC
   */
  public String getQuestionOptionAnswerC() {
    return questionOptionAnswerC;
  }

  /**
   * @param questionOptionAnswerC the questionOptionAnswerC to set
   */
  public void setQuestionOptionAnswerC(String questionOptionAnswerC) {
    this.questionOptionAnswerC = questionOptionAnswerC;
  }

  /**
   * @return the questionOptionAnswerD
   */
  public String getQuestionOptionAnswerD() {
    return questionOptionAnswerD;
  }

  /**
   * @param questionOptionAnswerD the questionOptionAnswerD to set
   */
  public void setQuestionOptionAnswerD(String questionOptionAnswerD) {
    this.questionOptionAnswerD = questionOptionAnswerD;
  }

  /**
   * @return the questionOptionAnswerE
   */
  public String getQuestionOptionAnswerE() {
    return questionOptionAnswerE;
  }

  /**
   * @param questionOptionAnswerE the questionOptionAnswerE to set
   */
  public void setQuestionOptionAnswerE(String questionOptionAnswerE) {
    this.questionOptionAnswerE = questionOptionAnswerE;
  }

  /**
   * @return the questionOptionAnswerF
   */
  public String getQuestionOptionAnswerF() {
    return questionOptionAnswerF;
  }

  /**
   * @param questionOptionAnswerF the questionOptionAnswerF to set
   */
  public void setQuestionOptionAnswerF(String questionOptionAnswerF) {
    this.questionOptionAnswerF = questionOptionAnswerF;
  }

  /**
   * @return the recordCreatorId
   */
  public String getRecordCreatorId() {
    return recordCreatorId;
  }

  /**
   * @param recordCreatorId the recordCreatorId to set
   */
  public void setRecordCreatorId(String recordCreatorId) {
    this.recordCreatorId = recordCreatorId;
  }

  /**
   * @return the recordCreateDate
   */
  public Date getRecordCreateDate() {
    return recordCreateDate;
  }

  /**
   * @param recordCreateDate the recordCreateDate to set
   */
  public void setRecordCreateDate(Date recordCreateDate) {
    this.recordCreateDate = recordCreateDate;
  }

  /**
   * @return the recordLastUpdaterId
   */
  public String getRecordLastUpdaterId() {
    return recordLastUpdaterId;
  }

  /**
   * @param recordLastUpdaterId the recordLastUpdaterId to set
   */
  public void setRecordLastUpdaterId(String recordLastUpdaterId) {
    this.recordLastUpdaterId = recordLastUpdaterId;
  }

  /**
   * @return the recordLastUpdatedDate
   */
  public Date getRecordLastUpdatedDate() {
    return recordLastUpdatedDate;
  }

  /**
   * @param recordLastUpdatedDate the recordLastUpdatedDate to set
   */
  public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
    this.recordLastUpdatedDate = recordLastUpdatedDate;
  }
  
  /**
   * @return the pageNumber
   */
  public Long getPageNumber() {
    return pageNumber;
  }

  /**
   * @param pageNumber the pageNumber to set
   */
  public void setPageNumber(Long pageNumber) {
    this.pageNumber = pageNumber;
  }
  
  /**
   * @return the pageText
   */
  public String getPageText() {
    return pageText;
  }

  /**
   * @param pageText the pageText to set
   */
  public void setPageText(String pageText) {
    this.pageText = pageText;
  }  
  
  /**
   * @return the pageTitle
   */
  public String getPageTitle() {
    return pageTitle;
  }

  /**
   * @param pageTitle the pageTitle to set
   */
  public void setPageTitle(String pageTitle) {
    this.pageTitle = pageTitle;
  }

  /**
   * @return the pageAudio
   */
  public byte[] getPageAudio() {
    return pageAudio;
  }

  /**
   * @param pageAudio the pageAudio to set
   */
  public void setPageAudio(byte[] pageAudio) {
    this.pageAudio = pageAudio;
  }

  /**
   * @return the pageVideo
   */
  public byte[] getPageVideo() {
    return pageVideo;
  }

  /**
   * @param pageVideo the pageVideo to set
   */
  public void setPageVideo(byte[] pageVideo) {
    this.pageVideo = pageVideo;
  }
  
  /**
   * @return the audioFileName
   */
  public String getAudioFileName() {
    return audioFileName;
  }

  /**
   * @param audioFileName the audioFileName to set
   */
  public void setAudioFileName(String audioFileName) {
    this.audioFileName = audioFileName;
  }

  /**
   * @return the videoFileName
   */
  public String getVideoFileName() {
    return videoFileName;
  }

  /**
   * @param videoFileName the videoFileName to set
   */
  public void setVideoFileName(String videoFileName) {
    this.videoFileName = videoFileName;
  }

}
