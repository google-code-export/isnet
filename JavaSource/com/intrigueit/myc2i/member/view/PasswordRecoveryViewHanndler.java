package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.email.HtmlEmailerTask;
import com.intrigueit.myc2i.email.TaskExecutionPool;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("passRecViewHandler")
@Scope("request")
public class PasswordRecoveryViewHanndler extends BasePage implements Serializable{

	/**
	 * Serialize version no
	 */
	private static final long serialVersionUID = 3366162514019941839L;
	private ViewDataProvider vwdataProvider;
	private MemberService memberService;
	
	private String email;
	private String question1Id;
	private String question1Ans;
	
	private String question2Id;
	private String question2Ans;
	
	private ArrayList<SelectItem> questionList1;
	private ArrayList<SelectItem> questionList2;
	
	
	/**
	 * @param memberService
	 */
	@Autowired
	public PasswordRecoveryViewHanndler(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public void loadQuestion(){
		try{
			Member member = this.memberService.loadMemberByEmail(this.getEmail());
			if(member == null){
				this.question1Id = "";
				this.question2Id = "";
				return;
			}
			this.question1Id = member.getSecurityQuestion1();
			this.question2Id = member.getSecurityQuestion2();
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
	}
	public void recoverMyPassword(){
		try{
			
			resetMessage();
			
			/** Validation phase 1 */
			this.validate();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}
			Member member = this.memberService.loadMemberByEmail(this.getEmail());
			
			this.invalidSecurityQuestion(member);
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}			
			/** Validation phase 2 */
			this.validateMemberInformation(member);
			
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}
			
			CryptographicUtility crp = CryptographicUtility.getInstance();
			this.sendConfirmationEmail(member.getEmail(), crp.getDeccryptedText(member.getPassword()));
			this.setSuccessMessage(this.getText("password_recovery_confirmation_message"));
			this.resetFields();
		}
		catch(Exception ex){
			this.errMsgs.add(this.getText("password_recover_unable_to_recover_password"));
			this.hasError = true;
			log.error(ex.getCause());
		}
	}
	private void resetFields(){
		this.question1Ans = "";
		this.question2Ans = "";
		this.question1Id = "";
		this.question2Id = "";
	}
	/** Send confirmation email to member */
	private void sendConfirmationEmail(String email, String password)throws Exception{
		
		String emailSubject  = this.getText("email_password_recovery_confirmation_subject");
		String msgBody = this.getText("email_password_recovery_confirmation_body", new String[]{email,password});
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);

		Runnable task = new HtmlEmailerTask(emailer);
		TaskExecutionPool pool =  (TaskExecutionPool) this.getBean("taskPool");
		pool.addTaskToPool(task);

	}	
	private void validateMemberInformation(Member member){
		if(member == null ||
			!member.getSecurityQuestion1().equals(this.getQuestion1Id()) ||
			!member.getSecurityQuestionAns1().equals(this.getQuestion1Ans()) ||
			!member.getSecurityQuestion2().equals(this.getQuestion2Id())||
			!member.getSecurityQuestionAns2().equals( this.getQuestion2Ans())){
			
			this.errMsgs.add( this.getText("password_recovery_invalid_member"));
		}
		
	}
	private void invalidSecurityQuestion(Member member){
		if(member == null ||
			member.getSecurityQuestion1() == null ||
			member.getSecurityQuestionAns1() == null ||
			member.getSecurityQuestion2() == null ||
			member.getSecurityQuestionAns2() == null ){
			
			this.errMsgs.add("No security questions are set yet.You need to login first with initial password.");
		}
		
	}
	private void validate(){
		/** Check email address */
		if(!CommonValidator.isValidEmail(this.getEmail())){
			this.errMsgs.add( this.getText("member_validation_email_address"));
		}
		
		if(CommonValidator.isEmpty(this.question1Id)){
			this.errMsgs.add( this.getText("change_password_security_question1_chose"));
		}		
		if(CommonValidator.isEmpty(this.question1Ans)){
			this.errMsgs.add( this.getText("change_password_security_question1_empty"));
		}	
		if(CommonValidator.isEmpty(this.question2Id)){
			this.errMsgs.add( this.getText("change_password_security_question2_chose"));
		}		
		if(CommonValidator.isEmpty(this.question2Ans)){
			this.errMsgs.add( this.getText("change_password_security_question2_empty"));
		}	
	}
	public ArrayList<SelectItem> getQuestionList1() {
		if(questionList1 == null){
			this.questionList1 = this.getVwdataProvider().getQuestionList();
		}
		return questionList1;
	}

	public void setQuestionList1(ArrayList<SelectItem> questionList1) {
		this.questionList1 = questionList1;
	}

	public ArrayList<SelectItem> getQuestionList2() {
		if(questionList2 == null){
			this.questionList2 = this.getVwdataProvider().getQuestionList();
		}
		return questionList2;
	}

	public void setQuestionList2(ArrayList<SelectItem> questionList2) {
		this.questionList2 = questionList2;
	}

	public void test(){
		log.debug("Recovery user password");
		
	}
	public void sendPassWord(){
		log.debug("Send your password");
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion1Id() {
		return question1Id;
	}

	public void setQuestion1Id(String question1Id) {
		this.question1Id = question1Id;
	}

	public String getQuestion1Ans() {
		return question1Ans;
	}

	public void setQuestion1Ans(String question1Ans) {
		this.question1Ans = question1Ans;
	}

	public String getQuestion2Id() {
		return question2Id;
	}

	public void setQuestion2Id(String question2Id) {
		this.question2Id = question2Id;
	}

	public String getQuestion2Ans() {
		return question2Ans;
	}

	public void setQuestion2Ans(String question2Ans) {
		this.question2Ans = question2Ans;
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
