package com.intrigueit.myc2i.common.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;

@Component("navigationBean")
@Scope("session")
public class NavigationBean extends BasePage{

	public String backToLogin(){
		return ViewConstant.BACK_TO_LOGIN;
	}
	public String backToHome(){
		if(this.getMember() == null){
			return ViewConstant.BACK_TO_LOGIN;
		}
		if(this.getMember().getTypeId().equals(CommonConstants.PROTEGE)){
			return ViewConstant.TO_PROTEGE_DASHBOARD;
		}
		else if(this.getMember().getTypeId().equals(CommonConstants.MENTOR)){
			return ViewConstant.TO_MENTOR_DASHBOARD;
		}
		else if(this.getMember().getTypeId().equals(CommonConstants.ADMIN)){
			return ViewConstant.TO_ADMIN_HOME;
		}		
		return ViewConstant.BACK_TO_LOGIN;
	}
	public String backToMentorRegister(){
		return "TO_MENTOR";
	}
}
