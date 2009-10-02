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
@Table(name="TEST_TUTORIAL_DOCUMENT")
public class TestTutorialDocument implements Serializable {
	private static final long serialVersionUID = 7046264158950500215L;

  @Id
	@Column(name="TEST_TUTORIAL_DOCUMENT_ID")
	@GeneratedValue(generator="documentSeq")
  @SequenceGenerator(name="documentSeq",sequenceName="DOCUMENT_ID_SEQ", allocationSize=1,initialValue=1)
	private long documentId;

  @NotNull
  @NotEmpty
  @Length(min=1, max=100)
	@Column(name="DOCUMENT_NAME",nullable = false, length = 100)
	private String documentName;
  
  @Column(name="AUDIO_FILE_NAME")
  private String audioFileName;
  
  @Column(name="VIDEO_FILE_NAME")
  private String videoFileName;
	
  @Column(name="DOCUMENT_INTRO_AUDIO")
	@Lob
	private byte[] documentIntroAudio;
	
	@Column(name="DOCUMENT_INTRO_VIDEO")
  @Lob
  private byte[] documentIntroVideo;

  @Column(name="RECORD_CREATOR_ID")
  private String recordCreatorId;

  @Column(name="RECORD_CREATE_DATE")
  private Date recordCreateDate;

  @Column(name="RECORD_LAST_UPDATER_ID")
  private String recordLastUpdaterId;

  @Column(name="RECORD_LAST_UPDATED_DATE")
  private Date recordLastUpdatedDate;

  public TestTutorialDocument() {
    super();
  }

  public TestTutorialDocument(long documentId, String documentName) {
    this.documentId = documentId;
    this.documentName = documentName;
  }

	/**
   * @return the documentId
   */
  public long getDocumentId() {
    return documentId;
  }

  /**
   * @param documentId the documentId to set
   */
  public void setDocumentId(long documentId) {
    this.documentId = documentId;
  }

  /**
   * @return the documentName
   */
  public String getDocumentName() {
    return documentName;
  }

  /**
   * @param documentName the documentName to set
   */
  public void setDocumentName(String documentName) {
    this.documentName = documentName;
  }

  /**
   * @return the documentIntroAudio
   */
  public byte[] getDocumentIntroAudio() {
    return documentIntroAudio;
  }

  /**
   * @param documentIntroAudio the documentIntroAudio to set
   */
  public void setDocumentIntroAudio(byte[] documentIntroAudio) {
    this.documentIntroAudio = documentIntroAudio;
  }

  /**
   * @return the documentIntroVideo
   */
  public byte[] getDocumentIntroVideo() {
    return documentIntroVideo;
  }

  /**
   * @param documentIntroVideo the documentIntroVideo to set
   */
  public void setDocumentIntroVideo(byte[] documentIntroVideo) {
    this.documentIntroVideo = documentIntroVideo;
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
