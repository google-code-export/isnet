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
import com.intrigueit.myc2i.member.service.MemberExService;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("resetPasswordViewHandler")
@Scope("session") 
public class ResetPasswordViewHandler extends BasePage implements Serializable {	
  private static final long serialVersionUID = 2098951095935218884L;
  
  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( ResetPasswordViewHandler.class );
  
  private MemberExService memberExService;
  private MemberService memberService;
	private Member currentMember;
	private ListDataModel memberLines;
	private SearchBean searchBean;
	private ArrayList<SelectItem> usersList;
	private ViewDataProvider viewDataProvider;
	private Long recordId;
  
}
