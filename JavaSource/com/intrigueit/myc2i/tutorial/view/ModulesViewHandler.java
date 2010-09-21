package com.intrigueit.myc2i.tutorial.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.utility.FileUploadBean;
import com.intrigueit.myc2i.common.utility.UFile;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;
import com.intrigueit.myc2i.tutorial.service.DocumentService;
import com.intrigueit.myc2i.tutorial.service.ModulesService;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;
import com.intrigueit.myc2i.tutorialtest.service.TestResultService;


@Component("modulesViewHandler")
@Scope("session")
public class ModulesViewHandler extends BasePage implements Serializable {
  
  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -3806943561966771817L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger
      .getLogger(ModulesViewHandler.class);

  private ModulesService modulesService;
  private DocumentService documentService;
  private TestTutorialModules currentModules;
  private ArrayList<SelectItem> usersList;
  private List<SelectItem> documentList;
  private ListDataModel modulesLines;
  private String userType;
  private String documentId;
  private String documentName;
  private FileUploadBean fileUploadBean;
  private ViewDataProvider viewDataProvider;

  private TestResultService testResltService;
  
  private List<TestTutorialModules> modules;

   public List<TestTutorialModules> getModules() {
		try {
			
			List<TestResult> results = this.loadUserTestModules();
			
			if (modules == null) {
				modules = modulesService.findModulesByUserType(this.getMember().getTypeId());
			}
			this.modules.get(0).setTestStatus(true);
			for(int index= 1;index < modules.size() ;index++){
				if(isModulePassed(results, modules.get(index-1))){
					modules.get(index).setTestStatus(true);
				}
				else{
					modules.get(index).setTestStatus(false);
				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return modules;
	}
   
    private Boolean isModulePassed(List<TestResult> results,TestTutorialModules module ){
    	/** If test result empty then no tutorial is completed. */
    	if(results == null || results.size()== 0){
    		return false;
    	}
    	/** If module name found in result set then this module is passed */
    	for(TestResult res: results){
    		if(res.getModuleId().equals(module.getModulesId()) && res.getIsPassed()){
    			return true;
    		}
    	}
    	/** Otherwise no modules completed. */
    	return false;
    }
	private List<TestResult> loadUserTestModules(){
		List<TestResult> results = null;
		try{
			results = testResltService.findUserTestResult( this.getMember().getMemberId());
			//results = testResltService.findAll();
			
		}
		catch(Exception ex){
			log.error(" Error during loading user test modules: "+ ex.getMessage());
		}
		return results;
	}

	public void setModules(List<TestTutorialModules> modules) {
		this.modules = modules;
	}
	
  @Autowired
  public ModulesViewHandler(ModulesService modulesService,
      DocumentService documentService) {
    this.modulesService = modulesService;
    this.documentService = documentService;
    this.initializeTestTutorialModules();
  }

  @Autowired
  public void setViewDataProvider(ViewDataProvider viewDataProvider) {
    this.viewDataProvider = viewDataProvider;
  }

  public void initializeTestTutorialModules() {
    setSecHeaderMsg("");
    this.getModulesByCritariya();
  }

  @SuppressWarnings("unchecked")
  public void loadDocuments() {
    logger.debug(" Load Documents ");
    setErrorMessage("");
    try {
      ArrayList<SelectItem> vList = new ArrayList<SelectItem>();
      vList.add(new SelectItem("", ""));
      List<TestTutorialDocument> recordList = documentService
          .loadDispDocuments();
      if (recordList != null && !recordList.isEmpty()) {
        Iterator iterator = recordList.iterator();
        while (iterator.hasNext()) {
          TestTutorialDocument object = (TestTutorialDocument) iterator.next();
          if (object != null) {
            vList.add(new SelectItem("" + object.getDocumentId(), ""
                + object.getDocumentName()));
          }
        }
      }
      setDocumentList(vList);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean preValidate() {
    logger.debug("Validating tutorial modules");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if (this.getDocumentId() == null || this.getDocumentId().isEmpty()) {
      errorMessage.append(this.getText("common_error_prefix")).append(" ")
          .append(this.getText("tutorial_module_doc_notselect"));
      flag = false;
    }
    if (this.getUserType() == null || this.getUserType().isEmpty()) {
      if (!flag)
        errorMessage.append("<br />");
      errorMessage.append(this.getText("common_error_prefix")).append(" ")
          .append(this.getText("tutorial_module_user_notselect"));
      flag = false;
    }
    if (!flag)
      setErrorMessage(this.getText("common_error_header")
          + errorMessage.toString());
    return flag;
  }

  public boolean postValidate(TestTutorialModules document) {
    logger.debug("Validating tutorial module");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if (document == null) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    } else {
      if (document.getDocumentId() == null) {
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
            .append(this.getText("tutorial_module_doc_notselect"));
        flag = false;
      }
      if (document.getMemberTypeIndicator() == null) {
        if (!flag)
          errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
            .append(this.getText("tutorial_module_user_notselect"));
        flag = false;
      }

      if (flag) {
        if (modulesService.isModuleExist(this.currentModules.getModulesId(),
            document.getDocumentId(), this.currentModules.getModuleName())) {
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
              .append(this.getText("module_name_exist"));
          flag = false;
        }
        if (this.currentModules.getModuleText() == null
            || this.currentModules.getModuleText().isEmpty()) {
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
              .append(this.getText("module_content_cant_empty"));
          flag = false;
        }
      }
    }
    if (!flag)
      setErrorMessage(this.getText("common_error_header")
          + errorMessage.toString());
    return flag;
  }

  public void getModulesByCritariya() {
    logger.debug(" Load tutorial module ");
    String docTypeId = "";
    String userId = "";
    try {
      if (getDocumentId() != null && !getDocumentId().isEmpty()) {
        docTypeId = getDocumentId();
      }
      if (getUserType() != null && !getUserType().isEmpty()) {
        userId = getUserType();
      }
      List<TestTutorialModules> recordList = modulesService.findByProperties(
          docTypeId, userId);
      getModulesLines().setWrappedData(recordList);
    } catch (Exception e) {
      logger.error("Unable to load modules :" + e.getMessage());
    }
  }

  public void setCommonData(String action) {
    setSecHeaderMsg("");
    if (getDocumentId() != null && !getDocumentId().isEmpty()) {
      this.currentModules.setDocumentId(Long.parseLong(getDocumentId()));
    }
    if (getUserType() != null && !getUserType().isEmpty()) {
      this.currentModules.setMemberTypeIndicator(Long.parseLong(getUserType()));
    }
    try {
      Date dt = new Date();
      this.currentModules.setRecordLastUpdaterId(""
          + this.getMember().getMemberId());
      this.currentModules.setRecordLastUpdatedDate(dt);
      if (action.equals(ServiceConstants.ADD)) {
        this.currentModules.setRecordCreatorId(""
            + this.getMember().getMemberId());
        this.currentModules.setRecordCreateDate(dt);
      }
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message"));
      logger.error(" Unable to set common data :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void preAddModules() {
    logger.debug("Preparing tutorial module");
    setErrorMessage("");
    try {
      if (preValidate()) {
        this.currentModules = new TestTutorialModules();
        this.setCommonData(ServiceConstants.ADD);
        setSecHeaderMsg(this.getText("header_msg_tutorial_modules") + " "
            + this.getText("header_msg_add"));
        setActionType(ServiceConstants.ADD);
        setReRenderIds("RECORD_LINES");
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      setErrorMessage(this.getText("common_system_error"));
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void addModules() {
    logger.debug("Prepare tutorial module for new record");
    try {
      setErrorMessage("");
      if (postValidate(this.currentModules)) {
        this.modulesService.addModules(this.currentModules);
        List<TestTutorialModules> itemList = (List<TestTutorialModules>) getModulesLines()
            .getWrappedData();
        itemList.add(this.currentModules);
      }
    } catch (Exception e) {
      if (this.currentModules.getModulesId() != null) {
        this.currentModules.setModulesId(null);
      }
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  public void preUpdateModules() {
    logger.debug("Prepare tutorial module for updating");
    setErrorMessage("");
    if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        this.currentModules = modulesService.loadById(Long.parseLong(recordId));
        this.setCommonData(ServiceConstants.UPDATE);
        setSecHeaderMsg(this.getText("header_msg_tutorial_modules") + " "
            + this.getText("header_msg_update"));
        setActionType(ServiceConstants.UPDATE);
        setReRenderIds("RECORD_LINES");
        setRowIndex(getModulesLines().getRowIndex());
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void updateModules() {
    logger.debug("Updating tutorial module");
    try {
      setErrorMessage("");
      this.currentModules = getCurrentModules();
      if (postValidate(this.currentModules)) {
        int rowIdx = getRowIndex();
        Date dt = new Date();
        this.currentModules.setRecordLastUpdatedDate(dt);
        this.modulesService.updateModules(this.currentModules);
        putObjInList(rowIdx, this.currentModules);
        setErrorMessage("");
      }
    } catch (Exception e) {
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void deleteModules() {
    setErrorMessage("");
    logger.debug("Deleting tutorial module");
    setErrorMessage("");
    if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        int rowIndex = getModulesLines().getRowIndex();
        modulesService.deleteModules(Long.parseLong(recordId));
        List<TestTutorialModules> itemList = (List<TestTutorialModules>) getModulesLines()
            .getWrappedData();
        itemList.remove(rowIndex);
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void putObjInList(int idx, Object fetchObj) {
    try {
      List<TestTutorialModules> list = (List<TestTutorialModules>) getModulesLines()
          .getWrappedData();
      list.remove(idx);
      list.add(idx, (TestTutorialModules) fetchObj);
    } catch (Exception e) {
      logger.error("Unable to preocess list");
    }
  }

  public void uploadRichText() {
    System.out.println(this.currentModules.getModuleText());
  }

  public void uploadAudio() {
    try {
      if (fileUploadBean != null && fileUploadBean.getUploadFile() != null) {
        UFile ufile = fileUploadBean.getUploadFile();
        this.currentModules.setModuleIntroAudio(ufile.getData());
        this.currentModules.setAudioFileName(ufile.getName());
        logger.debug("Uploaded audio::"+ufile.getName());
        fileUploadBean.clearUploadData();
      }
    } catch (Exception ex) {
      logger.error("Unable to load audio:" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void uploadVideo() {
    try {
      if (fileUploadBean != null && fileUploadBean.getUploadFile() != null) {
        UFile ufile = fileUploadBean.getUploadFile();
        this.currentModules.setModuleIntroVideo(ufile.getData());
        this.currentModules.setVideoFileName(ufile.getName());
        logger.debug("Uploaded vedio::"+ufile.getName());
        fileUploadBean.clearUploadData();
      }
    } catch (Exception ex) {
      logger.error("Unable to load video:" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  /**
   * @return the currentModules
   */
  public TestTutorialModules getCurrentModules() {
    if (currentModules == null) {
      currentModules = new TestTutorialModules();
    }
    return currentModules;
  }

  /**
   * @param currentModules
   *          the currentModules to set
   */
  public void setCurrentModules(TestTutorialModules currentModules) {
    this.currentModules = currentModules;
  }

  public List<SelectItem> getDocumentList() {
    loadDocuments();
    return documentList;
  }

  public void setDocumentList(List<SelectItem> documentList) {
    this.documentList = documentList;
  }

  public ArrayList<SelectItem> getUsersList() {
    if (usersList == null) {
      this.usersList = viewDataProvider.getModuleUsers();
    }
    return usersList;
  }

  public void setUsersList(ArrayList<SelectItem> usersList) {
    this.usersList = usersList;
  }

  /**
   * @return the categoryId
   */
  public String getUserType() {
    return userType;
  }

  /**
   * @param categoryId
   *          the categoryId to set
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * @return the modulesLines
   */
  public ListDataModel getModulesLines() {
    if (modulesLines == null) {
      modulesLines = new ListDataModel();
    }
    return modulesLines;
  }

  /**
   * @param modulesLines
   *          the modulesLines to set
   */
  public void setModulesLines(ListDataModel modulesLines) {
    this.modulesLines = modulesLines;
  }

  public String getDocumentName() {
    return documentName;
  }

  public void setDocumentName(String documentName) {
    this.documentName = documentName;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  /**
   * @return the fileUploadBean
   */
  public FileUploadBean getFileUploadBean() {
    if (fileUploadBean == null)
      fileUploadBean = new FileUploadBean();
    return fileUploadBean;
  }

  /**
   * @param fileUploadBean
   *          the fileUploadBean to set
   */
  public void setFileUploadBean(FileUploadBean fileUploadBean) {
    this.fileUploadBean = fileUploadBean;
  }

  @Autowired
  public void setTestResltService(TestResultService testResltService) {
	this.testResltService = testResltService;
  }





}
