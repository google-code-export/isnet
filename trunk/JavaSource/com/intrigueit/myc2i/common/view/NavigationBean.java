package com.intrigueit.myc2i.common.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("navigationBean")
@Scope("session")
public class NavigationBean extends BasePage{

	public String backToLogin(){
		return ViewConstant.BACK_TO_LOGIN;
	}
}
