package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.ViewDataProvider;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("protegeViewHandler")
@Scope("session")
public class ProtegeViewHandler extends BasePage implements Serializable {	
    
  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -1110938352734812489L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger.getLogger( ProtegeViewHandler.class );
  
  private MemberService memberService;
	private Member currentProtege;
	List<SelectItem> mentorList;
	private ListDataModel protegeLines;
	private SearchBean searchBean;
	private String mentorId;	
	private ViewDataProvider viewDataProvider;
	
  @Autowired
	public ProtegeViewHandler(MemberService memberService,
      ViewDataProvider viewDataProvider) {
		this.memberService = memberService;
		this.viewDataProvider = viewDataProvider;
		this.initialize();
	} 
    
	public void initialize(){		
	  loadProteges();
	}

	public boolean validate (Member protege) {
	  logger.debug(" Validating protege ");
	  boolean flag = true;
	  StringBuffer errorMessage = new StringBuffer();
	  if ( protege == null ) {
	    errorMessage.append(this.getText("common_system_error"));
	    flag = false;
		} else {
		  if (getMentorId() == null || getMentorId().isEmpty()) {
		    errorMessage.append(this.getText("mentor_notselect"));
		    flag = false;
		  }
		}
		if (!flag) setErrorMessage(errorMessage.toString());
	  return flag;
	}
	
	public void loadProteges() {		
	  try {
	    Object value = CommonConstants.PROTEGE;
	    logger.debug(" Load proteges ");    
	    List<Member> protegeList = memberService.findByProperty("typeId", value);
	    getProtegeLines().setWrappedData(protegeList);
	  }catch (Exception ex) {
      logger.error("Unable to load proteges:"+ex.getMessage());
	    ex.printStackTrace();
    }
	}		
	
	public void loadProtegesByCriteria() {
	  try {
	    logger.debug(" Load proteges by search critariya ");    
	    SearchBean value = getSearchBean();
	    value.setRecordId(CommonConstants.PROTEGE);
	    List<Member> protegeList = memberService.findByProperties(value);
	    getProtegeLines().setWrappedData(protegeList);
	  }catch (Exception ex) {
	    logger.error("proteges by search critariya:"+ex.getMessage());
	    ex.printStackTrace();
	  }
	}	
	
	@SuppressWarnings("unchecked")
  public void loadMentors() {    
	  Object value = CommonConstants.MENTOR;
    logger.debug(" Load mentors ");
    ArrayList<SelectItem> mList = new ArrayList<SelectItem>();
    mList.add(new SelectItem("",""));
    try {
      List<Member> pList = memberService.findByProperty("typeId", value);
      if(pList!=null && !pList.isEmpty()) {
        Iterator list = pList.iterator();
        while(list.hasNext()) {
          Member mentor = (Member) list.next();
          mList.add(new SelectItem(""+mentor.getMemberId(),mentor.getFirstName()));
        }      
      }
    } catch (Exception ex) {
      logger.error("Unable to load mentors:"+ex.getMessage());
      ex.printStackTrace();
    }
    setMentorList(mList);
  }
	
	public void preAssignedMentor() {
    logger.debug(" Prepare mentor object for assiging mentor:: "+this.getParameter(ServiceConstants.RECORD_ID));
    if(this.getParameter(ServiceConstants.RECORD_ID)!=null) {
      String recordId = (String) this.getParameter(ServiceConstants.RECORD_ID);
      try {
        setErrorMessage("");        
        this.currentProtege = memberService.findById(Long.parseLong(recordId));
        setSecHeaderMsg(this.getText("header_msg_assign_proteges_to_mentor"));
        setReRenderIds("PROTEGE_LINES");
        setRowIndex(getProtegeLines().getRowIndex());
        System.out.println(currentProtege.getMemberId());
      } catch (Exception e) {
        setErrorMessage(this.getText("common_system_error"));
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
  }
	public void assignedMentor() {
	  logger.debug(" Assigned mentor to protege ");
    setErrorMessage("");
    try {
      if(this.currentProtege != null) {
        if(validate(this.currentProtege)) {         
          this.currentProtege.setMentoredByMemberId(Long.parseLong(getMentorId()));
          memberService.update(this.currentProtege);
        }
      }
    } catch (Exception e) {
      setErrorMessage(this.getText("common_system_error"));
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

	/**
	 * @return the currentProtege
	 */
	public Member getCurrentProtege() {
		if (currentProtege == null) {
			System.out.println(" Init protege ");
		  currentProtege = new Member();
		}
		return currentProtege;
	}

	/**
	 * @param currentProtege the currentProtege to set
	 */
	public void setCurrentProtege(Member currentProtege) {
		this.currentProtege = currentProtege;
	}

	/**
	 * @return the mentorList
	 */
	public List<SelectItem> getMentorList() {
		if (mentorList == null ) {
			loadMentors();
		}
		return mentorList;
	}
	/**
	 * @param mentorList the mentorList to set
	 */
	public void setMentorList(List<SelectItem> mentorList) {
		this.mentorList = mentorList;
	}	

  public List<SelectItem> getStatesList() {
    return viewDataProvider.getStateList();
  }
  
	/**
	 * @return the protegeLines
	 */
	public ListDataModel getProtegeLines() {
		if(protegeLines == null){
			protegeLines = new ListDataModel();
		}
		return protegeLines;
	}
	
	/**
	 * @param protegeLines the protegeLines to set
	 */
	public void setProtegeLines(ListDataModel protegeLines) {
		this.protegeLines = protegeLines;
	}
	
	public String getMentorId() {
    return mentorId;
  }

  public void setMentorId(String mentorId) {
    this.mentorId = mentorId;
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

}
