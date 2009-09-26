package com.intrigueit.myc2i.member.view;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberExService;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("myChgPasswordViewHandler")
@Scope("session") 
public class MyChgPasswordViewHandler extends BasePage implements Serializable {	
  
  private static final long serialVersionUID = -8177894044709887971L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( MyChgPasswordViewHandler.class );
  
  private MemberService memberService;
	private Member currentMember;
	
	@NotNull
  @NotEmpty
  private String oldPassword;
  
  @NotNull
  @NotEmpty
  private String newPassword;
  
  @NotNull
  @NotEmpty
  private String confirmPassword;
	/** Available transfer methods*/    
  @Autowired
	public MyChgPasswordViewHandler(MemberExService memberExService,MemberService memberService) {
		this.memberService = memberService;
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
	
	public boolean validate () throws Exception{
    logger.debug(" Validating member ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if ( this.currentMember == null ) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    } else {      
      CryptographicUtility crp = new CryptographicUtility();
      String oldPass = crp.getDeccryptedText(this.currentMember.getPassword());
      if (oldPass == null || !oldPass.equals(this.getOldPassword())) {
        errorMessage.append(this.getText("common_error_prefix"))
                    .append(" ")
                    .append(this.getText("user_password_miss_match_msg"));
         flag = false;
      }
      if ( !this.getNewPassword().equals(this.getConfirmPassword())) {
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("user_oldpass_mewpass_notmatch"));
        flag = false;
      }
    }
    if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
    return flag;
  }
	
	public void clearForm() {
	  this.setOldPassword("");
	  this.setNewPassword("");
	  this.setConfirmPassword("");
	}
	
	 public void resetUserPassword () {
	    logger.debug(" Change user password ");
	    setErrorMessage("");
	    try {
	      if(validate()) {         
	        CryptographicUtility crp = new CryptographicUtility();
	        this.currentMember.setPassword(crp.getEncryptedText(this.getNewPassword()));
	        memberService.update(this.currentMember);
	        this.setErrorMessage(this.getText("update_password_success_message"));
	        this.setMsgType(ServiceConstants.INFO);
	        
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
   * @return the oldPassword
   */
  public String getOldPassword() {
    return oldPassword;
  }

  /**
   * @param oldPassword the oldPassword to set
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * @return the newPassword
   */
  public String getNewPassword() {
    return newPassword;
  }

  /**
   * @param newPassword the newPassword to set
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  /**
   * @return the confirmPassword
   */
  public String getConfirmPassword() {
    return confirmPassword;
  }

  /**
   * @param confirmPassword the confirmPassword to set
   */
  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
