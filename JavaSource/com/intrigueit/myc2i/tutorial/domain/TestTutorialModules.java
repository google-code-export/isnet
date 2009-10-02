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

import com.sun.istack.internal.NotNull;

@Entity
@Table(name="TEST_TUTORIAL_MODULES")
public class TestTutorialModules implements Serializable {
  
  private static final long serialVersionUID = 6148154328394694589L;

  @Id
  @Column(name="TEST_TUTORIAL_MODULES_ID")
  @GeneratedValue(generator="modulesSeq")
  @SequenceGenerator(name="modulesSeq",sequenceName="MODULES_ID_SEQ", allocationSize=1,initialValue=1)
  private long modulesId;

  @Column(name="TEST_TUTORIAL_DOCUMENT_ID")
  private Long documentId;

  @NotNull
  @NotEmpty
  @Length(max=1)
  @Column(name="TEST_INDICATOR",nullable = false, length = 1)
  private String testIndicator;

  @Column(name="MEMBER_TYPE_INDICATOR")
  private Long memberTypeIndicator;

  @NotNull
  @NotEmpty
  @Length(min=1,max=100)
  @Column(name="MODULE_NAME",nullable = false, length = 100)
  private String moduleName;

  @Column(name="MODULE_INTRO_AUDIO")
  @Lob
  private byte[] moduleIntroAudio;

  @Column(name="MODULE_INTRO_VIDEO")
  @Lob
  private byte[] moduleIntroVideo;
  
  @Column(name="AUDIO_FILE_NAME")
  private String audioFileName;
  
  @Column(name="VIDEO_FILE_NAME")
  private String videoFileName;
  
  @NotNull
  @NotEmpty
  @Length(min=1,max=2000)
  @Column(name="MODULE_TEXT",nullable = false, length = 2000)
  private String moduleText;
  
  @NotNull
  @NotEmpty
  @Length(min=1,max=500)
  @Column(name="MODULE_TITLE",nullable = false, length = 500)
  private String moduleTitle;
  
  @Column(name="RECORD_CREATOR_ID")
  private String recordCreatorId;

  @Column(name="RECORD_CREATE_DATE")
  private Date recordCreateDate;

  @Column(name="RECORD_LAST_UPDATER_ID")
  private String recordLastUpdaterId;

  @Column(name="RECORD_LAST_UPDATED_DATE")
  private Date recordLastUpdatedDate;

  public TestTutorialModules() {
    super();
  }

  public TestTutorialModules(long modulesId, String moduleName,String moduleText) {
    this.modulesId = modulesId;
    this.moduleName = moduleName;
    this.moduleText = moduleText;
  }
  
  /**
   * @return the modulesId
   */
  public long getModulesId() {
    return modulesId;
  }

  /**
   * @param modulesId the modulesId to set
   */
  public void setModulesId(long modulesId) {
    this.modulesId = modulesId;
  }

  /**
   * @return the documentId
   */
  public Long getDocumentId() {
    return documentId;
  }

  /**
   * @param documentId the documentId to set
   */
  public void setDocumentId(Long documentId) {
    this.documentId = documentId;
  }  
  /**
   * @return the testIndicator
   */
  public String getTestIndicator() {
    return testIndicator;
  }

  /**
   * @param testIndicator the testIndicator to set
   */
  public void setTestIndicator(String testIndicator) {
    this.testIndicator = testIndicator;
  }

  /**
   * @return the memberTypeIndicator
   */
  public Long getMemberTypeIndicator() {
    return memberTypeIndicator;
  }

  /**
   * @param memberTypeIndicator the memberTypeIndicator to set
   */
  public void setMemberTypeIndicator(Long memberTypeIndicator) {
    this.memberTypeIndicator = memberTypeIndicator;
  }

  /**
   * @return the moduleName
   */
  public String getModuleName() {
    return moduleName;
  }

  /**
   * @param moduleName the moduleName to set
   */
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  /**
   * @return the moduleIntroAudio
   */
  public byte[] getModuleIntroAudio() {
    return moduleIntroAudio;
  }

  /**
   * @param moduleIntroAudio the moduleIntroAudio to set
   */
  public void setModuleIntroAudio(byte[] moduleIntroAudio) {
    this.moduleIntroAudio = moduleIntroAudio;
  }

  /**
   * @return the moduleIntroVideo
   */
  public byte[] getModuleIntroVideo() {
    return moduleIntroVideo;
  }

  /**
   * @param moduleIntroVideo the moduleIntroVideo to set
   */
  public void setModuleIntroVideo(byte[] moduleIntroVideo) {
    this.moduleIntroVideo = moduleIntroVideo;
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

  /**
   * @return the pageText
   */
  public String getModuleText() {
    return moduleText;
  }

  /**
   * @param pageText the pageText to set
   */
  public void setModuleText(String moduleText) {
    this.moduleText = moduleText;
  }
  
  /**
   * @return the moduleTitle
   */
  public String getModuleTitle() {
    return moduleTitle;
  }

  /**
   * @param moduleTitle the moduleTitle to set
   */
  public void setModuleTitle(String moduleTitle) {
    this.moduleTitle = moduleTitle;
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

}
