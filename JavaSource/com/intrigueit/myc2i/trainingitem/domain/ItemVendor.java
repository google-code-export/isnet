package com.intrigueit.myc2i.trainingitem.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "ITEM_VENDOR")
public class ItemVendor implements Serializable {

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = -2080629454595731508L;

	@Id
	@Column(name = "VENDOR_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long vendorId;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 50)
	@Column(name = "VENDOR_NAME", nullable = false, length = 50)
	private String vendorName;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 50)
	@Email
	@Column(name = "VENDOR_EMAIL", nullable = false, length = 50)
	private String vendorEmail;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 100)
	@Column(name = "VENDOR_ADDRESS", nullable = false, length = 100)
	private String vendorAddress;

	@Length(max = 20)
	@Column(name = "VENDOR_PHONE", nullable = true, length = 20)
	private String vendorPhone;

	@Column(name = "RECORD_CREATOR_ID")
	private String recordCreatorId;

	@Column(name = "RECORD_CREATE_DATE")
	private Date recordCreateDate;

	@Column(name = "RECORD_LAST_UPDATER_ID")
	private String recordLastUpdaterId;

	@Column(name = "RECORD_LAST_UPDATED_DATE")
	private Date recordLastUpdatedDate;

	public ItemVendor() {
		super();
	}

	public Long getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Long vendorId) {
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

	public String getVendorPhone() {
		return this.vendorPhone;
	}

	public void setVendorPhone(String vendorPhone) {
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
