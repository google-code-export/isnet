package com.intrigueit.myc2i.tutorial.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.utility.FileUploadBean;
import com.intrigueit.myc2i.common.utility.UFile;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;
import com.intrigueit.myc2i.tutorial.service.DocumentService;

@Component("documentViewHandler")
@Scope("session")
public class DocumentViewHandler extends BasePage implements Serializable {
	private static final long serialVersionUID = 3515525820248010355L;
	
	/** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( DocumentViewHandler.class );
	
  private DocumentService documentService;
	private TestTutorialDocument currentDocument;
	private ListDataModel documentLines;
	private String audioPath;
	private String videoPath;
	private FileUploadBean fileUploadBean;	
	
	@Autowired
  public DocumentViewHandler(DocumentService documentService) {
    this.documentService = documentService;
    this.initializeDocument();    
  }

  public void initializeDocument(){   
    loadDocuments();
  }

  public void uploadAudio() {
    try {
      if (fileUploadBean != null && fileUploadBean.getUploadFile() != null) {
        UFile ufile = fileUploadBean.getUploadFile();
        this.currentDocument.setDocumentIntroAudio(ufile.getData());        
        this.setAudioPath(ufile.getName());
        logger.debug("Uploaded audio::"+ufile.getName());
        fileUploadBean.clearUploadData();
      }
    } catch(Exception e) {
      logger.error("Unable to upload audio files :"+e.getMessage());
      e.printStackTrace();
    }
  }
  
  public void uploadVideo() {
    try {
      if (fileUploadBean != null && fileUploadBean.getUploadFile() != null) {
        UFile ufile = fileUploadBean.getUploadFile();
        this.currentDocument.setDocumentIntroVideo(ufile.getData());
        this.setVideoPath(ufile.getName());
        logger.debug("Uploaded video::"+ufile.getName());
        fileUploadBean.clearUploadData();
      }
    } catch(Exception e) {
      logger.error("Unable to upload video files :"+e.getMessage());
      e.printStackTrace();
    }
  }
  	
	public boolean validate (TestTutorialDocument document) {
	  logger.debug("Validating Document");
	  boolean flag = true;
	  StringBuffer errorMessage = new StringBuffer();
	  if ( document == null ) {
	    errorMessage.append(this.getText("common_system_error"));
	    return false;
		} 	
		if (!flag) setErrorMessage(errorMessage.toString());
	  return flag;
	}
	
	public void loadDocuments() {
	  logger.debug(" Load Documents ");
	  try {
	    List<TestTutorialDocument> recordList = documentService.loadDispDocuments();
	    getDocumentLines().setWrappedData(recordList);
	  } catch (Exception e) {
      logger.error(" Unable to load  Documents :"+e.getMessage());
      e.printStackTrace();
    }
	}
	
	public void setCommonData ( String action ) {
	  setSecHeaderMsg("");
	  Date dt = new Date();           
    this.audioPath = "";
    this.videoPath = "";
    try {
      this.currentDocument.setRecordLastUpdaterId(""+this.getMember().getMemberId());    
      this.currentDocument.setRecordLastUpdatedDate(dt);
      this.setVideoPath("");
      this.setAudioPath("");
      if (action.equals(ServiceConstants.ADD )) {
        this.currentDocument.setRecordCreatorId(""+this.getMember().getMemberId());
        this.currentDocument.setRecordCreateDate(dt);
      }
    } catch (Exception e) {
      setSecHeaderMsg(this.getText("invalid_seesion_message"));
      logger.error(" Unable to set common data :"+e.getMessage());
      e.printStackTrace();
    }
   }
	
	public void preAddDocument () {	  
	  logger.debug("Preparing new document");
	  setErrorMessage("");
	  try {
	    this.currentDocument = new TestTutorialDocument();
	    this.setCommonData(ServiceConstants.ADD);
	    setSecHeaderMsg(this.getText("header_msg_tutorial_document") + " " + this.getText("header_msg_add"));
			setActionType(ServiceConstants.ADD);
			setReRenderIds("DOCUMENT_LINES");
		} catch (Exception e) {			
		  logger.error(e.getMessage());
		  setErrorMessage(this.getText("common_system_error"));
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addDocument () {
	  logger.debug("Adding new document");
	  try {		  
		  setErrorMessage("");
		  if(validate(this.currentDocument)) {		            
  			this.documentService.addDocument(this.currentDocument);
  			List<TestTutorialDocument> udvList = (List<TestTutorialDocument>) getDocumentLines().getWrappedData();
  			udvList.add(this.currentDocument);
  		}
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
	}	            
	public void preUpdateDocument() {
	  logger.debug("Prepare document for updating");
	  if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {
  		  setErrorMessage("");  	    
  		  this.currentDocument = documentService.loadById(Long.parseLong(recordId));
  		  this.setCommonData(ServiceConstants.UPDATE);
  		  setSecHeaderMsg(this.getText("header_msg_tutorial_document") + " " + this.getText("header_msg_update"));
				setActionType(ServiceConstants.UPDATE);
				setReRenderIds("DOCUMENT_LINES");
				setRowIndex(getDocumentLines().getRowIndex());
			} catch (Exception e) {
			  setErrorMessage(this.getText("common_system_error"));
			  logger.error(e.getMessage());
				e.printStackTrace();
			}
  	}
	}
	
	public void updateDocument() {
	  logger.debug(" Updating document ");
	  try {
	    setErrorMessage("");
	    TestTutorialDocument document = getCurrentDocument();
	    if(validate(document)) {  		  
		    int rowIdx = getRowIndex();  		    
		    this.documentService.updateDocument(document);
		    putObjInList(rowIdx,document);
		    setErrorMessage("");
  		}
		} catch (Exception e) {
		  setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteDocument() {
	  logger.debug("Deleting training item object");
	  setErrorMessage("");
	  if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
  		String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
  		try {  			
				int rowIndex = getDocumentLines().getRowIndex();
				documentService.deleteDocument(Long.parseLong(recordId));
				List<TestTutorialDocument> udvList = (List<TestTutorialDocument>) getDocumentLines().getWrappedData();
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
		try {
		  List<TestTutorialDocument> list = (List<TestTutorialDocument>) getDocumentLines().getWrappedData();
		  list.remove(idx);
		  list.add(idx, (TestTutorialDocument) fetchObj);
		} catch (Exception e) {
      logger.error("Unable to process list");
		  e.printStackTrace();
    }
  }

	/**
	 * @return the currentDocument
	 */
	public TestTutorialDocument getCurrentDocument() {
		if (currentDocument == null) {
			currentDocument = new TestTutorialDocument();
		}
		return currentDocument;
	}

	/**
	 * @param currentDocument the currentDocument to set
	 */
	public void setCurrentDocument(TestTutorialDocument currentDocument) {
		this.currentDocument = currentDocument;
	}
	
	/**
	 * @return the documentLines
	 */
	public ListDataModel getDocumentLines() {
		if(documentLines == null){
			documentLines = new ListDataModel();
		}
		return documentLines;
	}
	
	/**
	 * @param documentLines the documentLines to set
	 */
	public void setDocumentLines(ListDataModel documentLines) {
		this.documentLines = documentLines;
	}	
	
	/**
   * @return the fileUploadBean
   */
  public FileUploadBean getFileUploadBean() {
    if (fileUploadBean == null ) fileUploadBean = new FileUploadBean();
    return fileUploadBean;
  }

  /**
   * @param fileUploadBean the fileUploadBean to set
   */
  public void setFileUploadBean(FileUploadBean fileUploadBean) {
    this.fileUploadBean = fileUploadBean;
  }

  
	/**
   * @return the audioPath
   */
  public String getAudioPath() {
    return audioPath;
  }

  /**
   * @param audioPath the audioPath to set
   */
  public void setAudioPath(String audioPath) {
    this.audioPath = audioPath;
  }

  /**
   * @return the videoPath
   */
  public String getVideoPath() {
    return videoPath;
  }

  /**
   * @param videoPath the videoPath to set
   */
  public void setVideoPath(String videoPath) {
    this.videoPath = videoPath;
  } 

}
