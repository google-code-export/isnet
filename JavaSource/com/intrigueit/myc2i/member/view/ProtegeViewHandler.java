package com.intrigueit.myc2i.member.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("protegeViewHandler")
@Scope("session")
public class ProtegeViewHandler extends BasePage implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -1110938352734812489L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(ProtegeViewHandler.class);

	private MemberService memberService;
	private Member currentProtege;
	List<SelectItem> mentorList;
	private ListDataModel protegeLines;
	private SearchBean searchBean;
	private String mentorId;
	private ViewDataProvider viewDataProvider;
	private UDValuesService udService;
	
	@Autowired
	public ProtegeViewHandler(MemberService memberService,
			ViewDataProvider viewDataProvider,UDValuesService udService) {
		this.memberService = memberService;
		this.viewDataProvider = viewDataProvider;
		this.udService = udService;
		this.initialize();
	}

	public void initialize() {
		loadProteges();
	}

	public boolean validate(Member protege) {
		logger.debug(" Validating protege ");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (protege == null) {
			errorMessage.append(this.getText("common_system_error"));
			flag = false;
		} else {
			if (getMentorId() == null || getMentorId().isEmpty()) {
				errorMessage.append(this.getText("mentor_notselect"));
				flag = false;
			}
		}
		if (!flag)
			setErrorMessage(errorMessage.toString());
		return flag;
	}

	public void loadProteges() {
		try {
			logger.debug(" Load proteges ");
			List<Member> protegeList = memberService.loadProtegeWithoutMentor();
			getProtegeLines().setWrappedData(protegeList);
		} catch (Exception ex) {
			logger.error("Unable to load proteges:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void loadProtegesByCriteria() {
		try {
			logger.debug(" Load proteges by search critariya ");
			SearchBean value = getSearchBean();
			if (searchBean.getExtraProps() != null
					&& !searchBean.getExtraProps().equals("true")) {
				searchBean.setExtraProps("mentoredByMemberId is null");
			} else {
				searchBean.setExtraProps("");
			}
			value.setRecordId(CommonConstants.PROTEGE);
			List<Member> protegeList = memberService.findByProperties(value);
			getProtegeLines().setWrappedData(protegeList);
		} catch (Exception ex) {
			logger.error("proteges by search critariya:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadMentors() {
		Object value = CommonConstants.MENTOR;
		logger.debug(" Load mentors ");
		ArrayList<SelectItem> mList = new ArrayList<SelectItem>();
		mList.add(new SelectItem("", ""));
		try {
			List<Member> pList = memberService.findByProperty("typeId", value);
			if (pList != null && !pList.isEmpty()) {
				Iterator list = pList.iterator();
				while (list.hasNext()) {
					Member mentor = (Member) list.next();
					mList.add(new SelectItem("" + mentor.getMemberId(),
							this.getDisplayItem(mentor)));
				}
			}
		} catch (Exception ex) {
			logger.error("Unable to load mentors:" + ex.getMessage());
			ex.printStackTrace();
		}
		setMentorList(mList);
	}
	
	private String getDisplayItem(Member mentor) {
		String dispItem = "";
		try {
			UserDefinedValues profession = udService.loadById(new Long(mentor.getProfession()));		
			String gender = (mentor.getGenderInd() == "M") ? "Male" : "Female"; 
			Calendar cal = Calendar.getInstance();
			int age = cal.get(Calendar.YEAR) - mentor.getBirthYear().intValue();
			dispItem = mentor.getFirstName() + " " + mentor.getLastName() + ", "
			+ profession.getUdValuesDesc() + ", "
			+ gender + ", "
			+ age;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dispItem;
		
	}
	public void preAssignedMentor() {
		logger.debug(" Prepare mentor object for assiging mentor:: "
				+ this.getParameter(ServiceConstants.RECORD_ID));
		if (this.getParameter(ServiceConstants.RECORD_ID) != null) {
			String recordId = (String) this
					.getParameter(ServiceConstants.RECORD_ID);
			try {
				setErrorMessage("");
				this.currentProtege = memberService.findById(Long
						.parseLong(recordId));
				setSecHeaderMsg(this
						.getText("header_msg_assign_proteges_to_mentor"));
				setReRenderIds("PROTEGE_LINES");
				this.setRowIndex(getProtegeLines().getRowIndex());
			} catch (Exception e) {
				setErrorMessage(this.getText("common_system_error"));
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void assignedMentor() {
		logger.debug(" Assigned mentor to protege ");
		setErrorMessage("");
		try {
			if (this.currentProtege != null) {
				if (validate(this.currentProtege)) {
					this.currentProtege.setMentoredByMemberId(Long
							.parseLong(getMentorId()));
					Member mentor = memberService.findById(Long.parseLong(getMentorId()));
					memberService.update(this.currentProtege);
					if (searchBean.getExtraProps() != null
							&& !searchBean.getExtraProps().equals("true")) {
						List<Member> itemList = (List<Member>) getProtegeLines()
								.getWrappedData();
						int rowIndex = this.getRowIndex();
						itemList.remove(rowIndex);						
					}
					/* Send notification to protege */
					//sendNotification(this.currentProtege.getEmail(),
			  		//        this.getText("assign_mentor_to_protege_sub"),
					//);
					String pBody = this.getText("assign_mentor_to_protege_bodytoprotege", new String[]{mentor.getFirstName()+ " " + mentor.getLastName()});
					String name1 = this.currentProtege.getFirstName()+ " " + this.currentProtege.getLastName();
					this.sendEmail(this.currentProtege.getEmail(),  this.getText("assign_mentor_to_protege_sub"), pBody, name1);
					
					/* Send notification email to mentor */
					String mBody = this.getText("assign_mentor_to_protege_bodytomentor", new String[]{mentor.getFirstName()+ " " + mentor.getLastName()});
					String name2 = mentor.getFirstName()+ " " + mentor.getLastName();
					this.sendEmail(mentor.getEmail(),  this.getText("assign_mentor_to_protege_sub"), mBody, name2);
										
/*					sendNotification(mentor.getEmail(),
			  		        this.getText("assign_mentor_to_protege_sub"),
			  		        this.getText("assign_mentor_to_protege_bodytomentor",
			                  new String[]{this.currentProtege.getFirstName()+ " " + this.currentProtege.getLastName()}));*/
					
					logger.debug(" Protege assigned to mentor ");
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
			currentProtege = new Member();
		}
		return currentProtege;
	}

	/**
	 * @param currentProtege
	 *            the currentProtege to set
	 */
	public void setCurrentProtege(Member currentProtege) {
		this.currentProtege = currentProtege;
	}

	/**
	 * @return the mentorList
	 */
	public List<SelectItem> getMentorList() {
		if (mentorList == null) {
			loadMentors();
		}
		return mentorList;
	}
	/**
	 * @param mentorList
	 *            the mentorList to set
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
		if (protegeLines == null) {
			protegeLines = new ListDataModel();
		}
		return protegeLines;
	}

	/**
	 * @param protegeLines
	 *            the protegeLines to set
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
