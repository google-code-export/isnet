package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.memberlog.service.MemberLogService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("userManageViewHandler")
@Scope("session")
public class UserManageViewHandler extends BasePage implements Serializable {
  
  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = 3453749110036449548L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger
      .getLogger(UserManageViewHandler.class);

  private MemberService memberService;
  private MemberLogService memberLogService;
  private Member currentMember;
  private ListDataModel memberLines;
  private SearchBean searchBean;
  private ArrayList<SelectItem> usersList;
  private ViewDataProvider viewDataProvider;
  private CommonValidator commonValidator;
  private Long recordId;
  /** Available transfer methods */
  private ArrayList<SelectItem> martialStatusList;
  private ArrayList<SelectItem> birthYearlist;
  private ArrayList<SelectItem> knowledgeLevelList;
  private String confirmPass;
  private Boolean agree = false;
  private String action;
  private String userType;
  private Hashtable<String, String> memberTypeHash;
  private boolean isUserTypeReadOnly;
  /**
   * @return the isUserTypeReadOnly
   */
  public boolean getIsUserTypeReadOnly() {
    return isUserTypeReadOnly;
  }

  /**
   * @param isUserTypeReadOnly the isUserTypeReadOnly to set
   */
  public void setIsUserTypeReadOnly(boolean isUserTypeReadOnly) {
    this.isUserTypeReadOnly = isUserTypeReadOnly;
  }

  /**
   * @return the memberTypeHash
   */
  public Hashtable<String, String> getMemberTypeHash() {
    if (memberTypeHash == null) {
      this.memberTypeHash = viewDataProvider.getMemberTypeHash();
    }
    return memberTypeHash;
  }

  /**
   * @return the userType
   */
  public String getUserType() {
    return userType;
  }

  /**
   * @param userType
   *          the userType to set
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @param action
   *          the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  @Autowired
  public UserManageViewHandler(MemberService memberService,
      ViewDataProvider viewDataProvider,MemberLogService memberLogService) {
    this.memberService = memberService;
    this.viewDataProvider = viewDataProvider;
    this.memberLogService = memberLogService;
    commonValidator = new CommonValidator();
    this.initialize();
  }

  public void initialize() {
    setSecHeaderMsg("");
    loadAllMembers();
  }

  public void loadAllMembers() {
    try {
      logger.debug(" Load Members ");
      List<Member> memberList = memberService.findAll();
      getMemberLines().setWrappedData(memberList);
    } catch (Exception ex) {
      logger.error("Unable to load Members:" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void loadMembersByCriteria() {
    try {
      logger.debug(" Load Members by search critariya ");
      SearchBean value = getSearchBean();
      List<Member> memberList = memberService.findByProperties(value);
      getMemberLines().setWrappedData(memberList);
    } catch (Exception ex) {
      logger.error("Members by search critariya:" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public boolean validate() {
    logger.debug(" Validating member ");
    StringBuffer errorMessage = new StringBuffer();
    boolean flag = commonValidator.validateMember(this.currentMember,this.getUserType(),
        action, confirmPass, errorMessage);
    if (!flag)
      setErrorMessage(this.getText("common_error_header")
          + errorMessage.toString());
    return flag;
  }

  private boolean validationPhase2() {
    logger.debug(" Validating member ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if (this.action.equals(ServiceConstants.ADD)) {
      if (this.memberService.isMemberExist(this.currentMember.getEmail())) {
        if (!flag) errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
            .append(this.getText("member_validation_email_exist"));
        flag = false;
      }
    }
    if (!flag)
      setErrorMessage(this.getText("common_error_header")
          + errorMessage.toString());
    return flag;
  }

  public void setCommonData(String action) {
    setSecHeaderMsg("");
    try {
      Date dt = new Date();
      this.currentMember
          .setRecordUpdaterId("" + this.getMember().getMemberId());
      this.currentMember.setLastUpdated(dt);
      if (action.equals(ServiceConstants.ADD)) {
        if (this.getParameter(ServiceConstants.RECORD_TYPE) != null) {
          this.setUserType(this.getParameter(ServiceConstants.RECORD_TYPE)
              .toString());
          Hashtable<String, String> mType = this.getMemberTypeHash();
          if (this.userType.equals(ServiceConstants.MENTOR)) {
            this.currentMember.setTypeId(Long.parseLong(mType
                .get(ServiceConstants.MENTOR)));
            this.currentMember.setMemberRoleId(CommonConstants.ROLE_SUPERVISOR);
          } else if (this.userType.equals(ServiceConstants.PROTEGE)) {
            this.currentMember.setTypeId(Long.parseLong(mType
                .get(ServiceConstants.PROTEGE)));
            this.currentMember.setMemberRoleId(CommonConstants.ROLE_GUEST);
          } else if (this.userType.equals(ServiceConstants.ADMIN)) {
            this.currentMember.setTypeId(Long.parseLong(mType
                .get(ServiceConstants.ADMIN)));
            this.currentMember.setMemberRoleId(CommonConstants.ROLE_ADMINISTRATOR);
          }
        }
        this.currentMember.setRecordCreatorId(""
            + this.getMember().getMemberId());
        this.currentMember.setRecordCreate(dt);
      }
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message"));
      logger.error(" Unable to set common data :" + e.getMessage());
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  private String parseMemberType(String type) {
    Hashtable<String, String> mType = this.getMemberTypeHash();
    Set keySet = mType.keySet();
    Iterator it = keySet.iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      if (type.equals(mType.get(key))) {
        return key;
      }
    }
    return "";
  }

  public void preAddUser() {
    logger.debug("Preparing member");
    setErrorMessage("");
    try {
      this.currentMember = new Member();
      this.setCommonData(ServiceConstants.ADD);
      this.setConfirmPass("");
      this.isUserTypeReadOnly = true;
      setSecHeaderMsg(this.getText("header_msg_manage_user") + " "
          + this.getText("header_msg_add"));
      setActionType(ServiceConstants.ADD);
      setReRenderIds("MEMBER_LINES");      
    } catch (Exception e) {
      logger.error(e.getMessage());
      setErrorMessage(this.getText("common_system_error"));
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void addUser() {
    logger.debug("Adding member.");
    this.action = ServiceConstants.ADD;
    try {
      setErrorMessage("");
      if (validate()) {
        if (validationPhase2()) {
          CryptographicUtility crp = new CryptographicUtility();
          this.currentMember.setPassword(crp
              .getEncryptedText(this.currentMember.getPassword()));
          if (this.currentMember.getCountry()!=null && this.currentMember.getCountry().equals("-1")) {
            this.currentMember.setCountry(null);
          }
          this.memberService.save(this.currentMember);
          List<Member> itemList = (List<Member>) getMemberLines()
              .getWrappedData();
          itemList.add(this.memberService.findById(this.currentMember.getMemberId()));
          if (this.currentMember.getEmail()!=null) {
            sendNotification(this.currentMember.getEmail(),
                this.getText("admin_new_user_creation_notify_sub"),
                this.getText("admin_new_user_creation_notify_body",
                    new String[]{this.currentMember.getFirstName(),
                    this.currentMember.getEmail(),
                    this.getConfirmPass()}));
          }
          logger.debug("Member added: " + this.currentMember.getMemberId());
        }
      }
    } catch (Exception e) {
      if (this.currentMember.getMemberId() != null) {
        this.currentMember.setMemberId(null);
      }
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /** Send confirmation email to member */
  public void sendNotification(String email, String emailSubject,String msgBody)throws Exception {
    /**Send email notification */
    try {
      Emailer emailer = new Emailer(email, msgBody,emailSubject);
      emailer.setContentType("text/html");
      emailer.sendEmail();
    } catch (Exception e) {
      logger.debug("Failed to sending notification email");
      e.printStackTrace();
    }
  }
  public void preUpdateUser() {
    logger.debug(" Prepare user for update :: "
        + this.getParameter(ServiceConstants.RECORD_ID));
    if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        if (this.getParameter(ServiceConstants.RECORD_TYPE) != null) {
          String recordType = (String) this
              .getParameter(ServiceConstants.RECORD_TYPE);         
          this.setUserType(this.parseMemberType(recordType));
        }
        setErrorMessage("");
        this.currentMember = memberService.findById(Long.parseLong(recordId));
        this.isUserTypeReadOnly = false;
        this.setCommonData(ServiceConstants.UPDATE);
        setSecHeaderMsg(this.getText("header_msg_manage_user") + " "
            + this.getText("header_msg_add"));
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

  public void updateUser() {
    logger.debug(" Updating user ");
    setErrorMessage("");
    this.action = ServiceConstants.UPDATE;
    try {
      if (validate()) {
        if (validationPhase2()) {
          int rowIdx = this.getRowIndex();
          this.memberService.update(this.currentMember);          
          putObjInList(rowIdx, this.memberService.findById(this.currentMember.getMemberId()));
          logger.debug("Member updated: " + this.currentMember.getMemberId());
          setErrorMessage("");
        }
      }
    } catch (Exception e) {
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void deleteUser() {
    logger.debug("Deleting user");
    setErrorMessage("");
    if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        int rowIndex = getMemberLines().getRowIndex();
        Member member = memberService.findById(Long.parseLong(recordId));
        this.memberLogService.deleteMemLogByFrMemId(member.getMemberId());
        this.memberLogService.deleteMemLogByToMemId(member.getMemberId());
        this.memberService.delete(member);
        List<Member> itemList = (List<Member>) getMemberLines()
            .getWrappedData();
        itemList.remove(rowIndex);
        logger.debug("Member is deleted: " + recordId);
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage() + "::" + recordId);
        e.printStackTrace();
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void putObjInList(int idx, Object fetchObj) {
    try {
      List<Member> list = (List<Member>) getMemberLines().getWrappedData();
      list.remove(idx);
      list.add(idx, (Member) fetchObj);
    } catch (Exception e) {
      logger.error("Unabale to process list");
    }
  }

  /**
   * @return the currentMember
   */
  public Member getCurrentMember() {
    if (currentMember == null) {
      currentMember = new Member();
    }
    return currentMember;
  }

  /**
   * @param currentMember
   *          the currentMember to set
   */
  public void setCurrentMember(Member currentMember) {
    this.currentMember = currentMember;
  }

  /**
   * @return the memberLines
   */
  public ListDataModel getMemberLines() {
    if (memberLines == null) {
      memberLines = new ListDataModel();
    }
    return memberLines;
  }

  /**
   * @param memberLines
   *          the memberLines to set
   */
  public void setMemberLines(ListDataModel memberLines) {
    this.memberLines = memberLines;
  }

  public ArrayList<SelectItem> getUsersList() {
    if (usersList == null) {
      this.usersList = viewDataProvider.getUserTypes();
    }
    return usersList;
  }

  public void setUsersList(ArrayList<SelectItem> usersList) {
    this.usersList = usersList;
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
    if (martialStatusList == null) {
      this.martialStatusList = this.viewDataProvider.getMaritialStatusList();
    }
    return martialStatusList;
  }

  public void setMartialStatusList(ArrayList<SelectItem> martialStatusList) {
    this.martialStatusList = martialStatusList;
  }

  public ArrayList<SelectItem> getBirthYearlist() {
    if (birthYearlist == null) {
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
    if (knowledgeLevelList == null) {
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

  public SearchBean getSearchBean() {
    if (searchBean == null) {
      searchBean = new SearchBean();
    }
    return searchBean;
  }

  public void setSearchBean(SearchBean searchBean) {
    this.searchBean = searchBean;
  }

  public Boolean getAgree() {
    return agree;
  }

  public void setAgree(Boolean agree) {
    this.agree = agree;
  }

  /**
   * @return the recordId
   */
  public Long getRecordId() {
    return recordId;
  }

  /**
   * @param recordId
   *          the recordId to set
   */
  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public String getConfirmPass() {
    return confirmPass;
  }

  public void setConfirmPass(String confirmPass) {
    this.confirmPass = confirmPass;
  }
}
