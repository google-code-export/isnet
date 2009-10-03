package com.intrigueit.myc2i.trainingitem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TRAINING_ITEM")
public class TrainingItem implements Serializable {
	/**
   * 
   */
	private static final long serialVersionUID = -7702585319897094869L;

	@Id
	@Column(name = "ITEM_ID")
	@GeneratedValue(generator = "trainingItemSeq")
	@SequenceGenerator(name = "trainingItemSeq", sequenceName = "TRAINING_ITEM_SEQ", allocationSize = 1, initialValue = 1)
	private long itemId;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 150)
	@Column(name = "ITEM_DESCRIPTION", nullable = false, length = 150)
	private String itemDescription;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 20)
	@Column(name = "ITEM_E_INDICATOR", nullable = false, length = 20)
	private String itemEIndicator;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 20)
	@Column(name = "ITEM_SUBSCRIPTION_IND", nullable = false, length = 20)
	private String itemSubscriptionInd;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 20)
	@Column(name = "ITEM_E_STORAGE_LOCATI", nullable = false, length = 20)
	private String itemEStorageLocati;

	@Column(name = "ITEM_PURCHASE_COST")
	private Double itemPurchaseCost;

	@Column(name = "ITEM_SALES_PRICE")
	private Double itemSalesPrice;

	@Column(name = "ITEM_AVAILABILITY", length = 1)
	private String itemAvailability;

	public String getItemAvailability() {
		return itemAvailability;
	}

	public void setItemAvailability(String itemAvailability) {
		this.itemAvailability = itemAvailability;
	}

	@Column(name = "RECORD_CREATOR_ID")
	private String recordCreatorId;

	@Column(name = "RECORD_CREATE_DATE")
	private Date recordCreateDate;

	@Column(name = "RECORD_LAST_UPDATER_ID")
	private String recordLastUpdaterId;

	@Column(name = "RECORD_LAST_UPDATED_DATE")
	private Date recordLastUpdatedDate;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 20)
	@Column(name = "ITEM_VERSION", nullable = false, length = 20)
	private String itemVersion;

	@NotNull
	@NotEmpty
	@Length(min = 1, max = 20)
	@Column(name = "ITEM_LANGUAGE", nullable = false, length = 20)
	private String itemLanguage;

	@Column(name = "ITEM_IMAGE")
	@Lob
	private byte[] itemImage;

	@Column(name = "VENDOR_ID")
	private Long vendorId;

	public TrainingItem() {
		super();
	}

	public TrainingItem(long itemId, String itemDescription,
			String itemEIndicator, String itemSubscriptionInd,
			String itemEStorageLocati, Double itemPurchaseCost,
			Double itemSalesPrice, String itemVersion, String itemLanguage,
			String itemAvailability) {
		this.itemId = itemId;
		this.itemDescription = itemDescription;
		this.itemEIndicator = itemEIndicator;
		this.itemSubscriptionInd = itemSubscriptionInd;
		this.itemEStorageLocati = itemEStorageLocati;
		this.itemPurchaseCost = itemPurchaseCost;
		this.itemSalesPrice = itemSalesPrice;
		this.itemVersion = itemVersion;
		this.itemLanguage = itemLanguage;
		this.itemAvailability = itemAvailability;
	}

	public long getItemId() {
		return this.itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemEIndicator() {
		return this.itemEIndicator;
	}

	public void setItemEIndicator(String itemEIndicator) {
		this.itemEIndicator = itemEIndicator;
	}

	public String getItemSubscriptionInd() {
		return this.itemSubscriptionInd;
	}

	public void setItemSubscriptionInd(String itemSubscriptionInd) {
		this.itemSubscriptionInd = itemSubscriptionInd;
	}

	public String getItemEStorageLocati() {
		return this.itemEStorageLocati;
	}

	public void setItemEStorageLocati(String itemEStorageLocati) {
		this.itemEStorageLocati = itemEStorageLocati;
	}

	public Double getItemPurchaseCost() {
		return this.itemPurchaseCost;
	}

	public void setItemPurchaseCost(Double itemPurchaseCost) {
		this.itemPurchaseCost = itemPurchaseCost;
	}

	public Double getItemSalesPrice() {
		return this.itemSalesPrice;
	}

	public void setItemSalesPrice(Double itemSalesPrice) {
		this.itemSalesPrice = itemSalesPrice;
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

	public String getItemVersion() {
		return this.itemVersion;
	}

	public void setItemVersion(String itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getItemLanguage() {
		return this.itemLanguage;
	}

	public void setItemLanguage(String itemLanguage) {
		this.itemLanguage = itemLanguage;
	}

	public byte[] getItemImage() {
		return this.itemImage;
	}

	public void setItemImage(byte[] itemImage) {
		this.itemImage = itemImage;
	}

	public Long getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

}
