package com.intrigueit.myc2i.trainingitem.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;
import com.intrigueit.myc2i.trainingitem.service.VendorService;

@Component("vendorViewHandler")
@Scope("session")
public class VendorViewHandler extends BasePage implements Serializable {
		
	/**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -4258841378553095560L;

  /** Initialize the Logger */  
	protected static final Logger logger = Logger.getLogger( VendorViewHandler.class );
	
	private VendorService vendorService;
	private ItemVendor currentVendor;
	private ListDataModel vendorLines;
	
  
  @Autowired
	public VendorViewHandler(VendorService vendorService) {
		this.vendorService = vendorService;
		this.initializeVendor();		
	}

	public void initializeVendor(){		
	  setSecHeaderMsg("");
	  loadVendors();
	}
	
	public boolean validate () {
	  logger.debug("Validating Vendor");
	  boolean flag = true;
	  StringBuffer errorMessage = new StringBuffer();
	  if ( this.currentVendor == null ) {
	    errorMessage.append(this.getText("common_system_error"));
	    return false;
		} else {
		  if (vendorService.isRecordExist(this.currentVendor.getVendorId(),
          this.currentVendor.getVendorName())){
		    errorMessage.append(this.getText("common_error_prefix")).append(" ")
                  .append(this.getText("training_materials_vendor_exist"));
		    flag = false;       
		  }
		}
	  if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
	  return flag;
	}
	
	public void loadVendors() {
	  logger.debug(" Load Vendors ");
	  try {
	    List<ItemVendor> recordList = vendorService.loadAll();
	    getVendorLines().setWrappedData(recordList);
	  } catch (Exception e) {
      logger.error(" Unable to load vendor :"+e.getMessage());
      e.printStackTrace();
    }
	}
	
	public void setCommonData ( String action ) {
	  setErrorMessage("");
	  try {
  	  Date dt = new Date();           
      this.currentVendor.setRecordLastUpdaterId(""+this.getMember().getMemberId());    
      this.currentVendor.setRecordLastUpdatedDate(dt);
      if (action.equals(ServiceConstants.ADD )) {
        this.currentVendor.setRecordCreatorId(""+this.getMember().getMemberId());
        this.currentVendor.setRecordCreateDate(dt);
      }
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message"));
      logger.error(" Unable to set common data :"+e.getMessage());
      e.printStackTrace();
    }
  }
	
	public void preAddVendor () {	  
	  logger.debug("Preparing new vendor");
	  setErrorMessage("");
	  try {
	    this.currentVendor = new ItemVendor();
	    this.setCommonData(ServiceConstants.ADD);
	    setSecHeaderMsg(this.getText("header_msg_training_item_vendor") + " " + this.getText("header_msg_add"));
			setActionType(ServiceConstants.ADD);
			setReRenderIds("VENDOR_LINES");
		} catch (Exception e) {			
		  logger.error(e.getMessage());
		  setErrorMessage(this.getText("common_system_error"));
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addVendor () {
	  logger.debug("Adding new vendor");
	  try {		  
		  setErrorMessage("");
		  this.currentVendor = getCurrentVendor();
		  if(validate()) {
  			this.vendorService.addVendor(this.currentVendor);
  			List<ItemVendor> udvList = (List<ItemVendor>) getVendorLines().getWrappedData();
  			udvList.add(this.currentVendor);
			}
		} catch (Exception e) {
		  if (this.currentVendor.getVendorId() != null) {
        this.currentVendor.setVendorId(null);
      }
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
	}	            
	public void preUpdateVendor() {
	  logger.debug("Prepare vendor for updating");
	  if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {
  		  setErrorMessage("");  	    
  		  this.currentVendor = vendorService.loadById(Long.parseLong(recordId));
  		  this.setCommonData(ServiceConstants.ADD);
  		  setSecHeaderMsg(this.getText("header_msg_training_item_vendor") + " " + this.getText("header_msg_update"));
				setActionType(ServiceConstants.UPDATE);
				setReRenderIds("VENDOR_LINES");
				setRowIndex(getVendorLines().getRowIndex());
			} catch (Exception e) {
			  setErrorMessage(this.getText("common_system_error"));
			  logger.error(e.getMessage());
				e.printStackTrace();
			}
  	}
	}
	
	public void updateVendor() {
	  logger.debug(" Updating vendor ");
	  try {
	    setErrorMessage("");
	    if(validate()) {
		    int rowIdx = getRowIndex();  		    
		    Date dt = new Date();
        this.currentVendor.setRecordLastUpdatedDate(dt);
		    this.vendorService.updateVendor(this.currentVendor);
		    putObjInList(rowIdx,this.currentVendor);
		    setErrorMessage("");
  		}
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteVendor() {
	  logger.debug("Deleting vendor");
	  setErrorMessage("");
	  if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {  			
				int rowIndex = getVendorLines().getRowIndex();
				vendorService.deleteVendor(Long.parseLong(recordId));
				List<ItemVendor> udvList = (List<ItemVendor>) getVendorLines().getWrappedData();
				udvList.remove(rowIndex);
			} catch (Exception e) {
			  setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
  	}
	}
	@SuppressWarnings("unchecked")
	private void putObjInList(int idx,Object fetchObj) {
		List<ItemVendor> list = (List<ItemVendor>) getVendorLines().getWrappedData();
		list.remove(idx);
		list.add(idx, (ItemVendor) fetchObj);
  }

	/**
	 * @return the currentVendor
	 */
	public ItemVendor getCurrentVendor() {
		if (currentVendor == null) {
			currentVendor = new ItemVendor();
		}
		return currentVendor;
	}

	/**
	 * @param currentVendor the currentVendor to set
	 */
	public void setCurrentVendor(ItemVendor currentVendor) {
		this.currentVendor = currentVendor;
	}
	
	/**
	 * @return the vendorLines
	 */
	public ListDataModel getVendorLines() {
		if(vendorLines == null){
			vendorLines = new ListDataModel();
		}
		return vendorLines;
	}
	
	/**
	 * @param vendorLines the vendorLines to set
	 */
	public void setVendorLines(ListDataModel vendorLines) {
		this.vendorLines = vendorLines;
	}	
}
