package com.intrigueit.myc2i.security.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.Myc2iException;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewConstant;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("changePassViewHandler")
@Scope("session")
public class ChangePasswordViewHandler extends BasePage  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
	

	private ViewDataProvider vwdataProvider;
	
	private String securityQuestion1;
	private String securityQuestion2;
	
	private String securityQuestionAns1;
	private String securityQuestionAns2;
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	private ArrayList<SelectItem> question1List; 
	private ArrayList<SelectItem> question2List;
	
	
	@Autowired
	public ChangePasswordViewHandler(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	/**
	 * Change user password
	 * @return
	 */
	public String changeMemberPassword(){
		try{
			this.errMsgs.clear();
			Member member = this.getMember();
			CryptographicUtility crp = CryptographicUtility.getInstance();
			this.checkOldPassword(crp.getDeccryptedText(member.getPassword()));
			this.validate();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return "";
			}			
			Member dbMember = this.memberService.findById(member.getMemberId());
			this.modifyMember(dbMember);
			this.sendConfirmationEmail(dbMember.getEmail(), newPassword);

			if(member.getTypeId().equals(CommonConstants.MENTOR)){
				return ViewConstant.TO_MENTOR_DASHBOARD;
			}
			else if(member.getTypeId().equals(CommonConstants.ADMIN)){
				return ViewConstant.TO_ADMIN_HOME;
			}
			else if(member.getTypeId().equals(CommonConstants.PROTEGE)){
				return ViewConstant.TO_PROTEGE_DASHBOARD;
			}			
		}
		catch(Myc2iException ex){
			this.errMsgs.add(ex.getMessage());
			log.error(ex.getMessage());
			ex.printStackTrace();
		}		
		catch(Exception ex){
			this.errMsgs.add(this.getText("change_password_unable_to_update"));
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}
	
	/** Send confirmation email to member */
	private void sendConfirmationEmail(String email, String password)throws Exception{
		
		String msgBody = this.getText("email_password_change_confirmation_body", new String[]{email,password});
		String emailSubject = this.getText("email_password_change_confirmation_subject");
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
	}	
	/**
	 * Update the member object with new information
	 * @param member
	 * @throws Exception
	 */
	private void modifyMember(Member member)throws Exception{
		CryptographicUtility crp = CryptographicUtility.getInstance();
		member.setLastUpdated(new Date());
		member.setPassword(crp.getEncryptedText(this.newPassword));
		member.setSecurityQuestion1(this.getSecurityQuestion1());
		member.setSecurityQuestion2(this.getSecurityQuestion2());
		member.setSecurityQuestionAns1(this.getSecurityQuestionAns1());
		member.setSecurityQuestionAns2(this.getSecurityQuestionAns2());
		this.memberService.save(member);
	}
	public void checkOldPassword(String actualPass){
		if(!this.oldPassword.equals(actualPass)){
			this.errMsgs.add( this.getText("change_password_change_validation_old_password_invalid"));
		}
	}
	public void validate(){
		if(!CommonValidator.isValidPassword(this.newPassword)){
			this.errMsgs.add( this.getText("member_validation_password"));
		}
		if(!this.newPassword.equals(this.confirmPassword)){
			this.errMsgs.add( this.getText("change_password_dont_match"));
		}
		if(CommonValidator.isEmpty(this.securityQuestion1)){
			this.errMsgs.add( this.getText("change_password_security_question1_chose"));
		}		
		if(CommonValidator.isEmpty(this.securityQuestionAns1)){
			this.errMsgs.add( this.getText("change_password_security_question1_empty"));
		}	
		if(CommonValidator.isEmpty(this.securityQuestion2)){
			this.errMsgs.add( this.getText("change_password_security_question2_chose"));
		}		
		if(CommonValidator.isEmpty(this.securityQuestionAns2)){
			this.errMsgs.add( this.getText("change_password_security_question2_empty"));
		}	
	}
	
	public String getSecurityQuestion1() {
		return securityQuestion1;
	}


	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}


	public String getSecurityQuestion2() {
		return securityQuestion2;
	}


	public void setSecurityQuestion2(String securityQuestion2) {
		this.securityQuestion2 = securityQuestion2;
	}


	public String getSecurityQuestionAns1() {
		return securityQuestionAns1;
	}


	public void setSecurityQuestionAns1(String securityQuestionAns1) {
		this.securityQuestionAns1 = securityQuestionAns1;
	}


	public String getSecurityQuestionAns2() {
		return securityQuestionAns2;
	}


	public void setSecurityQuestionAns2(String securityQuestionAns2) {
		this.securityQuestionAns2 = securityQuestionAns2;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public ArrayList<SelectItem> getQuestion1List() {
		if(question1List == null){
			this.question1List = this.getVwdataProvider().getQuestionList();
		}
		return question1List;
	}

	public void setQuestion1List(ArrayList<SelectItem> question1List) {
		this.question1List = question1List;
	}

	public ArrayList<SelectItem> getQuestion2List() {
		if(question2List == null){
			this.question2List = this.getVwdataProvider().getQuestionList();
		}
		return question2List;
	}

	public void setQuestion2List(ArrayList<SelectItem> question2List) {
		this.question2List = question2List;
	}

	public ViewDataProvider getVwdataProvider() {
		if(vwdataProvider == null){
			vwdataProvider= new ViewDataProvider();
		}
		return vwdataProvider;
	}

	public void setVwdataProvider(ViewDataProvider vwdataProvider) {
		this.vwdataProvider = vwdataProvider;
	}
	

}
