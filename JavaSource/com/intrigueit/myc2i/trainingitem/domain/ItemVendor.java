package com.intrigueit.myc2i.trainingitem.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="ITEM_VENDOR")
public class ItemVendor implements Serializable {
	/**
   * 
   */
  private static final long serialVersionUID = -2080629454595731508L;

  @Id
	@Column(name="VENDOR_ID")
	@GeneratedValue(generator="vendorSeq")
  @SequenceGenerator(name="vendorSeq",sequenceName="VENDOR_ID_SEQ", allocationSize=1,initialValue=1)
	private long vendorId;

	@NotNull
  @NotEmpty
  @Length(min=1,max=50)
	@Column(name="VENDOR_NAME",nullable = false, length = 50)
	private String vendorName;
	
	@NotNull
  @NotEmpty
  @Length(min=1,max=50)
  @Email
  @Column(name="VENDOR_EMAIL",nullable = false, length = 50)
	private String vendorEmail;

	@NotNull
  @NotEmpty
  @Length(min=1,max=100)
	@Column(name="VENDOR_ADDRESS",nullable = false, length = 100)
	private String vendorAddress;
	
	
	@Column(name="VENDOR_PHONE")
	private BigDecimal vendorPhone;

	@Column(name="RECORD_CREATOR_ID")
	private String recordCreatorId;

	@Column(name="RECORD_CREATE_DATE")
	private Date recordCreateDate;

	@Column(name="RECORD_LAST_UPDATER_ID")
	private String recordLastUpdaterId;

	@Column(name="RECORD_LAST_UPDATED_DATE")
	private Date recordLastUpdatedDate;	
	
	public ItemVendor() {
		super();
	}

	public long getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorEmail() {
		return this.vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getVendorAddress() {
		return this.vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public BigDecimal getVendorPhone() {
		return this.vendorPhone;
	}

	public void setVendorPhone(BigDecimal vendorPhone) {
		this.vendorPhone = vendorPhone;
	}

	public String getRecordCreatorId() {
		return this.recordCreatorId;
	}

	public void setRecordCreatorId(String recordCreatorId) {
		this.recordCreatorId = recordCreatorId;
	}

	public Date getRecordCreateDate() {
		return this.recordCreateDate;
	}

	public void setRecordCreateDate(Date recordCreateDate) {
		this.recordCreateDate = recordCreateDate;
	}

	public String getRecordLastUpdaterId() {
		return this.recordLastUpdaterId;
	}

	public void setRecordLastUpdaterId(String recordLastUpdaterId) {
		this.recordLastUpdaterId = recordLastUpdaterId;
	}

	public Date getRecordLastUpdatedDate() {
		return this.recordLastUpdatedDate;
	}

	public void setRecordLastUpdatedDate(Date recordLastUpdatedDate) {
		this.recordLastUpdatedDate = recordLastUpdatedDate;
	}
	
}
