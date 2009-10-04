package com.intrigueit.myc2i.common.autocomplete;

import java.io.Serializable;


public class CommonSearchDataTmp implements Serializable {
	private static final long serialVersionUID = -637984838979251727L;

	private String recordId;
	private String recordCode;
  private String recordDesc;

  public CommonSearchDataTmp(String recordId,String recordCode, String reocrdDesc) {
  	this.recordId = recordId;
    this.recordCode = recordCode;
    this.recordDesc = reocrdDesc;
  }

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the recordCode
	 */
	public String getRecordCode() {
		return recordCode;
	}

	/**
	 * @param recordCode the recordCode to set
	 */
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	/**
	 * @return the recordDesc
	 */
	public String getRecordDesc() {
		return recordDesc;
	}

	/**
	 * @param recordDesc the recordDesc to set
	 */
	public void setRecordDesc(String recordDesc) {
		this.recordDesc = recordDesc;
	}
}

