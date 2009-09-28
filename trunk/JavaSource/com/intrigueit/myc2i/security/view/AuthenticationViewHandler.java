 /**
 * @(#)AuthenticationViewHandler.java
 *
 * Copyright (c)  Ltd 2009
 * 
 */
package com.intrigueit.myc2i.security.view;


import java.io.Serializable;
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

@Component("authenticationViewHandler")
@Scope("session")
public class AuthenticationViewHandler extends BasePage implements Serializable {

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
	}

	public String loginUser(){
		String navOutCome = "";
		try{
			log.debug("Looging user...");
			Member member  = this.getValidUser();
			if(member != null){
				//this.getSession().setAttribute(CommonConstants.CURRENT_MEMMBER, member);
				this.setMemberinSession(member);
				navOutCome = this.getHomePageAddress(member);
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
			session.invalidate();
			//this.getResponse().sendRedirect(this.getRequest().getContextPath() + "/login.jsf");
			
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
			return ViewConstant.TO_MENTOR_DASHBOARD;
		}

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
	
	
	
}
