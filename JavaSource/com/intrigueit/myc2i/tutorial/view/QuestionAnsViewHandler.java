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
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;
import com.intrigueit.myc2i.tutorial.service.ModulesService;
import com.intrigueit.myc2i.tutorial.service.QuestionAnsService;

@Component("questionAnsViewHandler")
@Scope("session")
public class QuestionAnsViewHandler extends BasePage implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -1783081168429167496L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(QuestionAnsViewHandler.class);

	private QuestionAnsService questionAnsService;
	private ModulesService modulesService;
	private TestTutorialQuestionAns currentQuestionAns;
	List<SelectItem> categoryList;
	List<SelectItem> modulesList;
	private ListDataModel questionAnsLines;
	private String modulesId;
	private String modulesName;
	private FileUploadBean fileUploadBean;

	@Autowired
	public QuestionAnsViewHandler(QuestionAnsService questionAnsService,
			ModulesService modulesService) {
		this.questionAnsService = questionAnsService;
		this.modulesService = modulesService;
		this.initializeQuestionAns();
	}

	public void initializeQuestionAns() {
		setSecHeaderMsg("");
		getQuestionAnsByCategory();
	}

	public void getQuestionAnsByCategory() {
		logger.debug(" Load Question and answer items ");
		String moduleId = "";
		try {
			if (getModulesId() != null && !getModulesId().isEmpty()) {
				moduleId = getModulesId();
			}
			List<TestTutorialQuestionAns> recordList = questionAnsService
					.findByProperties(moduleId);
			getQuestionAnsLines().setWrappedData(recordList);
		} catch (Exception e) {
			logger.error("Unable to load question and answer: "
					+ e.getMessage());
		}
	}

	public void uploadAudio() {
		try {
			if (fileUploadBean != null
					&& fileUploadBean.getUploadFile() != null) {
				UFile ufile = fileUploadBean.getUploadFile();
				this.currentQuestionAns.setPageAudio(ufile.getData());
				this.currentQuestionAns.setAudioFileName(ufile.getName());
				logger.debug("Uploaded audio::" + ufile.getName());
				fileUploadBean.clearUploadData();
			}
		} catch (Exception ex) {
			logger.error("Unable to load audio");
			ex.printStackTrace();
		}
	}

	public void uploadVideo() {
		try {
			if (fileUploadBean != null
					&& fileUploadBean.getUploadFile() != null) {
				UFile ufile = fileUploadBean.getUploadFile();
				this.currentQuestionAns.setPageVideo(ufile.getData());
				this.currentQuestionAns.setVideoFileName(ufile.getName());
				logger.debug("Uploaded video::" + ufile.getName());
				fileUploadBean.clearUploadData();
			}
		} catch (Exception ex) {
			logger.error("Unable to load video");
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadModuless() {
		logger.debug(" Load Moduless ");
		setErrorMessage("");
		try {
			ArrayList<SelectItem> vList = new ArrayList<SelectItem>();
			vList.add(new SelectItem("", ""));
			List<TestTutorialModules> recordList = modulesService
					.findByProperties("", "");
			if (recordList != null && !recordList.isEmpty()) {
				Iterator list = recordList.iterator();
				while (list.hasNext()) {
					TestTutorialModules modules = (TestTutorialModules) list
							.next();
					vList.add(new SelectItem("" + modules.getModulesId(),
							modules.getModuleName()));
				}
			}
			setModulesList(vList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean preValidate() {
		logger.debug("Validating question and answer");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		System.out.println(this.getModulesId());
		if (this.getModulesId() == null || this.getModulesId().isEmpty()) {
			errorMessage.append(this.getText("common_error_prefix"))
					.append(" ").append(
							this.getText("error_msg_modules_notselect"));
			flag = false;
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	public boolean postValidate(TestTutorialQuestionAns questionAns) {
		logger.debug("Validating question and answer");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (questionAns == null) {
			errorMessage.append(this.getText("common_system_error"));
			return false;
		} else {
			if (questionAns.getModulesId() == null) {
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(
								this.getText("error_msg_modules_notselect"));
			}
			if (flag) {
				if (questionAnsService.isQuestionExist(this.currentQuestionAns
						.getQuestionAnsId(), questionAns.getModulesId(),
						this.currentQuestionAns.getQuestion())) {
					errorMessage.append(this.getText("common_error_prefix"))
							.append(" ").append(this.getText("question_exist"));
					flag = false;
				}
				if (questionAnsService.isPageNoExist(this.currentQuestionAns
						.getQuestionAnsId(), questionAns.getModulesId(),
						this.currentQuestionAns.getPageNumber())) {
					errorMessage.append(this.getText("common_error_prefix"))
							.append(" ").append(this.getText("page_no_exist"));
					flag = false;
				}
				/*
				 * if (this.currentQuestionAns.getPageText() == null ||
				 * this.currentQuestionAns.getPageText().isEmpty()){
				 * errorMessage
				 * .append(this.getText("common_error_prefix")).append(" ")
				 * .append(this.getText("page_content_cant_empty")); flag =
				 * false; }
				 */
			}
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	@SuppressWarnings("unchecked")
	public void loadCategories() {
		ArrayList<SelectItem> catList = new ArrayList<SelectItem>();
		catList.add(new SelectItem("", ""));
		try {
			ArrayList<String> categoryList = questionAnsService.getCategories();
			if (categoryList != null && !categoryList.isEmpty()) {
				Iterator list = categoryList.iterator();
				while (list.hasNext()) {
					String key = (String) list.next();
					catList.add(new SelectItem(key, key));
				}
			}
		} catch (Exception e) {
			logger.error("Unable to load categories:" + e.getMessage());
		}
		setCategoryList(catList);
	}

	public void setCommonData(String action) {
		setSecHeaderMsg("");
		if (getModulesId() != null && !getModulesId().isEmpty()) {
			this.currentQuestionAns.setModulesId(Long.parseLong(modulesId));
		}
		try {
			Date dt = new Date();
			this.currentQuestionAns.setRecordLastUpdaterId(""
					+ this.getMember().getMemberId());
			this.currentQuestionAns.setRecordLastUpdatedDate(dt);
			if (action.equals(ServiceConstants.ADD)) {
				this.currentQuestionAns.setRecordCreatorId(""
						+ this.getMember().getMemberId());
				this.currentQuestionAns.setRecordCreateDate(dt);
			}
		} catch (Exception e) {
			setSecHeaderMsg(this.getText("invalid_seesion_message"));
			logger.error(" Unable to set common data :" + e.getMessage());
			e.printStackTrace();
		}
	}

	public Long getMaxPageNo() {
		Long maxPageNo = new Long(0);
		try {
			if (getModulesId() != null && !getModulesId().isEmpty()) {
				maxPageNo = this.questionAnsService.getMaxPageNo(Long
						.parseLong(getModulesId()));
				maxPageNo = (maxPageNo == null) ? 1 : maxPageNo + 1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return maxPageNo;
	}

	public void preAddQuestionAns() {
		logger.debug("Preparing question and answer");
		setErrorMessage("");
		try {
			setErrorMessage("");
			if (preValidate()) {
				this.currentQuestionAns = new TestTutorialQuestionAns();
				this.setCommonData(ServiceConstants.ADD);
				setSecHeaderMsg(this.getText("header_msg_tutorial_qa") + " "
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
	public void addQuestionAns() {
		logger.debug("Prepare question and answer for new record");
		try {
			setErrorMessage("");
			this.currentQuestionAns = getCurrentQuestionAns();
			this.currentQuestionAns.setPageNumber(this.getMaxPageNo());
			if (postValidate(this.currentQuestionAns)) {
				questionAnsService.addQuestionAns(this.currentQuestionAns);
				List<TestTutorialQuestionAns> itemList = (List<TestTutorialQuestionAns>) getQuestionAnsLines()
						.getWrappedData();
				itemList.add(this.currentQuestionAns);
			}
		} catch (Exception e) {
			if (this.currentQuestionAns.getQuestionAnsId() != null) {
				this.currentQuestionAns.setQuestionAnsId(null);
			}
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void preUpdateQuestionAns() {
		logger.debug("Prepare question and answer updating");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				setErrorMessage("");
				this.currentQuestionAns = questionAnsService.loadById(Long
						.parseLong(recordId));
				this.setCommonData(ServiceConstants.UPDATE);
				setSecHeaderMsg(this.getText("header_msg_tutorial_qa") + " "
						+ this.getText("header_msg_update"));
				setActionType(ServiceConstants.UPDATE);
				setReRenderIds("RECORD_LINES");
				setRowIndex(getQuestionAnsLines().getRowIndex());
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void updateQuestionAns() {
		logger.debug("Updating question and answer");
		try {
			setErrorMessage("");
			TestTutorialQuestionAns questionAns = getCurrentQuestionAns();
			if (postValidate(questionAns)) {
				int rowIdx = getRowIndex();
				questionAnsService.updateQuestionAns(questionAns);
				putObjInList(rowIdx, questionAns);
				setErrorMessage("");
			}
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteQuestionAns() {
		logger.debug("Deleting question and answer");
		setErrorMessage("");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				int rowIndex = getQuestionAnsLines().getRowIndex();
				questionAnsService.deleteQuestionAns(Long.parseLong(recordId));
				List<TestTutorialQuestionAns> itemList = (List<TestTutorialQuestionAns>) getQuestionAnsLines()
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
			List<TestTutorialQuestionAns> list = (List<TestTutorialQuestionAns>) getQuestionAnsLines()
					.getWrappedData();
			list.remove(idx);
			list.add(idx, (TestTutorialQuestionAns) fetchObj);
		} catch (Exception e) {
			logger.error("Unable to process list:" + e.getMessage());
		}
	}

	public void uploadRichText() {
		//System.out.println(this.currentQuestionAns.getPageText());
	}

	/**
	 * @return the currentQuestionAns
	 */
	public TestTutorialQuestionAns getCurrentQuestionAns() {
		if (currentQuestionAns == null) {
			currentQuestionAns = new TestTutorialQuestionAns();
		}
		return currentQuestionAns;
	}

	/**
	 * @param currentQuestionAns
	 *            the currentQuestionAns to set
	 */
	public void setCurrentQuestionAns(TestTutorialQuestionAns currentQuestionAns) {
		this.currentQuestionAns = currentQuestionAns;
	}

	public List<SelectItem> getModulesList() {
		loadModuless();
		return modulesList;
	}

	public void setModulesList(List<SelectItem> modulesList) {
		this.modulesList = modulesList;
	}

	/**
	 * @return the categoryList
	 */
	public List<SelectItem> getCategoryList() {
		if (categoryList == null) {
			loadCategories();
		}
		return categoryList;
	}

	/**
	 * @param categoryList
	 *            the categoryList to set
	 */
	public void setCategoryList(List<SelectItem> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the questionAnsLines
	 */
	public ListDataModel getQuestionAnsLines() {
		if (questionAnsLines == null) {
			questionAnsLines = new ListDataModel();
		}
		return questionAnsLines;
	}

	/**
	 * @param questionAnsLines
	 *            the questionAnsLines to set
	 */
	public void setQuestionAnsLines(ListDataModel questionAnsLines) {
		this.questionAnsLines = questionAnsLines;
	}

	public String getModulesName() {
		return modulesName;
	}

	public void setModulesName(String modulesName) {
		this.modulesName = modulesName;
	}

	public String getModulesId() {
		return modulesId;
	}

	public void setModulesId(String modulesId) {
		this.modulesId = modulesId;
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
	 *            the fileUploadBean to set
	 */
	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

}
