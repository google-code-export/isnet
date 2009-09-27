package com.intrigueit.myc2i.memberlog.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;

@Component("contactUsViewHandler")
@Scope("session") 
public class ContactUsViewHandler extends BasePage implements Serializable {	
  
  private static final long serialVersionUID = -8177894044709887971L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( ContactUsViewHandler.class );
  
  private MemberLogService memberLogService;
  private MemberLog currentLog;
  private ListDataModel messageLines;
  private Date fromDate;
  private Date toDate;
  
  /**
   * @return the fromDate
   */
  public Date getFromDate() {
    return fromDate;
  }

  /**
   * @param fromDate the fromDate to set
   */
  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  /**
   * @return the toDate
   */
  public Date getToDate() {
    return toDate;
  }

  /**
   * @param toDate the toDate to set
   */
  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  /**
   * @return the messageLines
   */
  public ListDataModel getMessageLines() {
    if(messageLines == null){
      messageLines = new ListDataModel();
    }
    return messageLines;
  }

  /**
   * @param messageLines the messageLines to set
   */
  public void setMessageLines(ListDataModel messageLines) {
    this.messageLines = messageLines;
  }

  @Autowired
	public ContactUsViewHandler(MemberLogService memberLogService) {
		this.memberLogService = memberLogService;
		this.initialize();
	} 
    
	public void initialize(){		
	  loadAllMemberLog();
	}
	
	public void loadAllMemberLog() {   
    try {
      logger.debug(" Load All Member Log");    
      List<MemberLog> recordList = memberLogService.findAll();
      this.getMessageLines().setWrappedData(recordList);
      this.setActionType(ServiceConstants.UPDATE);
    }catch (Exception ex) {
      logger.error("Unable to load Members:"+ex.getMessage());
      ex.printStackTrace();
    }
  }
	
	public void loadMessagesByCriteria() {
    try {
      logger.debug(" Load message by search critariya ");    
      String DATE_FORMAT = "yyyyMMdd";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      String fromDate = sdf.format(this.getFromDate());
      String toDate = sdf.format(this.getToDate());
      List<MemberLog> recordList = memberLogService.findByProperties(fromDate,toDate);
      getMessageLines().setWrappedData(recordList);
    }catch (Exception ex) {
      logger.error("Message by search critariya:"+ex.getMessage());
      ex.printStackTrace();
    }
  }
	
	@SuppressWarnings("unchecked")
	public void deleteMemberLog() {
	  logger.debug("Deleting member log");
    setErrorMessage("");
    if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {       
        int rowIndex = getMessageLines().getRowIndex();
        MemberLog memberLog = memberLogService.findById(Long.parseLong(recordId));
        memberLogService.delete(memberLog);
        List<MemberLog> list = (List<MemberLog>) getMessageLines().getWrappedData();
        list.remove(rowIndex);
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
	}
	
	public void preReplay () {
	  
	}
	
	/**
	 * @return the currentMember
	 */
	public MemberLog getCurrentLog() {
		if (currentLog == null) {
			System.out.println(" Init member ");
			currentLog = new MemberLog();
		}
		return currentLog;
	}	
}
