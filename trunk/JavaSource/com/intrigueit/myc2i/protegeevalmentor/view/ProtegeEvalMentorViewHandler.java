package com.intrigueit.myc2i.protegeevalmentor.view;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.protegeevalmentor.domain.ProtegeEvaluationOfMentor;
import com.intrigueit.myc2i.protegeevalmentor.service.ProtegeEvalMentorService;

@Component("proEvalManViewHandler")
@Scope("session")
public class ProtegeEvalMentorViewHandler extends BasePage implements Serializable {
 
  private static final long serialVersionUID = -3717630038942130642L;

  /** Initialize the Logger */
  protected static final Logger logger = Logger
      .getLogger(ProtegeEvalMentorViewHandler.class);

  private ProtegeEvalMentorService protegeEvalMentorService;
  private ProtegeEvaluationOfMentor currentProEvalMentor;  
  String evalFactorSixRatingNum;
  /**
   * @return the evalFactorSixRatingNum
   */
  public String getEvalFactorSixRatingNum() {
    return evalFactorSixRatingNum;
  }

  /**
   * @param evalFactorSixRatingNum the evalFactorSixRatingNum to set
   */
  public void setEvalFactorSixRatingNum(String evalFactorSixRatingNum) {
    this.evalFactorSixRatingNum = evalFactorSixRatingNum;
  }

  @Autowired
  public ProtegeEvalMentorViewHandler(ProtegeEvalMentorService protegeEvalMentorService) {
    this.protegeEvalMentorService = protegeEvalMentorService;
    this.initialize();
  }

  public void initialize() {
    setSecHeaderMsg("");
  }
  
  public boolean validate() {
    logger.debug(" Validating user define values ");
    boolean flag = true;
    StringBuffer errorMessage = new StringBuffer();
    if (this.currentProEvalMentor == null) {
      errorMessage.append(this.getText("common_system_error"));
      return false;
    } else {
      /*if (this.currentProEvalMentor.getEvalFactorSixRatingNum() == null
          && this.currentProEvalMentor.getEvalFactorSixRatingNum() !=0) {
        errorMessage.append(this.getText("common_error_prefix"))
            .append(" ").append(
                this.getText("protege_evaluat_mentor_factor_six_text"));
        flag = false;
      }*/
    }
    if (!flag)
      setErrorMessage(this.getText("common_error_header")
          + errorMessage.toString());
    return flag;
  }

  public void doSave() {
    setErrorMessage("");
    try {      
      if (this.getParameter(ServiceConstants.MENTOR_ID) != null) {
      String mentorId = (String) this.getParameter(ServiceConstants.MENTOR_ID);
      Date dt = new Date();
        if (validate()) {
          this.currentProEvalMentor.setEvalDate(dt);
          this.currentProEvalMentor.setProtegeMemberId(this.getMember().getMemberId());
          this.currentProEvalMentor.setMentorMemberId(Long.parseLong(mentorId));
          protegeEvalMentorService.save(this.currentProEvalMentor);
          logger.debug("Added and clear form...");
          this.setCurrentProEvalMentor(new ProtegeEvaluationOfMentor());
          this.setErrorMessage(this.getText("update_success_message"));
          this.setMsgType(ServiceConstants.INFO);
        }
      }
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
    this.setCurrentProEvalMentor(new ProtegeEvaluationOfMentor());
  }
  /**
   * @return the currentProEvalMentor
   */
  public ProtegeEvaluationOfMentor getCurrentProEvalMentor() {
    if ( currentProEvalMentor == null ) {
      currentProEvalMentor = new ProtegeEvaluationOfMentor();
    }
    return currentProEvalMentor;
  }

  /**
   * @param currentProEvalMentor the currentProEvalMentor to set
   */
  public void setCurrentProEvalMentor(
      ProtegeEvaluationOfMentor currentProEvalMentor) {
    this.currentProEvalMentor = currentProEvalMentor;
  }


  
  
}
