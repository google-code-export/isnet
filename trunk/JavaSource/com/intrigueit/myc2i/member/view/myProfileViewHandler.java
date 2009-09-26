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
import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberExService;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("myProfileViewHandler")
@Scope("session") 
public class myProfileViewHandler extends BasePage implements Serializable {	
  private static final long serialVersionUID = 2098951095935218884L;
  
  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( myProfileViewHandler.class );
  
  private MemberService memberService;
	private Member currentMember;
	private ViewDataProvider viewDataProvider;
	/** Available transfer methods*/
  private ArrayList<SelectItem> martialStatusList;
  private ArrayList<SelectItem> birthYearlist;
  private ArrayList<SelectItem> knowledgeLevelList;
  private String confirmPass;
  private Boolean agree = false;
  
  private ArrayList<SelectItem> question1List; 
  private ArrayList<SelectItem> question2List;
  
  
  @Autowired
	public myProfileViewHandler(MemberExService memberExService,MemberService memberService,
	    ViewDataProvider viewDataProvider) {
		this.memberService = memberService;
		this.viewDataProvider = viewDataProvider;
		this.initialize();
	} 
    
	public void initialize(){		
	  loadMember();
	}
	
	public void loadMember() {   
    try {
      logger.debug(" Load Member ");    
      Long recordId = this.getMember().getMemberId();
      this.currentMember = memberService.findById(recordId);
      this.setActionType(ServiceConstants.UPDATE);
    }catch (Exception ex) {
      logger.error("Unable to load Members:"+ex.getMessage());
      ex.printStackTrace();
    }
  }
	
	public boolean validate(){
	  logger.debug(" Validating member ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if ( this.currentMember == null ) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    } else {      
      if(CommonValidator.isEmpty(this.getCurrentMember().getFirstName())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_first_name"));
        flag = false;
      }
      /** Check the last name */
      if(CommonValidator.isEmpty(this.getCurrentMember().getLastName())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_last_name"));
        flag = false;
      }      
      /** Check the Zip code */
      if(!CommonValidator.isValidZipCode(this.currentMember.getZip())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_zip_code"));
        flag = false;
      }         
      /** Check the member profession */
      if(CommonValidator.isEmpty(this.getCurrentMember().getProfession())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_profession"));
        flag = false;
      }
      /** Check the Year of birth*/
      if(CommonValidator.isNotValidNumber(this.getCurrentMember().getBirthYear())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_birth_year"));
        flag = false;
      }
      /** Check the member gender */
      if(CommonValidator.isEmpty(this.getCurrentMember().getGenderInd())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_gender"));
        flag = false;
      }
      
      if(CommonValidator.isEmpty(this.getCurrentMember().getSecurityQuestion1())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question1_chose"));
        flag = false;
      }   
      if(CommonValidator.isEmpty(this.getCurrentMember().getSecurityQuestionAns1())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question1_empty"));
        flag = false;
      } 
      if(CommonValidator.isEmpty(this.getCurrentMember().getSecurityQuestion2())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question2_chose"));
        flag = false;
      }   
      if(CommonValidator.isEmpty(this.getCurrentMember().getSecurityQuestionAns2())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question2_empty"));
        flag = false;
      }
    }
    if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
    return flag;
  }
	private boolean validationPhase2(){
	  logger.debug(" Validating member ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();    
    if(this.getAgree() == false){
      if ( !flag )errorMessage.append("<br />");
      errorMessage.append(this.getText("common_error_prefix")).append(" ")
                  .append(this.getText("member_validation_licence_agree"));
      flag = false;
    }
    if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
    return flag;
  }
	public void setCommonData ( String action ) {
    setSecHeaderMsg("");    
    try {           
       Date dt = new Date();           
       this.currentMember.setRecordUpdaterId(""+this.getMember().getMemberId());    
       this.currentMember.setLastUpdated(dt);       
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message")); 
      logger.error(" Unable to set common data :"+e.getMessage());
       e.printStackTrace();
     }
   }	
	
	public void reloadUser() {
	  Long recordId = this.getMember().getMemberId();
    this.currentMember = memberService.findById(recordId);
	}
	
	public void updateUser () {
    logger.debug(" Updating user ");
    setErrorMessage("");
    try {
      if(validate()) {         
        //if (validationPhase2()) {         
          this.memberService.update(this.currentMember);    
          logger.debug("Member updated: "+ this.currentMember.getMemberId());
          this.setErrorMessage(this.getText("update_success_message"));
          this.setMsgType(ServiceConstants.INFO);
        //}
      }
    } catch (Exception e) {
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }
	
	/**
	 * @return the currentMember
	 */
	public Member getCurrentMember() {
		if (currentMember == null) {
			System.out.println(" Init member ");
		  currentMember = new Member();
		}
		return currentMember;
	}

	/**
	 * @param currentMember the currentMember to set
	 */
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
  public Boolean getAgree() {
    return agree;
  }
  public void setAgree(Boolean agree) {
    this.agree = agree;
  }
  public String getConfirmPass() {
    return confirmPass;
  }
  public void setConfirmPass(String confirmPass) {
    this.confirmPass = confirmPass;
  }
    
  public ArrayList<SelectItem> getQuestion1List() {
    if(question1List == null){
      this.question1List = viewDataProvider.getQuestionList();
    }
    return question1List;
  }

  public void setQuestion1List(ArrayList<SelectItem> question1List) {
    this.question1List = question1List;
  }

  public ArrayList<SelectItem> getQuestion2List() {
    if(question2List == null){
      this.question2List = viewDataProvider.getQuestionList();
    }
    return question2List;
  }
  
  public void setQuestion2List(ArrayList<SelectItem> question2List) {
    this.question2List = question2List;
  }
}
