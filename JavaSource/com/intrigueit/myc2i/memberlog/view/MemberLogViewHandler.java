package com.intrigueit.myc2i.memberlog.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("memberLogViewHandler")
@Scope("session")
public class MemberLogViewHandler extends BasePage implements Serializable {
	private static final long serialVersionUID = 3248335429408226984L;
	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(MemberLogViewHandler.class);
	private MemberLogService memberLogService;
	private MemberService memberService;
	private UDValuesService udService;
	private List<MemberLog> memberLogs;
	private List<MemberLog> memberLogsOld;
	private List<MemberLog> memberLastWeekLogs;
	private List<SelectItem> mentorList;
	private MemberLog currentLog;
	private ViewDataProvider viewDataProvider;
	private ListDataModel messageLines;
	private Date fromDate;
	private Date toDate;
	private Boolean showSearchBox;
	private boolean isActivityTypeReadOnly;
	private String memberName;
	private String ddDay = "0";
	
	@Autowired
	public MemberLogViewHandler(MemberLogService memberLogService,
			MemberService memberService,UDValuesService udService) {
		this.memberLogService = memberLogService;
		this.memberService = memberService;
		this.udService = udService;
	}

	public void initMemberLog() {
		this.currentLog = new MemberLog();
		this.errMsgs.clear();
		this.hasError = false;
		this.showSearchBox = true;
		this.isActivityTypeReadOnly = false;

	}
	public void initRecentLog(){
		String memberId = this.getRequest().getParameter("MEMBER_ID");
		if(memberId == null){
			return;
		}		
		try{
			
			this.memberLastWeekLogs = this.memberLogService.getRecentConversation(Long.parseLong(memberId));
			if(this.memberLastWeekLogs == null){
				this.memberLastWeekLogs = new ArrayList<MemberLog>();
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public void initMemberLogForProtegeRequest() {
		try{
			this.memberName = this.getRequest().getParameter("MEMBER_NAME");
			String memberId = this.getRequest().getParameter("MEMBER_ID");
			if(memberId == null){
				return;
			}

			UserDefinedValues actType = null;
			List<UserDefinedValues> types = udService.findByProperty("udValuesValue", CommonConstants.ACTIVITY_TYPE_PROTEGE_REQUEST);
			if(types != null && types.size() > 0){
				actType = types.get(0);
			}
			
			this.currentLog = new MemberLog();
			this.currentLog.setToMemberId(Long.parseLong(memberId));
			this.currentLog.setMemberActivityType(actType.getUdValuesId());
			this.currentLog.setTopic(this.getText("acitivity_log_protege_request_sub"));
			this.errMsgs.clear();
			this.hasError = false;
			this.showSearchBox = false;
			this.isActivityTypeReadOnly = true;
			
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public void initMemberLogForMentorRequest() {
		try{
			this.memberName = this.getRequest().getParameter("MEMBER_NAME");
			String memberId = this.getRequest().getParameter("MEMBER_ID");
			if(memberId == null){
				return;
			}
			UserDefinedValues actType = null;
			List<UserDefinedValues> types = udService.findByProperty("udValuesValue", CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST);
			if(types != null && types.size() > 0){
				actType = types.get(0);
			}
			
			this.currentLog = new MemberLog();
			this.currentLog.setToMemberId(Long.parseLong(memberId));
			this.currentLog.setMemberActivityType(actType.getUdValuesId());
			this.currentLog.setTopic(this.getText("acitivity_log_mentor_request_sub"));
			this.errMsgs.clear();
			this.hasError = false;
			this.showSearchBox = false;
			this.isActivityTypeReadOnly = true;
			
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}	
	public void initMemberLogForContact() {
		try{
			this.memberName = this.getRequest().getParameter("MEMBER_NAME");
			String memberId = this.getRequest().getParameter("MEMBER_ID");
			if(memberId == null){
				return;
			}
			this.currentLog = new MemberLog();
			this.currentLog.setToMemberId(Long.parseLong(memberId));
			this.errMsgs.clear();
			this.hasError = false;
			this.showSearchBox = false;
			this.isActivityTypeReadOnly = false;
			
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}	
	@SuppressWarnings("unchecked")
	public void saveMemberLog() {
		String action = "";
		try {
			if (this.getParameter(ServiceConstants.ACTION) != null) {
				action = (String) this.getParameter(ServiceConstants.ACTION);
				log.debug(action);
			}
			this.errMsgs.clear();
			this.validate();
			if (this.errMsgs.size() > 0) {
				this.hasError = true;
				return;
			}
			Member memberTo = memberService.findById(this.currentLog.getToMemberId());
			this.currentLog.setToMember(memberTo);
			
			UserDefinedValues udLogType = udService.loadById(this.currentLog.getMemberActivityType());
			this.currentLog.setUserDefinedValues(udLogType);
			
			/** Set audit fields */
			this.currentLog.setRecordCreatorId(this.getMember().getMemberId()+ "");
			this.currentLog.setRecordCreatedDate(new Date());
			this.currentLog.setRecordLastUpdatedDate(new Date());
			this.currentLog.setRecordLastUpdaterId(this.getMember().getMemberId()+ "");
			this.currentLog.setStatus(CommonConstants.ACTIVITY_STATUS.PENDING.toString());
			this.currentLog.setFromMemberId(this.getMember().getMemberId());
			this.currentLog.setFromMember(this.getMember());

			Date dt = new Date();
			this.currentLog.setMemberLogDateTime(new Timestamp(dt.getTime()));
			
			if (action.equals("replay")) {
				this.currentLog.setStatus(CommonConstants.ACTIVITY_STATUS.COMPLETED.toString());
				List<MemberLog> lines = (List<MemberLog>) getMessageLines()
						.getWrappedData();
				lines.add(this.currentLog);
			}
			this.memberLogService.save(this.currentLog);
			
			log.debug("Sending email notification");
			String proteemail = this.memberService.findById(this.currentLog.getToMemberId()).getEmail();
			String emailSub = this.udService.loadById(this.currentLog.getMemberActivityType()).getUdValuesValue();

			this.sendConfirmationEmail(emailSub, proteemail);

		} catch (Exception ex) {
			this.errMsgs.add(this.getText("common_error_system_level"));
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		this.resetMessage();

	}

	/** Send confirmation email to member */
	private void sendConfirmationEmail(String type, String email)
			throws Exception {

		String msgBody = this.getText("email_activity_log_body",
				new String[] { type });
		String emailSubject = this.getText("email_activity_log_subject",
				new String[] { type });
		/** Send email notification */
		Emailer emailer = new Emailer(email, msgBody, emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();
	}

	private void validate() {
		if (CommonValidator.isEmpty(this.currentLog.getTopic())) {
			this.errMsgs.add(this.getText("activity_log_validation_subject"));
		}

	}

	public void loadAllMemberLog() {
		try {
		  Long recordId = this.getMember().getMemberId();
		  if ( recordId != null ) {
  		  logger.debug(" Load All Member Log");
  			List<MemberLog> recordList = memberLogService.loadMemberLogByActivityType(recordId);
  			this.getMessageLines().setWrappedData(recordList);
  			this.setActionType(ServiceConstants.UPDATE);
		  }	
		} catch (Exception ex) {
			logger.error("Unable to load Members:" + ex.getMessage());
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
			List<MemberLog> recordList = memberLogService.findMemberLogByDate(
					fromDate, toDate);
			getMessageLines().setWrappedData(recordList);
		} catch (Exception ex) {
			logger.error("Message by search critariya:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteMemberLog() {
		logger.debug("Deleting member log");
		setErrorMessage("");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				int rowIndex = getMessageLines().getRowIndex();
				MemberLog memberLog = memberLogService.findById(Long.parseLong(recordId));
				memberLogService.delete(memberLog);
				List<MemberLog> list = (List<MemberLog>) getMessageLines().getWrappedData();
				if(list != null && list.size() > 0){
					list.remove(rowIndex);
				}
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void preReplay() {
		setErrorMessage("");
		logger.debug(" Preparing member log for replay ");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {

			try {

				this.showSearchBox = false;
				this.isActivityTypeReadOnly = true;
				MemberLog mLog = (MemberLog) this.getMessageLines()
						.getRowData();
				this.currentLog = new MemberLog();
				this.currentLog.setFromMemberId(mLog.getToMemberId());
				this.setMemberName(mLog.getToMember().getFirstName());
				this.currentLog.setTopic("Ref : " + mLog.getTopic());
				this.setActionType(ServiceConstants.UPDATE);
				this.setReRenderIds("MESSAGE_LINES");
				this.currentLog.setMemberActivityType(CommonConstants.ACTIVITY_TYPE_MESSAGE);
				this.errMsgs.clear();
	      this.hasError = false;
	      this.showSearchBox = false;
	      this.isActivityTypeReadOnly = true;
				setSecHeaderMsg(this.getText("header_msg_contact_us") + " "
						+ this.getText("header_msg_update"));
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public List<MemberLog> getMemberLogs() {
		try {
			this.memberLogs = this.memberLogService.getAllPendingLog(this
					.getMember().getMemberId());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

		return memberLogs;
	}

	public void setMemberLogs(List<MemberLog> memberLogs) {
		this.memberLogs = memberLogs;
	}

	public MemberLog getCurrentLog() {
		if (currentLog == null)
			currentLog = new MemberLog();
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
		try {
			int dayCount = Integer.parseInt(this.ddDay);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			if(dayCount == 0){
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 1);
			}else{
				cal.add(Calendar.DATE, dayCount);
			}
			
			this.memberLogsOld = this.memberLogService.getAllCompletedLog(this.getMember().getMemberId(),cal.getTime());
		} catch (Exception ex) {
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
	 * @param fromDate
	 *            the fromDate to set
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
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the messageLines
	 */
	public ListDataModel getMessageLines() {
		if (messageLines == null) {
			messageLines = new ListDataModel();
			this.loadAllMemberLog();
		}
		return messageLines;
	}

	/**
	 * @param messageLines
	 *            the messageLines to set
	 */
	public void setMessageLines(ListDataModel messageLines) {
		this.messageLines = messageLines;
	}

	public Boolean getShowSearchBox() {
		return showSearchBox;
	}

	public void setShowSearchBox(Boolean showSearchBox) {
		this.showSearchBox = showSearchBox;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public boolean getIsActivityTypeReadOnly() {
		return isActivityTypeReadOnly;
	}

	public void setIsActivityTypeReadOnly(boolean isActivityTypeReadOnly) {
		this.isActivityTypeReadOnly = isActivityTypeReadOnly;
	}

	public List<MemberLog> getMemberLastWeekLogs() {
		this.initRecentLog();
		return memberLastWeekLogs;
	}

	public void setMemberLastWeekLogs(List<MemberLog> memberLastWeekLogs) {
		this.memberLastWeekLogs = memberLastWeekLogs;
	}

	public String getDdDay() {
		return ddDay;
	}

	public void setDdDay(String ddDay) {
		this.ddDay = ddDay;
	}

}
