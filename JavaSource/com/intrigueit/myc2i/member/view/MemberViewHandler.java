package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.MyC2iConfiguration;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.utility.PassPhrase;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewConstant;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.email.EmailTemplate;
import com.intrigueit.myc2i.email.HtmlEmailerTask;
import com.intrigueit.myc2i.email.TaskExecutionPool;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.utility.Emailer;



@Component("memberViewHandler")
@Scope("request")
public class MemberViewHandler extends BasePage implements Serializable{

	private static final long serialVersionUID = 1L;
	private MemberService memberService;
	private Member currentMember;
	
	/** Available transfer methods*/
	private ArrayList<SelectItem> martialStatusList;
	private ArrayList<SelectItem> birthYearlist;
	private ArrayList<SelectItem> knowledgeLevelList;
	private ViewDataProvider viewDataProvider;
	private String confirmPass;
	private Boolean agree = false;

	/** Initialize the Logger */
	protected static final Logger logger = Logger.getLogger( MemberViewHandler.class );
		

	@Autowired
	public MemberViewHandler(MemberService memberService) {
		this.memberService = memberService;
		this.currentMember = new Member();
	}

	public void update(){
		try{
			this.memberService.update(this.currentMember);
			logger.debug(this.currentMember.getFirstName());			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	public String registerMentor(){
		
		logger.debug("Registering Mentor");
		this.hasError = false;
		try{
			this.validateMember();
			this.validationPhase3();
			this.validationPhase2();
			
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return "";
			}
			
			CryptographicUtility crpUtil = CryptographicUtility.getInstance();
			String plainPassword = PassPhrase.getNext();
			this.currentMember.setPassword( crpUtil.getEncryptedText(plainPassword));
			Date dt = new Date();
			this.currentMember.setRecordCreate(dt);
			this.currentMember.setLastUpdated(dt);
			this.currentMember.setRecordCreatorId("-1");
			this.currentMember.setRecordUpdaterId("-1");
			this.currentMember.setTypeId(CommonConstants.MENTOR);
			this.currentMember.setMemberRoleId(CommonConstants.ROLE_GUEST);
			this.currentMember.setAdminUserIndicator(CommonConstants.STATUS.No.toString());
			this.memberService.save(this.currentMember);
			
			
			this.sendConfirmationEmail(this.currentMember.getEmail(), plainPassword, this.currentMember.getFirstName() + ""+ this.currentMember.getLastName());
			
			logger.debug("Mentor added: "+ this.currentMember.getMemberId());
			this.currentMember= new Member();
			this.setSuccessMessage(this.getText("mentor_success"));
			return ViewConstant.REGISTER_SUCCESSFULL;
		}
		catch(Exception ex){
			this.hasError = true;
			this.errMsgs.add(this.getText("member_register_system_error"));
			ex.printStackTrace();
			this.setSuccessMessage("");
			log.error(ex.getStackTrace());
			log.error(ex.getMessage());
		}
		this.resetPassword();
		return "";
		
	}
	private void resetPassword(){
		if(this.currentMember == null){
			return;
		}
		this.currentMember.setPassword("");
		this.confirmPass = "";
	}
	public String registerGuest(){
		logger.debug("Registering Guest");
		this.hasError = false;
		try{
			this.validateMember();
			this.validationPhase2();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return "";
			}

			CryptographicUtility crpUtil = CryptographicUtility.getInstance();
			String plainPassword = PassPhrase.getNext();
			this.currentMember.setPassword( crpUtil.getEncryptedText(plainPassword));
			Date dt = new Date();
			this.currentMember.setRecordCreate(dt);
			this.currentMember.setLastUpdated(dt);
			this.currentMember.setRecordCreatorId(CommonConstants.SYSTEM_USER_ID);
			this.currentMember.setRecordUpdaterId(CommonConstants.SYSTEM_USER_ID);
			this.currentMember.setTypeId(CommonConstants.PROTEGE);
			this.currentMember.setMemberRoleId(CommonConstants.ROLE_GUEST);
			this.currentMember.setAdminUserIndicator(CommonConstants.STATUS.No.toString());
			
			this.memberService.save(this.currentMember);
			this.sendConfirmationEmail(this.currentMember.getEmail(), plainPassword,this.currentMember.getFirstName() + " "+ this.currentMember.getLastName());

			logger.debug("Guest added: "+ this.currentMember.getMemberId());
			this.currentMember = new Member();
			this.setSuccessMessage(this.getText("mentor_success"));
			return ViewConstant.REGISTER_SUCCESSFULL;
		}
		catch(Exception ex){
			this.hasError = true;
			this.errMsgs.add(this.getText("member_register_system_error"));
			log.error(ex.getStackTrace());
			log.error(ex.getMessage());
			ex.printStackTrace();
			this.setSuccessMessage("");
		}		
		this.resetPassword();
		return "";
	}
	
	/** Send confirmation email to member */
	public void sendConfirmationEmail(String email, String password,String name)throws Exception{
		String templateName = "email_template_basic.vm";
		
		Map<String, String> templateValues = new HashMap<String, String>();
		templateValues.put("myc2i_link", MyC2iConfiguration.getInstance().getAppDomainURL());
		templateValues.put("myc2i_link_label", MyC2iConfiguration.getInstance().getAppDomainLabel());
		templateValues.put("member_name", name);
		templateValues.put("message_body_text", this.getText("email_account_confirmation_body", new String[]{email,password}));
		EmailTemplate template = new EmailTemplate(templateName,templateValues);
		String msgBody = template.generate(); //
		String emailSubject = this.getText("email_account_confirmation_subject");
		
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		Runnable task = new HtmlEmailerTask(emailer);
		TaskExecutionPool pool =  (TaskExecutionPool) this.getBean("taskPool");
		pool.addTaskToPool(task);

	}
	
	public void validateMember(){
		this.errMsgs.clear();
		
		/**Check first name */
		if(CommonValidator.isEmpty(this.getCurrentMember().getFirstName())){
			this.errMsgs.add( this.getText("member_validation_first_name"));
		}
		/** Check the last name */
		if(CommonValidator.isEmpty(this.getCurrentMember().getLastName())){
			this.errMsgs.add( this.getText("member_validation_last_name"));
		}	
		/** Check the City  */
		if(CommonValidator.isEmpty(this.getCurrentMember().getCity())){
			this.errMsgs.add( this.getText("member_validation_city"));
		}		
		/** Check the State  */
		if(CommonValidator.isInvalidListItem(this.getCurrentMember().getState())){
			this.errMsgs.add( this.getText("member_validation_state"));
		}		
		/** Check the Zip code */
		if(!CommonValidator.isValidZipCode(this.currentMember.getZip())){
			this.errMsgs.add( this.getText("member_validation_zip_code"));
		}		
		/** Check email address */
		if(!CommonValidator.isValidEmail(this.getCurrentMember().getEmail())){
			this.errMsgs.add( this.getText("member_validation_email_address"));
		}
		/** Check the member profession */
		if(CommonValidator.isInvalidListItem(this.getCurrentMember().getProfession())){
			this.errMsgs.add( this.getText("member_validation_profession"));
		}	
		/** Check the Year of birth*/
		if(CommonValidator.isNotValidNumber(this.getCurrentMember().getBirthYear())){
			this.errMsgs.add( this.getText("member_validation_birth_year"));
		}
		/** Check the member gender */
		if(CommonValidator.isEmpty(this.getCurrentMember().getGenderInd())){
			this.errMsgs.add( this.getText("member_validation_gender"));
		}		
/*		if(!this.confirmPass.equals(this.currentMember.getPassword())){
			this.errMsgs.add( this.getText("member_validation_password_dont_match"));
		}*/
		/** Check the member profession */
		if(CommonValidator.isInvalidListItem(this.getCurrentMember().getEthinicity())){
			this.errMsgs.add( this.getText("member_validation_ethinicity"));
		}
		
/*		if (this.getCurrentMember().getMaritalStatus()!=null 
		    && this.getCurrentMember().getMaritalStatus().equals("-1") 
        && this.getCurrentMember().getTypeId() != CommonConstants.PROTEGE) {
		  this.errMsgs.add( this.getText("member_validation_marital_status"));
    } */  
/*		*//** Check the valid password *//*
		if(!CommonValidator.isValidPassword(this.getCurrentMember().getPassword())){
			this.errMsgs.add( this.getText("member_validation_password"));
		}			
		*/
		
	}
	
	private void validationPhase2(){
		if(this.memberService.isMemberExist(this.currentMember.getEmail())){
			this.errMsgs.add(this.getText("member_validation_email_exist"));
		}
		if(this.getAgree() == false){
			this.errMsgs.add(this.getText("member_validation_licence_agree"));
		}		
	}
	private void validationPhase3(){
/*		if(CommonValidator.isInvalidListItem(this.getCurrentMember().getMazhab())){
			this.errMsgs.add( this.getText("member_validation_madhab"));
		}*/
	
	}
	public Member getCurrentMember() {
		return currentMember;
	}
	public void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
	}
	public List<SelectItem> getStatesList() {
		return getViewDataProvider().getStateList();
	}
	
	public List<SelectItem> getCountryList() {
		return this.viewDataProvider.getCountryList();
	}

	public List<SelectItem> getEthinicityList() {
		return this.viewDataProvider.getEthinicityList();
	}

	public ArrayList<SelectItem> getMartialStatusList() {
		if(martialStatusList == null){
			this.martialStatusList = this.getViewDataProvider().getMaritialStatusList();
		}
		return martialStatusList;
	}
	public void setMartialStatusList(ArrayList<SelectItem> martialStatusList) {
		this.martialStatusList = martialStatusList;
	}
	public ArrayList<SelectItem> getBirthYearlist() {
		if(birthYearlist == null){
			this.birthYearlist = ViewDataProvider.getYearList();
		}
		return birthYearlist;
	}
	public void setBirthYearlist(ArrayList<SelectItem> birthYearlist) {
		this.birthYearlist = birthYearlist;
	}
	public List<SelectItem> getProfessionList() {
		return getViewDataProvider().getProfessionList();
	}

	public List<SelectItem> getMadhabList() {
		return this.getViewDataProvider().getMadhabList();
	}

	public ArrayList<SelectItem> getKnowledgeLevelList() {
		if(knowledgeLevelList == null){
			this.knowledgeLevelList = ViewDataProvider.getKnowledgeLevelList();
		}
		return knowledgeLevelList;
	}
	public void setKnowledgeLevelList(ArrayList<SelectItem> knowledgeLevelList) {
		this.knowledgeLevelList = knowledgeLevelList;
	}
	
	public List<SelectItem> getReligionList() {
		return this.getViewDataProvider().getReligionList();
	}

	public String backToLogin(){
		return "TO_LOGIN";
	}
	public ViewDataProvider getViewDataProvider() {
		return viewDataProvider;
	}

   @Autowired
	public void setViewDataProvider(ViewDataProvider viewDataProvider) {
		this.viewDataProvider = viewDataProvider;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	public Boolean getAgree() {
		return agree;
	}
	public void setAgree(Boolean agree) {
		this.agree = agree;
	}
	
}
