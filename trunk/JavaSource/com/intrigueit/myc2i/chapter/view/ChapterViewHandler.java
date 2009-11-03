package com.intrigueit.myc2i.chapter.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.chapter.domain.LocalChapter;
import com.intrigueit.myc2i.chapter.service.ChapterService;
import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewDataProvider;

@Component("chapterViewHandler")
@Scope("session")
public class ChapterViewHandler extends BasePage implements Serializable {	
  
  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -463590747959332517L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( ChapterViewHandler.class );
  
  private ChapterService chapterService;
	private LocalChapter currentChapter;
	private SearchBean searchBean;	
	List<SelectItem> categoryList;
	private ListDataModel chapterLines;
	private String categoryName;
	private ViewDataProvider viewDataProvider;
		
  @Autowired
	public ChapterViewHandler(ChapterService chapterService,
      ViewDataProvider viewDataProvider) {
		this.chapterService = chapterService;
		this.viewDataProvider = viewDataProvider;
		this.initializeChapter();
	}

	public void initializeChapter(){		
	  setSecHeaderMsg("");
	  loadChapters();
	}

	public void loadChapters() {
    try {
      List<LocalChapter> chapterList = chapterService.loadAll();
      getChapterLines().setWrappedData(chapterList);
    } catch (Exception e) {
      logger.error(" Unable to load chapters "+e.getMessage());
      e.printStackTrace();
    }
  }
	
	public boolean validate () {
	  logger.debug(" Validating chapter ");
	  boolean flag = true;
	  StringBuffer errorMessage = new StringBuffer();
	  if ( this.currentChapter == null ) {
	    errorMessage.append(this.getText("common_system_error"));
	    return false;
		} else {
      if (chapterService.isRecordExist(this.currentChapter.getChapterId(),
          this.currentChapter.getChapterName())){
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                  .append(this.getText("chapter_exist"));
        flag = false;       
      }
    }
    if (!flag) setErrorMessage(this.getText("common_error_header") + errorMessage.toString());
	  return flag;
	}	
	
	public void loadChapterByCriteria() {
		logger.debug(" Load chapter by search critariya ");    
		try {
		  SearchBean value = getSearchBean();
		  List<LocalChapter> chapterList = chapterService.findByProperties(value);
		  getChapterLines().setWrappedData(chapterList);
		} catch (Exception e) {
      logger.error(" Unable to load chapter by search :"+e.getMessage());
      e.printStackTrace();
    }
	}
	
	public void setCommonData ( String action ) {       
	  setSecHeaderMsg("");
	  try {
      Date dt = new Date();           
      this.currentChapter.setRecordLastUpdaterId(""+this.getMember().getMemberId());    
      this.currentChapter.setRecordLastUpdatedDate(dt);
      if (action.equals(ServiceConstants.ADD )) {
        this.currentChapter.setRecordCreatorId(""+this.getMember().getMemberId());
        this.currentChapter.setRecordCreatedDate(dt);
      }
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message"));
      logger.error(" Unable to load training items by search critariya :"+e.getMessage());
      e.printStackTrace();
    }
  }
	
	public void preAddChapter () {
	  logger.debug(" Preparing user define values for adding new record ");
	  setErrorMessage("");
		try {		  
		  this.currentChapter = new LocalChapter();		  		  
			this.setCommonData(ServiceConstants.ADD);
			setSecHeaderMsg(this.getText("header_msg_manage_chapter") + " " + this.getText("header_msg_add"));
			setActionType(ServiceConstants.ADD);
			setReRenderIds("CHAPTER_LINES");
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
		  logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addChapter () {
	  logger.debug(" Adding new user define values into database ");
	  setErrorMessage("");
		try {		  
		  this.currentChapter = getCurrentChapter();
      if(validate()) {
        this.currentChapter.setChapterState(null);
        this.currentChapter.setChapterCountry(null);
  			this.chapterService.addChapter(this.currentChapter);
  			List<LocalChapter> udvList = (List<LocalChapter>) getChapterLines().getWrappedData();
  			udvList.add(this.currentChapter);
			}
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void preUpdateChapter() {
		setErrorMessage("");
		logger.debug(" Preparing user define values for updating ");
		if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {
  			this.currentChapter = chapterService.loadById(Long.parseLong(recordId));
  			this.setCommonData(ServiceConstants.UPDATE);
  			setSecHeaderMsg(this.getText("header_msg_manage_chapter") + " " + this.getText("header_msg_update"));
				setActionType(ServiceConstants.UPDATE);
				setReRenderIds("CHAPTER_LINES");
				setRowIndex(getChapterLines().getRowIndex());
			} catch (Exception e) {
			  setErrorMessage(this.getText("common_system_error"));
			  logger.error(e.getMessage());
				e.printStackTrace();
			}
  	}
	}
	
	public void updateChapter() {
	  logger.debug(" Updating user define values ");
	  setErrorMessage("");
	  try {
			this.currentChapter = getCurrentChapter();
  		if(validate()) {
		    int rowIdx = getRowIndex();  		    
		    Date dt = new Date();
        this.currentChapter.setRecordLastUpdatedDate(dt);
		    chapterService.updateChapter(this.currentChapter);
		    putObjInList(rowIdx,this.currentChapter);
		  }
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteChapter() {
	  logger.debug(" Deleting user define values from database ");
	  setErrorMessage("");
	  if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {  			
				int rowIndex = getChapterLines().getRowIndex();
				chapterService.deleteChapter(Long.parseLong(recordId));
				List<LocalChapter> udvList = (List<LocalChapter>) getChapterLines().getWrappedData();
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
		List<LocalChapter> list = (List<LocalChapter>) getChapterLines().getWrappedData();
		list.remove(idx);
		list.add(idx, (LocalChapter) fetchObj);
  }

	/**
	 * @return the currentChapter
	 */
	public LocalChapter getCurrentChapter() {
		if (currentChapter == null) {
			currentChapter = new LocalChapter();
		}
		return currentChapter;
	}

	/**
	 * @param currentChapter the currentChapter to set
	 */
	public void setCurrentChapter(LocalChapter currentChapter) {
		this.currentChapter = currentChapter;
	}	
	/**
	 * @return the categoryId
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the chapterLines
	 */
	public ListDataModel getChapterLines() {
		if(chapterLines == null){
			chapterLines = new ListDataModel();
		}
		return chapterLines;
	}
	
	/**
	 * @param chapterLines the chapterLines to set
	 */
	public void setChapterLines(ListDataModel chapterLines) {
		this.chapterLines = chapterLines;
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
  
  public List<SelectItem> getStatesList() {
    return viewDataProvider.getStateList();
  }

  public List<SelectItem> getCountryList() {
    return this.viewDataProvider.getCountryList();
  }

}
