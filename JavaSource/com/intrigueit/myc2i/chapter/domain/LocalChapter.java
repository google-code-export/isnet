package com.intrigueit.myc2i.chapter.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;

@Entity
@Table(name = "LOCAL_CHAPTER")
public class LocalChapter implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 249123646772893226L;

	@Id
	@Column(name = "LOCAL_CHAPTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chapterId;

	@Column(name = "CHAPTER_LEAD_MEMBER_ID")
	private Long leadMemberId;

	@Transient
	private String leadMemberName;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 100)
	@Column(name = "LOCAL_CHAPTER_NAME", length = 100)
	private String chapterName;

	@Length(max = 100)
	@Column(name = "LOCAL_CHAPTER_STREET_ADDRESS", length = 100)
	private String chapterStreetAddress;

	@Length(max = 50)
	@Column(name = "LOCAL_CHAPTER_CITY", length = 50)
	private String chapterCity;

	@Length(max = 50)
	@Column(name = "LOCAL_CHAPTER_STATE", length = 50)
	private String chapterState;

	@Length(max = 20)
	@Column(name = "LOCAL_CHAPTER_ZIP", length = 20)
	private String chapterZip;

	@Length(max = 50)
	@Email
	@Column(name = "LOCAL_CHAPTER_EMAIL_ADDRESS", length = 50)
	private String chapterEmailAddress;

	@Length(max = 50)
	@Column(name = "LOCAL_CHAPTER_URL", length = 50)
	private String chapterUrl;

	@Length(max = 20)
	@Column(name = "LOCAL_CHAPTER_PHONE", length = 20)
	private String chapterPhone;

	@Length(max = 20)
	@Column(name = "LOCAL_CHAPTER_FAX", length = 20)
	private String chapterFax;

	@Column(name = "LOCAL_CHAPTER_COUNTRY", length = 20)
	private String chapterCountry;

	@Column(name = "RECORD_CREATOR_ID")
	private String recordCreatorId;

	@Column(name = "RECORD_CREATED_DATE")
	private Date recordCreatedDate;

	@Column(name = "RECORD_LAST_UPDATER_ID")
	private String recordLastUpdaterId;

	@Column(name = "RECORD_LAST_UPDATED_DATE")
	private Date recordLastUpdatedDate;

	public LocalChapter() {
		super();
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getLeadMemberId() {
		return leadMemberId;
	}

	public void setLeadMemberId(Long leadMemberId) {
		this.leadMemberId = leadMemberId;
	}

	public String getLeadMemberName() {
		return leadMemberName;
	}

	public void setLeadMemberName(String leadMemberName) {
		this.leadMemberName = leadMemberName;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterStreetAddress() {
		return chapterStreetAddress;
	}

	public void setChapterStreetAddress(String chapterStreetAddress) {
		this.chapterStreetAddress = chapterStreetAddress;
	}

	public String getChapterCity() {
		return chapterCity;
	}

	public void setChapterCity(String chapterCity) {
		this.chapterCity = chapterCity;
	}

	public String getChapterState() {
		return chapterState;
	}

	public void setChapterState(String chapterState) {
		this.chapterState = chapterState;
	}

	public String getChapterZip() {
		return chapterZip;
	}

	public void setChapterZip(String chapterZip) {
		this.chapterZip = chapterZip;
	}

	public String getChapterEmailAddress() {
		return chapterEmailAddress;
	}

	public void setChapterEmailAddress(String chapterEmailAddress) {
		this.chapterEmailAddress = chapterEmailAddress;
	}

	public String getChapterUrl() {
		return chapterUrl;
	}

	public void setChapterUrl(String chapterUrl) {
		this.chapterUrl = chapterUrl;
	}

	public String getChapterPhone() {
		return chapterPhone;
	}

	public void setChapterPhone(String chapterPhone) {
		this.chapterPhone = chapterPhone;
	}

	public String getChapterFax() {
		return chapterFax;
	}

	public void setChapterFax(String chapterFax) {
		this.chapterFax = chapterFax;
	}

	public String getChapterCountry() {
		return chapterCountry;
	}

	public void setChapterCountry(String chapterCountry) {
		this.chapterCountry = chapterCountry;
	}

	public String getRecordCreatorId() {
		return recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	public Date getRecordCreatedDate() {
		return recordCreatedDate;
	}

	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	public String getRecordLastUpdaterId() {
		return recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(String recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
	}

	public Date getRecordLastUpdatedDate() {
		return recordLastUpdatedDate;
	}

	public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
		this.recordLastUpdatedDate = recordLastUpdatedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCAL_CHAPTER_STATE", nullable = false, insertable = false, updatable = false)
	private UserDefinedValues stateUDV;

	public UserDefinedValues getStateUDV() {
		return stateUDV;
	}

	public void setStateUDV(UserDefinedValues stateUDV) {
		this.stateUDV = stateUDV;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCAL_CHAPTER_COUNTRY", nullable = false, insertable = false, updatable = false)
	private UserDefinedValues countryUDV;

	public UserDefinedValues getCountryUDV() {
		return countryUDV;
	}

	public void setCountryUDV(UserDefinedValues countryUDV) {
		this.countryUDV = countryUDV;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHAPTER_LEAD_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	private Member leadMember;

	/**
	 * @return the leadMember
	 */
	public Member getLeadMember() {
		return leadMember;
	}

	/**
	 * @param leadMember
	 *            the leadMember to set
	 */
	public void setLeadMember(Member leadMember) {
		this.leadMember = leadMember;
	}

}
