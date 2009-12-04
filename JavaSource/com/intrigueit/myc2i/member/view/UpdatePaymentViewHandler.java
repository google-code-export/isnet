package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.payment.domain.PayPalLog;
import com.intrigueit.myc2i.payment.service.PayPalLogService;

@Component("updatePaymentViewHandler")
@Scope("session") 
public class UpdatePaymentViewHandler extends BasePage implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4343353793602551592L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( UpdatePaymentViewHandler.class );
  
  private MemberService memberService;
	private Member currentMember;
	private PayPalLogService payPalLogService;
	private ListDataModel memberLines;
	private SearchBean searchBean;
	private PayPalLog lastPayPalLog;
	private ArrayList<SelectItem> usersList;
	private ArrayList<SelectItem> paymentTermList;
	private ViewDataProvider viewDataProvider;
	private Long recordId;
	private String paymentTerms;
	private ListDataModel memberPaymentLines;
  /**
   * @return the paymentTerms
   */
  public String getPaymentTerms() {
    return paymentTerms;
  }

  /**
   * @param paymentTerms the paymentTerms to set
   */
  public void setPaymentTerms(String paymentTerms) {
    this.paymentTerms = paymentTerms;
  }

  /**
   * @return the lastPayPalLog
   */
  public PayPalLog getLastPayPalLog() {
    if ( lastPayPalLog == null ) {
      lastPayPalLog = new PayPalLog();
    }
    return lastPayPalLog;
  }

  /**
   * @param lastPayPalLog the lastPayPalLog to set
   */
  public void setLastPayPalLog(PayPalLog lastPayPalLog) {
    this.lastPayPalLog = lastPayPalLog;
  }

  @Autowired
	public UpdatePaymentViewHandler(MemberService memberService,
	    ViewDataProvider viewDataProvider,PayPalLogService payPalLogService) {
		this.memberService = memberService;
		this.viewDataProvider = viewDataProvider;
		this.payPalLogService = payPalLogService;
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
	
	public boolean validate() throws Exception {
    logger.debug(" Validating information ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if ( this.currentMember == null ) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    }
    if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
    return flag;
  }
	
	public void loadPaymentInfoByMemberId(Long memberId) {
	  try {
  	  List<PayPalLog> values = this.payPalLogService.findByProperty("memberId", new Long(memberId));
  	  System.out.println(values.size());
  	  if (values != null) {
  	    this.getMemberPaymentLines().setWrappedData(values);
  	  }
	  } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }  
	}
	
	public boolean loadLastPaymentInfoById(Long recordId) {
	  try {
      PayPalLog pLog = this.payPalLogService.getLastPayPalLogById(recordId);
      this.setLastPayPalLog(pLog);
      return true;
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return false;
    }
	}
	
	public void preUpdatePaymentInfo () {
	  setErrorMessage("");
	  logger.debug(" Prepare payment info :: "+this.getParameter(ServiceConstants.RECORD_ID));
    if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);      
      try {                
        if ( this.loadLastPaymentInfoById(Long.parseLong(recordId)) ) {
          loadPaymentInfoByMemberId(new Long(recordId));
          this.currentMember = memberService.findById(Long.parseLong(recordId));
          setSecHeaderMsg(this.getText("header_msg_update_payment"));
          setActionType(ServiceConstants.UPDATE);
          setReRenderIds("MEMBER_LINES");
          setRowIndex(getMemberLines().getRowIndex());
        } else {
          setErrorMessage(this.getText("payment_no_payment_info_msg"));
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
	}
	
	public void updatePaymentInfo () {
    logger.debug(" Update member expiry date ");
    setErrorMessage("");
    try {
      if(validate()) {
        Date memExpDate = this.currentMember.getMemberShipExpiryDate();
        Date LastPayDate = this.lastPayPalLog.getPaymentDate();
        Date nextMemberExpDate = LastPayDate;
        if ( memExpDate != null && LastPayDate.compareTo(memExpDate) < 0 ) {
          nextMemberExpDate = memExpDate;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextMemberExpDate);
        cal.add(Calendar.YEAR,1);
        this.currentMember.setMemberShipExpiryDate(cal.getTime());
        memberService.update(this.currentMember);
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
	
	
	
	/**
   * @return the memberPaymentLines
   */
  public ListDataModel getMemberPaymentLines() {
    if(memberPaymentLines == null){
      memberPaymentLines = new ListDataModel();
    }
    return memberPaymentLines;
  }
  
  /**
   * @param memberPaymentLines the memberPaymentLines to set
   */
  public void setMemberPaymentLines(ListDataModel memberPaymentLines) {
    this.memberPaymentLines = memberPaymentLines;
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

  public List<SelectItem> getStatesList() {
    return viewDataProvider.getStateList();
  }
  
  /**
   * @return the paymentTerms
   */
  public ArrayList<SelectItem> getPaymentTermList() {
    if ( paymentTermList == null ) {
      this.paymentTermList = viewDataProvider.getPaymentTerms();
    }
    return paymentTermList;
  }

  /**
   * @param paymentTerms the paymentTerms to set
   */
  public void setPaymentTermList(ArrayList<SelectItem> paymentTermList) {
    this.paymentTermList = paymentTermList;
  }
  
  
}
