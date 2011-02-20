package com.intrigueit.myc2i.tutorialtest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "TUTORIAL_TEST_DETAILS")
public class TestResultDetails implements java.io.Serializable{

	/**
   * 
   */
  private static final long serialVersionUID = -2311910343080435672L;

    @Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long id;

	@ManyToOne ( targetEntity=TestResult.class,optional=true)
	@JoinColumn(name = "TUTORIAL_TEST_ID",referencedColumnName="TUTORIAL_TEST_ID")	
	private TestResult testResult;
	
	@Column(name = "QUESTION_ID", nullable = false)	
	private Long questionId;
	
	@Column(name = "MENTOR_ANSWER")	
	private String mentorAns;	

  @Column(name = "IS_CORRECT", nullable = false)	
	private Boolean isCorrect;
	
	@Column(name = "RECORD_CREATOR_ID", nullable = false)		
	private String recordCreatorId;
	
	@Column(name = "RECORD_CREATE_DATE", nullable = true)
	private Date recordCreateDate;

	@Column(name = "RECORD_LAST_UPDATER_ID", nullable = false)	
	private String recordUpdatorId;
	
	@Column(name = "RECORD_LAST_UPDATED_DATE", nullable = true)		
	private Date lastUpdatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	/**
   * @return the mentorAns
   */
  public String getMentorAns() {
    return mentorAns;
  }

  /**
   * @param mentorAns the mentorAns to set
   */
  public void setMentorAns(String mentorAns) {
    this.mentorAns = mentorAns;
  }
  
	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
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
	
	
}
