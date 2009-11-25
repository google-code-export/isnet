package com.intrigueit.myc2i.udvalues.view;

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
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

@Component("udvViewHandler")
@Scope("session")
public class UDValuesViewHandler extends BasePage implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -8378735635815072749L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(UDValuesViewHandler.class);

	private UDValuesService udvService;
	private UserDefinedValues currentUDValues;
	List<SelectItem> categoryList;
	private ListDataModel udValuesLines;
	private String categoryName;

	@Autowired
	public UDValuesViewHandler(UDValuesService udvService) {
		this.udvService = udvService;
		this.initializeUDValues();
	}

	public void initializeUDValues() {
		setSecHeaderMsg("");
		getUDValuesByCategory();
	}

	public boolean validate() {
		logger.debug(" Validating user define values ");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (this.currentUDValues == null) {
			errorMessage.append(this.getText("common_system_error"));
			return false;
		} else {
			if (udvService.isUDValueExist(this.currentUDValues.getUdValuesId(),
					this.currentUDValues.getUdValuesCategory(),
					this.currentUDValues.getUdValuesValue())) {
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(
								this.getText("user_define_values_code_exist"));
				flag = false;
			}
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	public void getUDValuesByCategory() {
		Object value = "%";
		logger.debug(" Load User define values ");
		try {
			if (getCategoryName() != null && !getCategoryName().isEmpty()) {
				value = getCategoryName();
			}
			List<UserDefinedValues> udValuesList = udvService.findByProperty(
					"udValuesCategory", value);
			getUdValuesLines().setWrappedData(udValuesList);
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadCategories() {
		ArrayList<SelectItem> catList = new ArrayList<SelectItem>();
		catList.add(new SelectItem("", ""));
		try {
			ArrayList<String> categoryList = udvService.getCategories();
			if (categoryList != null && !categoryList.isEmpty()) {
				Iterator list = categoryList.iterator();
				while (list.hasNext()) {
					String key = (String) list.next();
					catList.add(new SelectItem(key, key));
				}
			}
		} catch (Exception e) {
			logger.error("Unable to load categories:" + e.getMessage());
			e.printStackTrace();
		}
		setCategoryList(catList);
	}

	public void setCommonData(String action) {
		setSecHeaderMsg("");
		if (getCategoryName() != null && !getCategoryName().isEmpty()) {
			this.currentUDValues.setUdValuesCategory(getCategoryName());
		}
		try {
			Date dt = new Date();
			this.currentUDValues.setRecordLastUpdaterId(""
					+ this.getMember().getMemberId());
			this.currentUDValues.setRecordLastUpdatedDate(dt);
			if (action.equals(ServiceConstants.ADD)) {
				this.currentUDValues.setRecordCreatorId(""
						+ this.getMember().getMemberId());
				this.currentUDValues.setRecordCreatedDate(dt);
			}
		} catch (Exception e) {
			setSecHeaderMsg(this.getText("invalid_seesion_message"));
			logger.error(" Unable to set common data :" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void preAddUDValues() {
		logger.debug(" Preparing user define values for adding new record ");
		setErrorMessage("");
		try {
			this.currentUDValues = new UserDefinedValues();
			this.setCommonData(ServiceConstants.ADD);
			setSecHeaderMsg(this.getText("header_msg_user_define_value") + " "
					+ this.getText("header_msg_add"));
			setActionType(ServiceConstants.ADD);
			setReRenderIds("CATEGORY_LINES,idTopCon");
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void addUDValues() {
		logger.debug(" Adding new user define values into database ");
		setErrorMessage("");
		try {
			if (validate()) {
				this.udvService.addUDValues(this.currentUDValues);
				this.loadCategories();
				List<UserDefinedValues> udvList = (List<UserDefinedValues>) getUdValuesLines()
						.getWrappedData();
				udvList.add(this.currentUDValues);
				logger.debug("User Define value added: "
						+ this.currentUDValues.getUdValuesId());
			}
		} catch (Exception e) {
		  if (this.currentUDValues.getUdValuesId() != null) {
        this.currentUDValues.setUdValuesId(null);
      }
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void preUpdateUDValues() {
		setErrorMessage("");
		// logger.debug(" Preparing user define values for updating ");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				this.currentUDValues = udvService.loadById(Long
						.parseLong(recordId));
				this.setCommonData(ServiceConstants.UPDATE);
				setSecHeaderMsg(this.getText("header_msg_user_define_value")
						+ " " + this.getText("header_msg_update"));
				this.setActionType(ServiceConstants.UPDATE);
				this.setReRenderIds("CATEGORY_LINES,idTopCon");
				this.setRowIndex(getUdValuesLines().getRowIndex());
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void updateUDValues() {
		logger.debug(" Updating user define values ");
		setErrorMessage("");
		try {
			if (validate()) {
				this.setCommonData(ServiceConstants.UPDATE);
				int rowIdx = getRowIndex();
				this.udvService.updateUDValues(this.currentUDValues);
				loadCategories();
				putObjInList(rowIdx, this.currentUDValues);
				logger.debug("User Define value updated: "
						+ this.currentUDValues.getUdValuesId());
			}
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteUDValues() {
		logger.debug(" Deleting user define values from database ");
		setErrorMessage("");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				int rowIndex = getUdValuesLines().getRowIndex();
				this.udvService.deleteUDValues(Long.parseLong(recordId));
				List<UserDefinedValues> udvList = (List<UserDefinedValues>) getUdValuesLines()
						.getWrappedData();
				udvList.remove(rowIndex);
				logger.debug("User Define value deleted: "
						+ this.currentUDValues.getUdValuesId());
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
			List<UserDefinedValues> list = (List<UserDefinedValues>) getUdValuesLines()
					.getWrappedData();
			list.remove(idx);
			list.add(idx, (UserDefinedValues) fetchObj);
		} catch (Exception e) {
			logger.error("Unable to process list");
		}
	}

	/**
	 * @return the currentUDValues
	 */
	public UserDefinedValues getCurrentUDValues() {
		if (currentUDValues == null) {
			currentUDValues = new UserDefinedValues();
		}
		return currentUDValues;
	}

	/**
	 * @param currentUDValues
	 *            the currentUDValues to set
	 */
	public void setCurrentUDValues(UserDefinedValues currentUDValues) {
		this.currentUDValues = currentUDValues;
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
	 * @return the categoryId
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the udValuesLines
	 */
	public ListDataModel getUdValuesLines() {
		if (udValuesLines == null) {
			udValuesLines = new ListDataModel();
		}
		return udValuesLines;
	}

	/**
	 * @param udValuesLines
	 *            the udValuesLines to set
	 */
	public void setUdValuesLines(ListDataModel udValuesLines) {
		this.udValuesLines = udValuesLines;
	}
}
