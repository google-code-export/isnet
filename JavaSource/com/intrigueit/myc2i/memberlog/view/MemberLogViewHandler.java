package com.intrigueit.myc2i.memberlog.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("memberLogViewHandler")
@Scope("session")
public class MemberLogViewHandler extends BasePage implements Serializable{
	private static final long serialVersionUID = 3248335429408226984L;
	/** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( MemberLogViewHandler.class );
	private MemberLogService memberLogService;
	private MemberService memberService;	
	private List<MemberLog> memberLogs;
	private List<MemberLog> memberLogsOld;	
	private List<SelectItem> mentorList;	
	private MemberLog currentLog;	
	private ViewDataProvider viewDataProvider;
	private ListDataModel messageLines;
  private Date fromDate;
  private Date toDate;

	@Autowired
	public MemberLogViewHandler(MemberLogService memberLogService,MemberService memberService) {
		this.memberLogService = memberLogService;		
		this.memberService = memberService;
		this.initMemberLog();
	}
	
	public void initMemberLog(){
		//this.currentLog = new MemberLog();
		this.errMsgs.clear();
		this.hasError = false;
		loadAllMemberLog();		
	}
	
	@SuppressWarnings("unchecked")
  public void saveMemberLog(){
	  String action = "";
	  try{
		  if(this.getParameter(ServiceConstants.ACTION)!=null) {
		    action = (String) this.getParameter(ServiceConstants.ACTION);
	      System.out.println(action);
		  }
		  this.errMsgs.clear();
			this.validate();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}
			/** Set audit fields */
			this.currentLog.setRecordCreatorId(this.getMember().getMemberId()+"");
			this.currentLog.setRecordCreatedDate(new Date());
			this.currentLog.setRecordLastUpdatedDate(new Date());
			this.currentLog.setRecordLastUpdaterId(this.getMember().getMemberId()+"");
			this.currentLog.setStatus(CommonConstants.ACTIVITY_STATUS.PENDING.toString());
			this.currentLog.setFromMemberId(this.getMember().getMemberId());
			
			Date dt = new Date();
			this.currentLog.setMemberLogDateTime(new Timestamp(dt.getTime()));			
			this.memberLogService.save(this.currentLog);
			if (action.equals("replay")) {
			  List<MemberLog> lines = (List<MemberLog>) getMessageLines().getWrappedData();
			  lines.add(this.currentLog);
			}
			//MemberLog lg = this.memberLogService.findById(this.currentLog.getMemberLogId());
			log.debug("Sending email notification");
			//this.sendConfirmationEmail(this.currentLog.getUserDefinedValues().getUdValuesValue(), this.currentLog.getMember().getEmail());
			
			//log.debug("member id: "+lg.getMember().getMemberId()+" type id: "+lg.getMemberActivityType());
		}
		catch(Exception ex){
			this.errMsgs.add(this.getText("common_error_system_level"));
			ex.printStackTrace();
		}
		this.resetMessage();
		
	}
	/** Send confirmation email to member */
	private void sendConfirmationEmail(String type, String email)throws Exception{
		
		String msgBody = this.getText("email_activity_log_body", new String[]{type});
		String emailSubject = this.getText("email_activity_log_subject",new String[]{type});
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
	}	
	private void validate(){
		if(CommonValidator.isEmpty(this.currentLog.getTopic())){
			this.errMsgs.add( this.getText("activity_log_validation_subject"));
		}
	
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
    setErrorMessage("");
    logger.debug(" Preparing member log for replay ");
    if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
      //String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {        
        //this.currentLog = memberLogService.findById(Long.parseLong(recordId));
        MemberLog mLog = (MemberLog) this.getMessageLines().getRowData();
        this.currentLog = new MemberLog();
        this.currentLog.setFromMemberId(mLog.getToMemberId());
        this.currentLog.setTopic("Ref : "+mLog.getTopic());
        this.setActionType(ServiceConstants.UPDATE);
        this.setReRenderIds("MESSAGE_LINES");        
        setSecHeaderMsg(this.getText("header_msg_contact_us") + " " + this.getText("header_msg_update"));
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
  }
  
	public List<MemberLog> getMemberLogs() {
		try{
			this.memberLogs = this.memberLogService.getAllPendingLog( this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		
		return memberLogs;
	}


	public void setMemberLogs(List<MemberLog> memberLogs) {
		this.memberLogs = memberLogs;
	}


	public MemberLog getCurrentLog() {
		if (currentLog == null) currentLog = new MemberLog();
	  return currentLog;
	}


	public void setCurrentLog(MemberLog currentLog) {
		this.currentLog = currentLog;
	}


	public ViewDataProvider getViewDataProvider() {
		return viewDataProvider;
	}

   @Autowired
	public void setViewDataProvider(ViewDataProvider viewDataProvider) {
		this.viewDataProvider = viewDataProvider;
	}

	public List<MemberLog> getMemberLogsOld() {
		try{
			this.memberLogsOld = this.memberLogService.getAllCompletedLog(this.getMember().getMemberId());
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
				
		return memberLogsOld;
	}

	public void setMemberLogsOld(List<MemberLog> memberLogsOld) {
	  this.memberLogsOld = memberLogsOld;
	}
	
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


	
}
