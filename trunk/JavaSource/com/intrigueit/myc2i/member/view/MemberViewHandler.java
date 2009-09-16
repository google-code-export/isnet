package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.utility.Emailer;



@Component("memberViewHandler")
@Scope("request")
public class MemberViewHandler extends BasePage implements Serializable{

	private static final long serialVersionUID = 1L;
	private MemberService memberService;
	private Member currentMember;
	private Long id=  21L;	
	
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
	public void test(){
		this.currentMember = this.memberService.findById(id);
		logger.debug(this.currentMember.getFirstName());
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
	public void registerMentor(){
		
		logger.debug("Registering Mentor");
		this.hasError = false;
		try{
			this.validateMember();
			this.validationPhase2();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}
			
			CryptographicUtility crpUtil = new CryptographicUtility();
			String plainPassword = this.currentMember.getPassword();
			this.currentMember.setPassword( crpUtil.getEncryptedText(this.currentMember.getPassword()));
			Date dt = new Date();
			this.currentMember.setRecordCreate(dt);
			this.currentMember.setLastUpdated(dt);
			this.currentMember.setRecordCreatorId("-1");
			this.currentMember.setRecordUpdaterId("-1");
			this.currentMember.setTypeId(CommonConstants.MENTOR);
			this.currentMember.setMemberRoleId(CommonConstants.ROLE_GUEST);
			this.currentMember.setAdminUserIndicator(CommonConstants.STATUS.No.toString());
			this.memberService.save(this.currentMember);
			this.sendConfirmationEmail(this.currentMember.getEmail(), plainPassword);
			
			logger.debug("Mentor added: "+ this.currentMember.getMemberId());
			this.currentMember= new Member();
			this.setSuccessMessage(this.getText("mentor_success"));
		}
		catch(Exception ex){
			this.hasError = true;
			this.errMsgs.add(this.getText("member_register_system_error"));
			ex.printStackTrace();
			this.setSuccessMessage("");
		}
		this.resetPassword();
		
	}
	private void resetPassword(){
		if(this.currentMember == null){
			return;
		}
		this.currentMember.setPassword("");
		this.confirmPass = "";
	}
	public void registerGuest(){
		logger.debug("Registering Guest");
		this.hasError = false;
		try{
			this.validateMember();
			this.validationPhase2();
			if(this.errMsgs.size()>0){
				this.hasError = true;
				return;
			}

			CryptographicUtility crpUtil = new CryptographicUtility();
			String plainPassword = this.currentMember.getPassword();
			this.currentMember.setPassword( crpUtil.getEncryptedText(this.currentMember.getPassword()));
			Date dt = new Date();
			this.currentMember.setRecordCreate(dt);
			this.currentMember.setLastUpdated(dt);
			this.currentMember.setTypeId(CommonConstants.PROTEGE);
			this.currentMember.setMemberId(null);
			
			this.memberService.save(this.currentMember);
			//this.sendConfirmationEmail(this.currentMember.getEmail(), plainPassword);

			logger.debug("Guest added: "+ this.currentMember.getMemberId());
			this.currentMember = new Member();
			this.setSuccessMessage(this.getText("mentor_success"));
		}
		catch(Exception ex){
			this.hasError = true;
			this.errMsgs.add(this.getText("member_register_system_error"));
			log.error(ex.getStackTrace());
			ex.printStackTrace();
			this.setSuccessMessage("");
		}		
		this.resetPassword();
	}
	
	/** Send confirmation email to member */
	public void sendConfirmationEmail(String email, String password)throws Exception{
		
		String msgBody = this.getText("email_account_confirmation_body", new String[]{email,password});
		String emailSubject = this.getText("email_account_confirmation_subject");
		/**Send email notification */
		Emailer emailer = new Emailer(email, msgBody,emailSubject);
		emailer.setContentType("text/html");
		emailer.sendEmail();		
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
		/** Check the Zip code */
		if(!CommonValidator.isValidZipCode(this.currentMember.getZip())){
			this.errMsgs.add( this.getText("member_validation_zip_code"));
		}		
		/** Check email address */
		if(!CommonValidator.isValidEmail(this.getCurrentMember().getEmail())){
			this.errMsgs.add( this.getText("member_validation_email_address"));
		}
		/** Check the last name */
		if(CommonValidator.isEmpty(this.getCurrentMember().getPassword())){
			this.errMsgs.add( this.getText("member_validation_password"));
		}		
		/** Check the member profession */
		if(CommonValidator.isEmpty(this.getCurrentMember().getProfession())){
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
		if(!this.confirmPass.equals(this.currentMember.getPassword())){
			this.errMsgs.add( this.getText("member_validation_password_dont_match"));
		}
		
	}
	
	private void validationPhase2(){
		if(this.memberService.isMemberExist(this.currentMember.getEmail())){
			this.errMsgs.add(this.getText("member_validation_email_exist"));
		}
		if(this.getAgree() == false){
			this.errMsgs.add(this.getText("member_validation_licence_agree"));
		}		
	}
	public Member getCurrentMember() {
		return currentMember;
	}
	public void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
	}
	public List<SelectItem> getStatesList() {
		return viewDataProvider.getStateList();
	}
	
	public List<SelectItem> getCountryList() {
		return this.viewDataProvider.getCountryList();
	}

	public List<SelectItem> getEthinicityList() {
		return this.viewDataProvider.getEthinicityList();
	}

	public ArrayList<SelectItem> getMartialStatusList() {
		if(martialStatusList == null){
			this.martialStatusList = ViewDataProvider.getMaritialStatusList();
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
		return viewDataProvider.getProfessionList();
	}

	public List<SelectItem> getMadhabList() {
		return this.viewDataProvider.getMadhabList();
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
		return this.viewDataProvider.getReligionList();
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
