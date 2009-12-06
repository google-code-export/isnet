 /**
 * @(#)AuthenticationViewHandler.java
 *
 * Copyright (c)  Ltd 2009
 * 
 */
package com.intrigueit.myc2i.security.view;


import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.Myc2iException;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewConstant;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.role.domain.RolePageAccess;
import com.intrigueit.myc2i.security.Menu;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

@Component("authenticationViewHandler")
@Scope("session")
public class AuthenticationViewHandler extends BasePage implements Serializable {

	private Menu menu ;
	
	private Boolean checked;
	
	private String email_cokie = "__EMAIL_ID";
	private String pass_cokie = "__PASS_COKIE";
	private String chk_cokie = "__CHECKED";
	private String init;
	private String mentorStory;
	private String protegeStory;
	
	private UDValuesService udService;
	
	/**
	 * Serialized version no
	 */
	private static final long serialVersionUID = 6151863188013949687L;
	private MemberService memberService;
	

	/**
	 * User password
	 */
	private String password;
	/**
	 * User Login id.
	 */
	private String userEmailId;
	
	/***/
	private String errMessage;
	
	private Boolean isLogged;
	
	
	
	/**
	 * @param memberService
	 */
	@Autowired
	public AuthenticationViewHandler(MemberService memberService) {
		this.memberService = memberService;
		menu = new Menu();
	}
	private void storeInCokie(){

		CryptographicUtility crpUtil = new CryptographicUtility();		
		try{
			if(this.getChecked()){
				String pass = crpUtil.getEncryptedText(this.getPassword());
				pass = URLEncoder.encode(pass,"UTF-8");
				String email = this.getUserEmailId();
				email = URLEncoder.encode(email,"UTF-8");
				this.storeCokie(email_cokie, email);
				this.storeCokie(pass_cokie, pass);
			}
			this.storeCokie(chk_cokie, this.getChecked().toString());

		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		
	}
	private void restoreCokie(){
		CryptographicUtility crpUtil = new CryptographicUtility();	
		try{
			String chk = this.getCookieValue(chk_cokie);
			if(chk != null && !chk.equals("")){
				this.setChecked(Boolean.parseBoolean(chk));
				if(this.getChecked()){
					String pass = this.getCookieValue(pass_cokie);
					pass = URLDecoder.decode(pass,"UTF-8");
					String dec = crpUtil.getDeccryptedText(pass);
					if(!pass.equals("")){
						this.setPassword(dec);
					}
					String email = URLDecoder.decode(this.getCookieValue(email_cokie),"UTF-8");
					this.setUserEmailId(email);	
				}
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	public String loginUser(){
		String navOutCome = "";
		try{
			Member member  = this.getValidUser();
			if(member != null){
				//this.getSession().setAttribute(CommonConstants.CURRENT_MEMMBER, member);
				this.setMemberinSession(member);
				this.setUserPrivilegePages(member.getTypeId());
				navOutCome = this.getHomePageAddress(member);
				this.storeInCokie();
			}
			else{
				this.errMessage = this.getText("lgoin_page_login_error");
			}
		}
		catch(Myc2iException mEx){
			this.errMessage = mEx.getMessage();
		}
		catch(Exception ex){
			this.errMessage = this.getText("lgoin_page_login_system_error");
			ex.printStackTrace();
			log.error(ex.getStackTrace());
		}
		return navOutCome;
	}
	
	public void setUserPrivilegePages(Long memberTypeId) {	  
	  try {
  	  List<RolePageAccess> privilegePagesList = this.memberService.loadUserPrivilegePages(memberTypeId);    
  	  if (privilegePagesList !=null) {
  	    ArrayList<String> privilegePages = new ArrayList<String>();
  	    for (RolePageAccess rolePageAccess : privilegePagesList) {
          if ( rolePageAccess.getApplicationPages() != null ) {
            privilegePages.add(rolePageAccess.getApplicationPages().getPageUrl());
          }
        }
        HttpSession session =  getRequest().getSession(true);    
        session.setAttribute(CommonConstants.USER_PRIVILEGE_PAGES, privilegePages);
      }
	  } catch (Exception e) {
	    log.debug(e.getMessage());
	    e.printStackTrace();
    } 
	}
	/**
	 * Logout the User from application and clear
	 * user information from session object.
	 * @return
	 * @throws Exception
	 */
	public String logOutUser()  {
		try{
			log.debug("Log out user");

			HttpSession session = getSession();
			session.removeAttribute(CommonConstants.SESSION_MEMBER_KEY);
			session.removeAttribute(CommonConstants.SESSION_MEMBER_EMAIL);
			session.removeAttribute(CommonConstants.USER_PRIVILEGE_PAGES);			
			session.invalidate();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return ViewConstant.BACK_TO_LOGIN;

	}	
	private void setMemberinSession(Member member) {
  		HttpSession session =  getRequest().getSession(true);
  		
		session.setAttribute(CommonConstants.SESSION_MEMBER_KEY, member);
		session.setAttribute(CommonConstants.SESSION_MEMBER_EMAIL, member.getEmail());
	  }
	
	/**
	 * Verify the User credentials during the login
	 * process.
	 * @return
	 * @throws Exception
	 */
	private Member getValidUser() throws Exception{
		String emailId = this.getUserEmailId();
		String pass = this.getPassword();
		Member member = null;
		if(emailId.equals("") || pass.equals("")){
			return null;
		}
		List<Member> mems = this.memberService.findByProperty("email", emailId);
		if(mems == null || mems.size() < 1){
			return null;
		}
		member = mems.get(0);
		CryptographicUtility crpUtil = new CryptographicUtility();
		String decPass = crpUtil.getDeccryptedText(member.getPassword());
		if(!pass.equals(decPass)){
			return null; 
		}
		return member;
	}
	/**
	 * */
	public String getHomePageAddress(Member member){
		
		if(isFirstTimeLogin(member)){
			return ViewConstant.OUT_COME_PASSWORD_CHANGE;
		}
		else{
			if(member.getTypeId()== 15L || member.getTypeId() == 16L){
				this.menu.setMentorTopMenu(true);
				return ViewConstant.TO_MENTOR_DASHBOARD;
			}
			else if(member.getTypeId() == 18L){
				this.menu.setAdminTopMenu(true);
				return ViewConstant.TO_ADMIN_HOME;
			}
			else if(member.getTypeId() == 17L){
				this.menu.setGuestTopMenu(true);
				return ViewConstant.TO_PROTEGE_DASHBOARD;
			}
		}
		return "";
	}
	/**
	 * Check if the record created date and last updated date is same
	 * to find the first time login 
	 * */
	public Boolean isFirstTimeLogin(Member member){
		
		Calendar calRecCreated = Calendar.getInstance();
		calRecCreated.setTime(member.getRecordCreate());
		
		Calendar calRecLastUpdated = Calendar.getInstance();
		calRecLastUpdated.setTime(member.getLastUpdated());
		if(calRecCreated.equals(calRecLastUpdated)){
			return true;
		}
		return false;
	}

	public String recoverPassword(){
		return ViewConstant.TO_PASSWORD_RECOVERY;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public Boolean getIsLogged() {
		if(this.getSession().getAttribute(CommonConstants.SESSION_MEMBER_EMAIL) == null){
			return false;
		}
		return true;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Boolean getChecked() {

		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getInit() {
		this.restoreCokie();
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public String getMentorStory() {
		this.loadMentorStory();
		return mentorStory;
	}
	public void setMentorStory(String mentorStory) {
		this.mentorStory = mentorStory;
	}
	public String getProtegeStory() {
		this.loadProtegeStory();
		return protegeStory;
	}
	public void setProtegeStory(String protegeStory) {
		this.protegeStory = protegeStory;
	}
	private void loadMentorStory(){
		try{
			this.mentorStory = this.udService.findByProperty("udValuesCategory", "MENTOR_STORY").get(0).getUdValuesDesc();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			this.mentorStory = "I saw them debasing themselves in search of sustenance and I thought about the verse, \"And there is not a beast in the earth but the sustenance thereof depends on Allah\", so I kept myself busy with my responsibilities toward Him and I left my property with Him";
		}
	}
	private void loadProtegeStory(){
		try{
			this.protegeStory = this.udService.findByProperty("udValuesCategory", "PROTEGE_STORY").get(0).getUdValuesDesc();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			this.protegeStory = "I saw them debasing themselves in search of sustenance and I thought about the verse, \"And there is not a beast in the earth but the sustenance thereof depends on Allah\", so I kept myself busy with my responsibilities toward Him and I left my property with Him";
		}
	}

	public UDValuesService getUdService() {
		return udService;
	}
	
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}	
	
	
	
}
