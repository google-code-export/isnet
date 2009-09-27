package com.intrigueit.myc2i.memberlog.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3248335429408226984L;
	private MemberLogService memberLogService;
	private MemberService memberService;
	
	private List<MemberLog> memberLogs;
	private List<MemberLog> memberLogsOld;
	
	private List<SelectItem> mentorList;
	
	private MemberLog currentLog;
	
	private ViewDataProvider viewDataProvider;


	@Autowired
	public MemberLogViewHandler(MemberLogService memberLogService,MemberService memberService) {
		this.memberLogService = memberLogService;
		this.memberService = memberService;
		this.initMemberLog();
	}
	
	public void initMemberLog(){
		this.currentLog = new MemberLog();
		this.errMsgs.clear();
		this.hasError = false;
		
	}
	public void saveMemberLog(){
		try{
			this.errMsgs.clear();
			this.validate();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}
			/** Set audit fields */
			this.currentLog.setRecordCreatorId(this.getMember().getMemberId());
			this.currentLog.setRecordCreatedDate(new Date());
			this.currentLog.setRecordLastUpdatedDate(new Date());
			this.currentLog.setRecordLastUpdaterId(this.getMember().getMemberId());
			this.currentLog.setStatus(CommonConstants.ACTIVITY_STATUS.PENDING.toString());
			Date dt = new Date();
			this.currentLog.setMemberLogDateTime(new Timestamp(dt.getTime()));
			
			this.memberLogService.save(this.currentLog);
			
			MemberLog lg = this.memberLogService.findById(this.currentLog.getMemberLogId());
			//log.debug("Sending email notification");
			//this.sendConfirmationEmail(this.currentLog.getUserDefinedValues().getUdValuesValue(), this.currentLog.getMember().getEmail());
			
			log.debug("member id: "+lg.getMember().getMemberId()+" type id: "+lg.getMemberActivityType());
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
	
	public List<MemberLog> getMemberLogs() {
		try{
			this.memberLogs = this.memberLogService.getAllPendingLog();
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
			this.memberLogsOld = this.memberLogService.getAllCompletedLog();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
				
		return memberLogsOld;
	}

public void setMemberLogsOld(List<MemberLog> memberLogsOld) {
	this.memberLogsOld = memberLogsOld;
}

	
}
