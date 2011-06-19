package com.intrigueit.myc2i.protegeevalmentor.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.protegeevalmentor.domain.ProtegeEvaluationOfMentor;
import com.intrigueit.myc2i.protegeevalmentor.service.ProtegeEvalMentorService;

@Component("proEvalManViewHandler")
@Scope("session")
public class ProtegeEvalMentorViewHandler extends BasePage implements
		Serializable {

	private static final long serialVersionUID = -3717630038942130642L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(ProtegeEvalMentorViewHandler.class);

	private ProtegeEvalMentorService protegeEvalMentorService;
	private ProtegeEvaluationOfMentor currentProEvalMentor;
	private MemberService memberService;
	String evalFactorSixRatingNum;
	private String init;
	
	private ArrayList<ProtegeEvaluationOfMentor> evaluations;
	

	@Autowired
	public ProtegeEvalMentorViewHandler(
			ProtegeEvalMentorService protegeEvalMentorService,
			MemberService memberService) {
		this.protegeEvalMentorService = protegeEvalMentorService;
		this.memberService = memberService;
		this.initialize();
	}

	public void initialize() {
		setSecHeaderMsg("");
		setErrorMessage("");
	}

	/**
	 * @return the init
	 */
	public String getInit() {
		setSecHeaderMsg("");
		setErrorMessage("");
		this.setActionType(ServiceConstants.ADD);
		if (this.getMember() != null) {
			Member member = memberService.findById(this.getMember()
					.getMemberId());
			if (member.getMentoredByMemberId() == null) {
				this.setCurrentProEvalMentor(new ProtegeEvaluationOfMentor());
				setErrorMessage(this.getText("protege_evaluat_mentor_errmsg"));
			} else {
				List<ProtegeEvaluationOfMentor> objList = protegeEvalMentorService
						.findByProperty("protegeMemberId", this.getMember()
								.getMemberId());
				if (objList != null && !objList.isEmpty()) {
					this.setActionType(ServiceConstants.UPDATE);
					this.setCurrentProEvalMentor(objList.get(0));
				}
			}
		}
		return init;
	}

	/**
	 * @param init
	 *            the init to set
	 */
	public void setInit(String init) {
		this.init = init;
	}

	/**
	 * @return the evalFactorSixRatingNum
	 */
	public String getEvalFactorSixRatingNum() {
		return evalFactorSixRatingNum;
	}

	/**
	 * @param evalFactorSixRatingNum
	 *            the evalFactorSixRatingNum to set
	 */
	public void setEvalFactorSixRatingNum(String evalFactorSixRatingNum) {
		this.evalFactorSixRatingNum = evalFactorSixRatingNum;
	}

	public boolean validate() {
		logger.debug(" Validating user define values ");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (this.currentProEvalMentor == null) {
			errorMessage.append(this.getText("common_system_error"));
			return false;
		} else {
			/*
			 * if (this.currentProEvalMentor.getEvalFactorSixRatingNum() == null
			 * && this.currentProEvalMentor.getEvalFactorSixRatingNum() !=0) {
			 * errorMessage.append(this.getText("common_error_prefix"))
			 * .append(" ").append(
			 * this.getText("protege_evaluat_mentor_factor_six_text")); flag =
			 * false; }
			 */
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	public void doSave() {
		setErrorMessage("");
		try {
			// if (this.getParameter(ServiceConstants.MENTOR_ID) != null) {
			// String mentorId = (String)
			// this.getParameter(ServiceConstants.MENTOR_ID);
			Long mentorId = this.getMember().getMentoredByMemberId();
			if (mentorId != null) {
				Date dt = new Date();
				if (validate()) {

					this.currentProEvalMentor.setEvalDate(dt);
					this.currentProEvalMentor.setProtegeMemberId(this
							.getMember().getMemberId());
					this.currentProEvalMentor.setMentorMemberId(mentorId);
					protegeEvalMentorService.save(this.currentProEvalMentor);

					logger.debug("Added and clear form...");

					// this.setCurrentProEvalMentor(new
					// ProtegeEvaluationOfMentor());
					if (this.getActionType().equals(ServiceConstants.UPDATE)) {
						this.setErrorMessage(this
								.getText("update_success_message"));
					} else {
						this.setErrorMessage(this
								.getText("added_success_message"));
					}

					this.setMsgType(ServiceConstants.INFO);
				}
			} else {
				setErrorMessage(this
						.getText("protege_evaluat_mentor_no_mentor_errmsg"));
			}
			// }
		} catch (Exception e) {
			if (this.currentProEvalMentor.getProtegeEvalOfMentorId() != null) {
				this.currentProEvalMentor.setProtegeEvalOfMentorId(null);
			}
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void doClear() {
		logger.debug("Clear form...");
		setErrorMessage("");
		this.setCurrentProEvalMentor(new ProtegeEvaluationOfMentor());
	}

	/**
	 * @return the currentProEvalMentor
	 */
	public ProtegeEvaluationOfMentor getCurrentProEvalMentor() {
		if (currentProEvalMentor == null) {
			currentProEvalMentor = new ProtegeEvaluationOfMentor();
		}
		return currentProEvalMentor;
	}

	/**
	 * @param currentProEvalMentor
	 *            the currentProEvalMentor to set
	 */
	public void setCurrentProEvalMentor(
			ProtegeEvaluationOfMentor currentProEvalMentor) {
		this.currentProEvalMentor = currentProEvalMentor;
	}

	public ArrayList<ProtegeEvaluationOfMentor> getEvaluations() {
		String mentorId = this.getRequest().getParameter("MENTOR_ID");
		String protegeId = this.getRequest().getParameter("PROTEGE_ID");
		
		try{
			
			if(mentorId != null && !mentorId.equals("")){
				evaluations =  (ArrayList<ProtegeEvaluationOfMentor>) this.protegeEvalMentorService.findByProperty("mentorMemberId", Long.parseLong(mentorId));
			}
			if(protegeId != null && !protegeId.equals("")){
				evaluations =  (ArrayList<ProtegeEvaluationOfMentor>) this.protegeEvalMentorService.findByProperty("protegeMemberId", Long.parseLong(protegeId));
			}

		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}

		return evaluations;
	}

	public void setEvaluations(ArrayList<ProtegeEvaluationOfMentor> evaluations) {
		this.evaluations = evaluations;
	}

}
