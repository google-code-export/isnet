package com.intrigueit.myc2i.trainingitem.view;

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
import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;
import com.intrigueit.myc2i.trainingitem.service.TrainingItemService;
import com.intrigueit.myc2i.trainingitem.service.VendorService;

@Component("trainingItemViewHandler")
@Scope("session")
public class TrainingItemViewHandler extends BasePage implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 6912747403326406124L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(TrainingItemViewHandler.class);

	private TrainingItemService trainingItemService;
	private VendorService vendorService;
	private TrainingItem currentTrainingItem;
	List<SelectItem> categoryList;
	List<SelectItem> vendorList;
	private ListDataModel trainingItemLines;
	private String categoryName;
	private String vendorId;
	private String vendorName;
	private FileUploadBean fileUploadBean;
	private String imagePath;

	@Autowired
	public TrainingItemViewHandler(TrainingItemService trainingItemService,
			VendorService vendorService) {
		this.trainingItemService = trainingItemService;
		this.vendorService = vendorService;
		this.initializeTrainingItem();
	}

	public void initializeTrainingItem() {
		setSecHeaderMsg("");
		getTrainingItemsByCategory();
	}

	@SuppressWarnings("unchecked")
	public void loadVendors() {
		logger.debug(" Load Vendors ");
		setErrorMessage("");
		try {
			ArrayList<SelectItem> vList = new ArrayList<SelectItem>();
			vList.add(new SelectItem("", ""));
			List<ItemVendor> recordList = vendorService.loadAll();
			if (recordList != null && !recordList.isEmpty()) {
				Iterator list = recordList.iterator();
				while (list.hasNext()) {
					ItemVendor vendor = (ItemVendor) list.next();
					vList.add(new SelectItem("" + vendor.getVendorId(), vendor
							.getVendorName()));
				}
			}
			setVendorList(vList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void uploadImage() {
		try {
			if (fileUploadBean != null
					&& fileUploadBean.getUploadFile() != null) {
				UFile ufile = fileUploadBean.getUploadFile();
				this.currentTrainingItem.setItemImage(ufile.getData());
				this.currentTrainingItem.setImageFileName(ufile.getName());
				this.setImagePath(ufile.getName());
				fileUploadBean.clearUploadData();
			}
		} catch (Exception ex) {
			logger.error(" Unable to upload image :" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public boolean postValidate(TrainingItem trainingItem) {
		logger.debug("Validating Training items");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (trainingItem == null) {
			errorMessage.append(this.getText("common_system_error"));
			flag = false;
		} else {
			if (trainingItem.getVendorId() == null) {
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(this.getText("vendor_notselect"));
				flag = false;
			}

			if (trainingItemService.isCategoryExist(this.currentTrainingItem
					.getItemId(), this.currentTrainingItem.getItemEIndicator(),
					this.currentTrainingItem.getItemDescription())) {
				if (!flag)
					errorMessage.append("<br />");
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(
								this.getText("item_description_exist"));
				flag = false;
			}
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	public boolean preValidate() {
		logger.debug("Validating Training items");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (this.getVendorId() == null || this.getVendorId().equals("")) {
			errorMessage.append(this.getText("common_error_prefix"))
					.append(" ").append(this.getText("vendor_notselect"));
			flag = false;
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
		ArrayList<String> categoryList = trainingItemService.getCategories();
		try {
			if (categoryList != null && !categoryList.isEmpty()) {
				Iterator list = categoryList.iterator();
				while (list.hasNext()) {
					String key = (String) list.next();
					catList.add(new SelectItem(key, key));
				}
			}
		} catch (Exception e) {
			logger.error(" Unable to load categories :" + e.getMessage());
			e.printStackTrace();
		}
		setCategoryList(catList);
	}

	public void getTrainingItemsByCategory() {
		logger.debug(" Load Trainings items ");
		String itemEnd = "";
		String vendor = "";
		try {
			if (getCategoryName() != null && !getCategoryName().equals("")) {
				itemEnd = getCategoryName();
			}
			if (getVendorId() != null && !getVendorId().equals("")) {
				vendor = getVendorId();
			}
			List<TrainingItem> recordList = trainingItemService
					.findByProperties(vendor, itemEnd);
			getTrainingItemLines().setWrappedData(recordList);
		} catch (Exception e) {
			logger.error(" Unable to load training items by search critariya :"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void setCommonData(String action) {
		setSecHeaderMsg("");
		if (getCategoryName() != null && !getCategoryName().equals("")) {
			this.currentTrainingItem.setItemEIndicator(getCategoryName());
		}
		if (getVendorId() != null && !getVendorId().equals("")) {
			this.currentTrainingItem.setVendorId(Long.parseLong(vendorId));
		}
		try {
			Date dt = new Date();
			this.currentTrainingItem.setRecordLastUpdaterId(""
					+ this.getMember().getMemberId());
			this.currentTrainingItem.setRecordLastUpdatedDate(dt);
			this.setImagePath("");
			if (action.equals(ServiceConstants.ADD)) {
				this.currentTrainingItem.setRecordCreatorId(""
						+ this.getMember().getMemberId());
				this.currentTrainingItem.setRecordCreateDate(dt);
			}
		} catch (Exception e) {
			setSecHeaderMsg(this.getText("invalid_seesion_message"));
			logger.error(" Unable to set common data :" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void preAddTrainingItem() {
		logger.debug("Preparing training item objet");
		setErrorMessage("");
		try {
			if (preValidate()) {
				this.currentTrainingItem = new TrainingItem();
				this.setCommonData(ServiceConstants.ADD);
				setSecHeaderMsg(this.getText("header_msg_training_material")
						+ " " + this.getText("header_msg_add"));
				setActionType(ServiceConstants.ADD);
				setReRenderIds("RECORD_LINES,idTopCon");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			setErrorMessage(this.getText("common_system_error"));
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void addTrainingItem() {
		logger.debug("Prepare Training item objet for new record");
		try {
			setErrorMessage("");
			if (postValidate(this.currentTrainingItem)) {
				this.trainingItemService
						.addTrainingItem(this.currentTrainingItem);
				List<TrainingItem> itemList = (List<TrainingItem>) getTrainingItemLines()
						.getWrappedData();
				itemList.add(this.currentTrainingItem);
				this.loadCategories();
				logger.debug("Training item added: "
						+ this.currentTrainingItem.getItemId());
			}
		} catch (Exception e) {
			if (this.currentTrainingItem.getItemId() != null) {
				this.currentTrainingItem.setItemId(null);
			}
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void preUpdateTrainingItem() {
		logger.debug("Prepare training item object updating");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				setErrorMessage("");
				this.currentTrainingItem = trainingItemService.loadById(Long
						.parseLong(recordId));
				this.setCommonData(ServiceConstants.UPDATE);
				setSecHeaderMsg(this.getText("header_msg_training_material")
						+ " " + this.getText("header_msg_update"));
				setActionType(ServiceConstants.UPDATE);
				setReRenderIds("RECORD_LINES,idTopCon");
				setRowIndex(getTrainingItemLines().getRowIndex());
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void updateTrainingItem() {
		logger.debug("Updating training item object");
		try {
			setErrorMessage("");
			if (postValidate(this.currentTrainingItem)) {
				int rowIdx = getRowIndex();
				this.trainingItemService
						.updateTrainingItem(this.currentTrainingItem);
				putObjInList(rowIdx, this.currentTrainingItem);
				this.loadCategories();
				logger.debug("Training item updated: "
						+ this.currentTrainingItem.getItemId());
				setErrorMessage("");
			}
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteTrainingItem() {
		logger.debug("Deleting training item object");
		setErrorMessage("");
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				int rowIndex = getTrainingItemLines().getRowIndex();
				trainingItemService
						.deleteTrainingItem(Long.parseLong(recordId));
				List<TrainingItem> itemList = (List<TrainingItem>) getTrainingItemLines()
						.getWrappedData();
				itemList.remove(rowIndex);
				logger.debug("Training item deleted: " + recordId);
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
			List<TrainingItem> list = (List<TrainingItem>) getTrainingItemLines()
					.getWrappedData();
			list.remove(idx);
			list.add(idx, (TrainingItem) fetchObj);
		} catch (Exception e) {
			logger.error("Unabale to processl list");
		}
	}

	/**
	 * @return the currentTrainingItem
	 */
	public TrainingItem getCurrentTrainingItem() {
		if (currentTrainingItem == null) {
			currentTrainingItem = new TrainingItem();
		}
		return currentTrainingItem;
	}

	/**
	 * @param currentTrainingItem
	 *            the currentTrainingItem to set
	 */
	public void setCurrentTrainingItem(TrainingItem currentTrainingItem) {
		this.currentTrainingItem = currentTrainingItem;
	}

	public List<SelectItem> getVendorList() {
		loadVendors();
		return vendorList;
	}

	public void setVendorList(List<SelectItem> vendorList) {
		this.vendorList = vendorList;
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
	 * @return the trainingItemLines
	 */
	public ListDataModel getTrainingItemLines() {
		if (trainingItemLines == null) {
			trainingItemLines = new ListDataModel();
		}
		return trainingItemLines;
	}

	/**
	 * @param trainingItemLines
	 *            the trainingItemLines to set
	 */
	public void setTrainingItem(ListDataModel trainingItemLines) {
		this.trainingItemLines = trainingItemLines;
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @param fileUploadBean
	 *            the fileUploadBean to set
	 */
	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

}
