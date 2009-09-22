 /**
 * @(#)Member.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * This class represent the MyC2i Member entity
 * @version 	1.00	August 11,2009
 * @author 		Shamim Ahmmed
 * 
 */
@Entity(name = "MEMBER")
public class Member implements java.io.Serializable{

	/**
	 * Serialized version number
	 */
	private static final long serialVersionUID = 5132754826162521230L;

	/** Member Id */
	@Id 
	@Column(name = "MEMBER_ID")
	@GeneratedValue(generator="MemberSeq")
	@SequenceGenerator(name="MemberSeq",sequenceName="MEMBER_ID_SEQ", allocationSize=1,initialValue=1)		
	private Long memberId;
	
	@Column( name = "MEMBER_TYPE_ID") 
	private Long typeId;

	@Column(name = "MENTORED_BY_MEMBER_ID")
	private Long mentoredByMemberId;
	
	/** Member first name */
	@Column( name = "MEMBER_FIRST_NAME")	
	private String firstName;
	
	/** Member last name */
	@Column( name = "MEMBER_LAST_NAME")		
	private String lastName;
	
	@Column(name = "MEMBER_STREET_ADDRESS", length = 20)
	private String streetAddress;

	@Column(name = "MEMBER_CITY",  length = 20)
	private String city;

	@Column(name = "MEMBER_STATE", length = 20)
	private String state;
	
	@Column(name = "MEMBER_ZIP", precision = 22, scale = 0)
	private Long zip;

	@Column(name = "MEMBER_EMAIL")
	private String email;
	
	@Column(name = "MEMBER_LINKED_IN_LINK_URL")
	private String linkedInURL;
	
	@Column(name = "MEMBER_COUNTRY")
	private String country;
	
	@Column(name = "MEMBER_CELL_PHONE_NUMBER", precision = 22, scale = 0)  	
	private Long cellPhoneNumber;
	
	@Column(name = "MEMBER_WORK_PHONE_NUMBER", precision = 22, scale = 0)  	
	private Long workPhoneNumber;
	
	@Column(name = "MEMBER_HOME_PHONE_NUMBER", precision = 22, scale = 0) 	
	private Long homePhoneNumber;	
	
	@Column(name = "MEMBER_PROFESSION")
	private String profession;
	
	@Column(name = "MEMBER_COMPANY_NAME")
	private String company;
	
	@Column(name = "MEMBER_PASSWORD")
	private String password;   
	
	@Column(name = "MEMBER_INTRODUCTION_URL") 
	private String introductionUrl; 
	
	@Column(name = "MEMBER_ETHNICITY_ID")
	private Long ethinicity;
	
	@Column(name = "MEMBER_YEAR_OF_BIRTH")
	private Long birthYear;
	
	@Column(name = "MEMBER_GENDER_INDICATOR")
	private String genderInd;
	
	@Column(name = "MEMBER_MARITAL_STATUS")
	private String maritalStatus;
	
	@Column(name = "MEMBER_CHILDREN_INDICATOR")
	private String childrenInd;
	
	@Column(name = "MEMBER_MAZHAB")
	private String mazhab;
	
	@Column(name = "DO_NOT_CALL_INDICATOR")
	private String dontCall;
	
	@Column(name = "DO_NOT_EMAIL_INDICATOR")
	private String dontEmail;
	
	@Column(name = "MEMBER_INTEREST_LEVEL_ID")
	private Long knowledgeLevel;
	
	@Column(name = "MEMBER_RELIGION_ID")
	private Long religionId;
	
	@Column(name = "RECORD_CREATED_DATE")
	private Date recordCreate;
	
	@Column(name = "RECORD_LAST_UPDATED_DATE")
	private Date lastUpdated;
	
	@Column(name = "MEMBER_SECURITY_QUESTION_1")
	private String securityQuestion1;

	@Column(name = "MEMBER_SECURITY_QUESTION_2")
	private String securityQuestion2;
	
	@Column(name = "MEMBER_SECURITY_QUESTION_3")
	private String securityQuestion3;
	
	@Column(name = "MEMBER_SECURITY_ANSWER_1")
	private String securityQuestionAns1;		
	
	@Column(name = "MEMBER_SECURITY_ANSWER_2")
	private String securityQuestionAns2;		
	
	@Column(name = "MEMBER_SECURITY_ANSWER_3")
	private String securityQuestionAns3;	
	
	@Column(name = "MEMBER_ROLE_ID")
	private Long memberRoleId;
	
	@Column( name = "RECORD_CREATOR_ID")
	private String recordCreatorId;
	
	@Column( name = "RECORD_LAST_UPDATER_ID")
	private String recordUpdaterId;
	

	@Column(name = "ADMIN_USER_INDICATOR",length = 20)
	private String adminUserIndicator;
	
	@Column(name = "MEMBER_MEMBERSHIP_EXPIRY_DATE")
	private Date memberShipExpiryDate;
	
	/*
	@Column(name = "MEMBER_LINKED_IN_LINK_URL", length = 20)
	private String linkedInLinkUrl;

	*/
	 
	public Long getMemberId() {
		return memberId;
	}

	public Member() {
		super();
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
   * @return the typeId
   */
  public Long getTypeId() {
    return typeId;
  }

  /**
   * @param typeId the typeId to set
   */
  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  /**
   * @return the mentoredByMemberId
   */
  public Long getMentoredByMemberId() {
    return mentoredByMemberId;
  }

  /**
   * @param mentoredByMemberId the mentoredByMemberId to set
   */
  public void setMentoredByMemberId(Long mentoredByMemberId) {
    this.mentoredByMemberId = mentoredByMemberId;
  }

  public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Long getZip() {
		return zip;
	}
/*
	public String getAdminUserIndicator() {
		return adminUserIndicator;
	}

	public void setAdminUserIndicator(String adminUserIndicator) {
		this.adminUserIndicator = adminUserIndicator;
	}

	public String getLinkedInLinkUrl() {
		return linkedInLinkUrl;
	}

	public void setLinkedInLinkUrl(String linkedInLinkUrl) {
		this.linkedInLinkUrl = linkedInLinkUrl;
	}
*/
	public void setZip(Long zip) {
		this.zip = zip;
	}

	public Long getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(Long cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public Long getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(Long workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public Long getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(Long homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIntroductionUrl() {
		return introductionUrl;
	}

	public void setIntroductionUrl(String introductionUrl) {
		this.introductionUrl = introductionUrl;
	}

	public Long getEthinicity() {
		return ethinicity;
	}

	public void setEthinicity(Long ethinicity) {
		this.ethinicity = ethinicity;
	}

	public Long getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Long birthYear) {
		this.birthYear = birthYear;
	}

	public String getChildrenInd() {
		return childrenInd;
	}

	public void setChildrenInd(String childrenInd) {
		this.childrenInd = childrenInd;
	}

	public String getMazhab() {
		return mazhab;
	}

	public void setMazhab(String mazhab) {
		this.mazhab = mazhab;
	}

	public String getDontCall() {
		return dontCall;
	}

	public void setDontCall(String dontCall) {
		this.dontCall = dontCall;
	}

	public String getDontEmail() {
		return dontEmail;
	}

	public void setDontEmail(String dontEmail) {
		this.dontEmail = dontEmail;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getGenderInd() {
		return genderInd;
	}

	public void setGenderInd(String genderInd) {
		this.genderInd = genderInd;
	}

	public Long getKnowledgeLevel() {
		return knowledgeLevel;
	}

	public void setKnowledgeLevel(Long knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel;
	}

	public Long getReligionId() {
		return religionId;
	}

	public void setReligionId(Long religionId) {
		this.religionId = religionId;
	}

	public Date getRecordCreate() {
		return recordCreate;
	}

	public void setRecordCreate(Date recordCreate) {
		this.recordCreate = recordCreate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getSecurityQuestion1() {
		return securityQuestion1;
	}

	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}

	public String getSecurityQuestion2() {
		return securityQuestion2;
	}

	public void setSecurityQuestion2(String securityQuestion2) {
		this.securityQuestion2 = securityQuestion2;
	}

	public String getSecurityQuestion3() {
		return securityQuestion3;
	}

	public void setSecurityQuestion3(String securityQuestion3) {
		this.securityQuestion3 = securityQuestion3;
	}

	public String getSecurityQuestionAns1() {
		return securityQuestionAns1;
	}

	public void setSecurityQuestionAns1(String securityQuestionAns1) {
		this.securityQuestionAns1 = securityQuestionAns1;
	}

	public String getSecurityQuestionAns2() {
		return securityQuestionAns2;
	}

	public void setSecurityQuestionAns2(String securityQuestionAns2) {
		this.securityQuestionAns2 = securityQuestionAns2;
	}

	public String getSecurityQuestionAns3() {
		return securityQuestionAns3;
	}

	public void setSecurityQuestionAns3(String securityQuestionAns3) {
		this.securityQuestionAns3 = securityQuestionAns3;
	}

	public String getLinkedInURL() {
		return linkedInURL;
	}

	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}

	public Long getMemberRoleId() {
		return memberRoleId;
	}

	public void setMemberRoleId(Long memberRoleId) {
		this.memberRoleId = memberRoleId;
	}

	public String getRecordCreatorId() {
		return recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	public String getRecordUpdaterId() {
		return recordUpdaterId;
	}

	public void setRecordUpdaterId(String recordUpdaterId) {
		this.recordUpdaterId = recordUpdaterId;
	}

	public String getAdminUserIndicator() {
		return adminUserIndicator;
	}

	public void setAdminUserIndicator(String adminUserIndicator) {
		this.adminUserIndicator = adminUserIndicator;
	}

	public Date getMemberShipExpiryDate() {
		return memberShipExpiryDate;
	}

	public void setMemberShipExpiryDate(Date memberShipExpiryDate) {
		this.memberShipExpiryDate = memberShipExpiryDate;
	}
	
	
	
}
