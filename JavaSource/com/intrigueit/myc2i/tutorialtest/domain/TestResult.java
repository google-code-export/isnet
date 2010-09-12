package com.intrigueit.myc2i.tutorialtest.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "TUTORIAL_TEST")
public class TestResult implements java.io.Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -6126621475036233716L;

    @Id
	@Column(name = "TUTORIAL_TEST_ID")
	@GeneratedValue(generator = "TutorialTestSeq")
	@SequenceGenerator(name = "TutorialTestSeq", sequenceName = "TUTORIAL_TEST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Long tutorialTestId;

	@OneToMany(mappedBy="testResult",targetEntity=TestResultDetails.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<TestResultDetails> testResultDetails;
	
	@Column(name = "DOCUMENT_ID", nullable = false)
	private Long documentId;
	
	@Column(name = "MODULE_ID", nullable = false)
	private Long moduleId;
	
	@Column(name = "MEMBER_ID", nullable = false)	
	private Long memberId;

	@Column(name = "TEST_START_TIME", nullable = true)
	private Date startTime;
	
	@Column(name = "TEST_END_TIME")	
	private Date endTime;
	
	@Column(name = "TOTAL_QUESTIONS")		
	private Long totalQuestions;
	
	@Column(name = "TOTAL_CORRECT")		
	private Long totalCorrect;

	@Column(name = "IS_PASSED")		
	private Boolean isPassed;
	
	@Column(name = "IS_COMPLETED")    
    private Boolean isCompleted;

    @Column(name = "RECORD_CREATOR_ID", nullable = false)		
	private String recordCreatorId;
	
	@Column(name = "RECORD_CREATE_DATE", nullable = true)		
	private Date recordCreateDate;

	@Column(name = "RECORD_LAST_UPDATER_ID", nullable = false)	
	private String recordUpdatorId;
	
	@Column(name = "RECORD_LAST_UPDATED_DATE", nullable = true)		
	private Date lastUpdatedDate;
	
	@Column( name = "LAST_ACCESS_PAGE", nullable= true)
	private Long lastAccessPage;

	public Long getTutorialTestId() {
		return tutorialTestId;
	}

	public void setTutorialTestId(Long tutorialTestId) {
		this.tutorialTestId = tutorialTestId;
	}

	public Set<TestResultDetails> getTestResultDetails() {
		return testResultDetails;
	}

	public void setTestResultDetails(Set<TestResultDetails> testResultDetails) {
		this.testResultDetails = testResultDetails;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Long totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Long getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(Long totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public Boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getRecordCreatorId() {
		return recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	public Date getRecordCreateDate() {
		return recordCreateDate;
	}

	public void setRecordCreateDate(Date recordCreateDate) {
		this.recordCreateDate = recordCreateDate;
	}

	public String getRecordUpdatorId() {
		return recordUpdatorId;
	}

	public void setRecordUpdatorId(String recordUpdatorId) {
		this.recordUpdatorId = recordUpdatorId;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getLastAccessPage() {
		return lastAccessPage;
	}

	public void setLastAccessPage(Long lastAccessPage) {
		this.lastAccessPage = lastAccessPage;
	}

	

}
