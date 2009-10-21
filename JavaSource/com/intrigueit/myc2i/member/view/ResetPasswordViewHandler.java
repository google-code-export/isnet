package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("resetPasswordViewHandler")
@Scope("session") 
public class ResetPasswordViewHandler extends BasePage implements Serializable {	
  /**
   * 
   */
  private static final long serialVersionUID = -8545336293801428884L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( ResetPasswordViewHandler.class );
  
  private MemberService memberService;
	private Member currentMember;
	private ListDataModel memberLines;
	private SearchBean searchBean;
	private ArrayList<SelectItem> usersList;
	private ViewDataProvider viewDataProvider;
	private Long recordId;
	
	@NotNull
	@NotEmpty
	private String oldPassword;
  
	@NotNull
  @NotEmpty
	private String newPassword;
  
	@NotNull
  @NotEmpty
	private String confirmPassword;
  
  @Autowired
	public ResetPasswordViewHandler(MemberService memberService,
	    ViewDataProvider viewDataProvider) {
		this.memberService = memberService;
		this.viewDataProvider = viewDataProvider;
		this.initialize();
	} 
    
	public void initialize(){		
	  loadAllMembers();
	}
	
	public void loadAllMembers() {   
    try {
      logger.debug(" Load Members ");    
      List<Member> memberList = memberService.findAll();
      getMemberLines().setWrappedData(memberList);
    }catch (Exception ex) {
      logger.error("Unable to load Members:"+ex.getMessage());
      ex.printStackTrace();
    }
  }
	
	public void loadMembersByCriteria() {
    try {
      logger.debug(" Load Members by search critariya ");    
      SearchBean value = getSearchBean();
      System.out.println(value.getRecordId());
      List<Member> memberList = memberService.findByProperties(value);
      getMemberLines().setWrappedData(memberList);
    }catch (Exception ex) {
      logger.error("Members by search critariya:"+ex.getMessage());
      ex.printStackTrace();
    }
  }
	
	public boolean validate (Member member) throws Exception{
    logger.debug(" Validating member ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if ( member == null ) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    } else {      
      CryptographicUtility crp = new CryptographicUtility();
      String oldPass = crp.getDeccryptedText(member.getPassword());
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
	
	
	public void preResetPassword () {
	  logger.debug(" Reset user password :: "+this.getParameter(ServiceConstants.RECORD_ID));
    if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        setErrorMessage("");        
        this.currentMember = memberService.findById(Long.parseLong(recordId));
        this.setOldPassword("");
        this.setNewPassword("");
        this.setConfirmPassword("");
        setSecHeaderMsg(this.getText("header_msg_reset_user_password"));
        setActionType(ServiceConstants.UPDATE);
        setReRenderIds("MEMBER_LINES");
        setRowIndex(getMemberLines().getRowIndex());
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
	}
	
	public void resetUserPassword () {
    logger.debug(" Change user password ");
    setErrorMessage("");
    try {
      if(validate(this.currentMember)) {         
        CryptographicUtility crp = new CryptographicUtility();
        this.currentMember.setPassword(crp.getEncryptedText(this.getNewPassword()));
        memberService.update(this.currentMember);
      }
    } catch (Exception e) {
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }
	
	public List<SelectItem> getStatesList() {
    return viewDataProvider.getStateList();
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
	
	/**
	 * @return the memberLines
	 */
	public ListDataModel getMemberLines() {
		if(memberLines == null){
			memberLines = new ListDataModel();
		}
		return memberLines;
	}
	
	/**
	 * @param memberLines the memberLines to set
	 */
	public void setMemberLines(ListDataModel memberLines) {
		this.memberLines = memberLines;
	}	
	public ArrayList<SelectItem> getUsersList() {
    if(usersList == null){
      this.usersList = viewDataProvider.getUserTypes();
    }
    return usersList;
  }
  public void setUsersList(ArrayList<SelectItem> usersList) {
    this.usersList = usersList;
  } 
  
  public SearchBean getSearchBean() {
    if (searchBean == null) {
      searchBean = new SearchBean();
    }
    return searchBean;
  }

  public void setSearchBean(SearchBean searchBean) {
    this.searchBean = searchBean;
  }
  
  
  /**
   * @return the recordId
   */
  public Long getRecordId() {
    return recordId;
  }

  /**
   * @param recordId the recordId to set
   */
  public void setRecordId(Long recordId) {
    this.recordId = recordId;
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
