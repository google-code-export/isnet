package com.intrigueit.myc2i.udvalues.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;


/**
 * This class represent the MyC2i User defined values entity
 * @version 	1.00	August 11,2009
 * @author 		Mithun
 *
 */
@Entity
@Table(name="USER_DEFINED_VALUES")
public class UserDefinedValues implements Serializable {

	/**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -8802595429940620956L;

  @Id
	@Column(name="USER_DEFINED_VALUES_ID")
	//@GeneratedValue(generator="UDValueSeq")
	//@SequenceGenerator(name="UDValueSeq",sequenceName="UDVALUE_ID_SEQ", allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long udValuesId;

	@Length(max=500)
	@Column(name="USER_DEFINED_VALUES_DESCRIPTIO",nullable = true, length = 500)	
	private String udValuesDesc;

	@Column(name="RECORD_CREATOR_ID",nullable = true, length = 20)
	private String recordCreatorId;
	
	@Column(name="RECORD_CREATED_DATE")
	private Date recordCreatedDate;

	@Length(min=1, max=20)
	@Column(name="RECORD_LAST_UPDATER_ID",nullable = true, length = 20)
	private String recordLastUpdaterId;

	@Column(name="RECORD_LAST_UPDATED_DATE")
	private Date recordLastUpdatedDate;

	@NotNull
  @NotEmpty
  @Length(min=1, max=20)
  @Column(name="USER_DEFINED_VALUES_CATEGORY",nullable = false, length = 20)
	private String udValuesCategory;

	@NotNull
	@NotEmpty
	@Length(min=1, max=250)
	@Column(name="USER_DEFINED_VALUES_VALUE",nullable = false, length = 250)
	private String udValuesValue;

	public UserDefinedValues() {
		super();
	}

	/**
	 * @return the udValuesId
	 */
	public Long getUdValuesId() {
		return udValuesId;
	}

	/**
	 * @param udValuesId the udValuesId to set
	 */
	public void setUdValuesId(Long udValuesId) {
		this.udValuesId = udValuesId;
	}

	/**
	 * @return the udValuesDesc
	 */
	public String getUdValuesDesc() {
		return udValuesDesc;
	}

	/**
	 * @param udValuesDesc the udValuesDesc to set
	 */
	public void setUdValuesDesc(String udValuesDesc) {
		this.udValuesDesc = udValuesDesc;
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
	 * @return the recordCreatedDate
	 */
	public Date getRecordCreatedDate() {
		return recordCreatedDate;
	}

	/**
	 * @param recordCreatedDate the recordCreatedDate to set
	 */
	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
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
	 * @return the udValuesCategory
	 */
	public String getUdValuesCategory() {
		return udValuesCategory;
	}

	/**
	 * @param udValuesCategory the udValuesCategory to set
	 */
	public void setUdValuesCategory(String udValuesCategory) {
		this.udValuesCategory = udValuesCategory;
	}

	/**
	 * @return the udValuesValue
	 */
	public String getUdValuesValue() {
		return udValuesValue;
	}

	/**
	 * @param udValuesValue the udValuesValue to set
	 */
	public void setUdValuesValue(String udValuesValue) {
		this.udValuesValue = udValuesValue;
	}
}
