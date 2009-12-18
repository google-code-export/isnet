package com.intrigueit.myc2i.media;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;
import com.intrigueit.myc2i.tutorial.service.ModulesService;
import com.intrigueit.myc2i.tutorial.service.QuestionAnsService;
import com.intrigueit.myc2i.tutotialtest.domain.TestResult;
import com.intrigueit.myc2i.tutotialtest.domain.TestResultDetails;
import com.intrigueit.myc2i.tutotialtest.service.TestResultDetailsService;
import com.intrigueit.myc2i.tutotialtest.service.TestResultService;
import com.intrigueit.myc2i.utility.Emailer;

@Component("modulePlayer")
@Scope("session")
public class ModulePlayer extends BasePage{

	private TestTutorialModules module;
	
	private TestTutorialQuestionAns currentPage;
	
	private List<TestTutorialQuestionAns> tutorials;
	
  	private ModulesService modulesService;
  	
  	private QuestionAnsService questionService;
  	
  	private String modulePlay;
  	
  	private MediaBean mediaBean;
  	
  	private int pageIndex = -1;
  	
  	private String pageContent;
  	
  	private Boolean hasQuestionAns;
  	
  	private Date examStartDate;
  	
  	private TestResultService testService;
  	private TestResultDetailsService testDetailsService;
  	
  	private String initPage;
  	private boolean disabledNext;
  	private boolean disabledLast;
  	private boolean showFirst = true;
  	private boolean showPrevious = true;
  	private boolean showStop = true;
  	private boolean showNext = true;
  	private boolean showLast = true;
  	private boolean notEndExam = true;
  	private Integer noOfQuestion;
  	private Integer noOfCorrectAns;
  	private String passStatus;
  	private Integer perOfMarks;
  	
  	/**
     * @return the disabledLast
     */
    public boolean isDisabledLast() {
      return disabledLast;
    }

    /**
     * @param disabledLast the disabledLast to set
     */
    public void setDisabledLast(boolean disabledLast) {
      this.disabledLast = disabledLast;
    }

    /**
     * @return the noOfQuestion
     */
    public Integer getNoOfQuestion() {
      return noOfQuestion;
    }

    /**
     * @param noOfQuestion the noOfQuestion to set
     */
    public void setNoOfQuestion(Integer noOfQuestion) {
      this.noOfQuestion = noOfQuestion;
    }

    /**
     * @return the noOfCorrectAns
     */
    public Integer getNoOfCorrectAns() {
      return noOfCorrectAns;
    }

    /**
     * @param noOfCorrectAns the noOfCorrectAns to set
     */
    public void setNoOfCorrectAns(Integer noOfCorrectAns) {
      this.noOfCorrectAns = noOfCorrectAns;
    }

    /**
     * @return the passStatus
     */
    public String getPassStatus() {
      return passStatus;
    }

    /**
     * @param passStatus the passStatus to set
     */
    public void setPassStatus(String passStatus) {
      this.passStatus = passStatus;
    }

    /**
     * @return the perOfMarks
     */
    public Integer getPerOfMarks() {
      return perOfMarks;
    }

    /**
     * @param perOfMarks the perOfMarks to set
     */
    public void setPerOfMarks(Integer perOfMarks) {
      this.perOfMarks = perOfMarks;
    }

    /**
     * @return the notEndExam
     */
    public boolean isNotEndExam() {
      return notEndExam;
    }

    /**
     * @param notEndExam the notEndExam to set
     */
    public void setNotEndExam(boolean notEndExam) {
      this.notEndExam = notEndExam;
    }

    /**
     * @return the disabledNext
     */
    public boolean isDisabledNext() {
      return disabledNext;
    }

    /**
     * @param disabledNext the disabledNext to set
     */
    public void setDisabledNext(boolean disabledNext) {
      this.disabledNext = disabledNext;
    }    
    
    /**
     * @return the showFirst
     */
    public boolean isShowFirst() {
      return showFirst;
    }

    /**
     * @param showFirst the showFirst to set
     */
    public void setShowFirst(boolean showFirst) {
      this.showFirst = showFirst;
    }

    /**
     * @return the showPrevious
     */
    public boolean isShowPrevious() {
      return showPrevious;
    }

    /**
     * @param showPrevious the showPrevious to set
     */
    public void setShowPrevious(boolean showPrevious) {
      this.showPrevious = showPrevious;
    }

    /**
     * @return the showStop
     */
    public boolean isShowStop() {
      return showStop;
    }

    /**
     * @param showStop the showStop to set
     */
    public void setShowStop(boolean showStop) {
      this.showStop = showStop;
    }

    /**
     * @return the showNext
     */
    public boolean isShowNext() {
      return showNext;
    }

    /**
     * @param showNext the showNext to set
     */
    public void setShowNext(boolean showNext) {
      this.showNext = showNext;
    }

    /**
     * @return the showLast
     */
    public boolean isShowLast() {
      return showLast;
    }

    /**
     * @param showLast the showLast to set
     */
    public void setShowLast(boolean showLast) {
      this.showLast = showLast;
    }

    public String getInitPage() {
  		this.getInit();
  		return initPage;
  	}

  	public void setInitPage(String initPage) {
  		this.initPage = initPage;
  	}	
  	
  	public void processResult() {      
  	  int totalQuestion  = 0;
  	  int tCorrectAns  = 0;
  	  try {
        if ( this.tutorials != null ) {
          TestResult testResult = new TestResult();
          Set<TestResultDetails> testDetailsList = new HashSet<TestResultDetails>();
          for(TestTutorialQuestionAns tQuestionAns : this.tutorials) {            
            if(tQuestionAns.getQuestion() != null && !tQuestionAns.getQuestion().equals("")){
              totalQuestion++;
              TestResultDetails testDetails = new TestResultDetails();
              if ((tQuestionAns.getQuestionCorrectAnswer() != null && tQuestionAns.getExaminerAns()!=null)
                  && (tQuestionAns.getQuestionCorrectAnswer().equals(tQuestionAns.getExaminerAns()))) {
                testDetails.setIsCorrect(true);
                tCorrectAns++;
              } else {
                testDetails.setIsCorrect(false);
              }
              testDetails.setRecordCreatorId(this.getMember().getMemberId()+"");
              testDetails.setRecordCreateDate(new Date());
              testDetails.setLastUpdatedDate(new Date());
              testDetails.setRecordUpdatorId(this.getMember().getMemberId()+"");
              testDetails.setMemberAns(tQuestionAns.getExaminerAns());
              testDetails.setQuestionId(tQuestionAns.getQuestionAnsId());              
              testDetails.setTestResult(testResult);
              testDetailsList.add(testDetails);
            }
          }          
          testResult.setModuleId(module.getModulesId());
          testResult.setDocumentId(module.getDocumentId());
          testResult.setMemberId(this.getMember().getMemberId());
          testResult.setStartTime(this.getExamStartDate());
          testResult.setEndTime(new Date());
          testResult.setTotalQuestions(new Long(totalQuestion));
          testResult.setTotalCorrect(new Long(tCorrectAns));
          testResult.setIsPassed(true);
          testResult.setRecordCreatorId(this.getMember().getMemberId()+"");
          testResult.setRecordCreateDate(new Date());
          testResult.setLastUpdatedDate(new Date());
          testResult.setRecordUpdatorId(this.getMember().getMemberId()+"");
          testResult.setTestResultDetails(testDetailsList);
          this.testService.save(testResult);
          this.noOfQuestion = totalQuestion;
          this.noOfCorrectAns = tCorrectAns;
          this.perOfMarks = tCorrectAns * 100 / totalQuestion; 
          
          Integer passMarks = 60;
          if (this.getText("tutorial_pass_marks") != null && !this.getText("tutorial_pass_marks").isEmpty()) {
            passMarks = Integer.parseInt(this.getText("tutorial_pass_marks"));
          }
          this.passStatus = this.getText("tutorial_fail_status");
          if ( this.perOfMarks >= passMarks ) this.passStatus = this.getText("tutorial_pass_status");;
          sendNotification(this.getMember().getEmail(),
              this.getText("tutorial_test_email_sub"),
              this.getText("tutorial_test_email_body",
                  new String[]{this.getMember().getFirstName(),""+perOfMarks}));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  	}
  	
  	 /** Send confirmation email to member */
    public void sendNotification(String email, String emailSubject,String msgBody)throws Exception {
      /**Send email notification */
      try {
        Emailer emailer = new Emailer(email, msgBody,emailSubject);
        emailer.setContentType("text/html");
        emailer.sendEmail();
      } catch (Exception e) {
        log.debug("Failed to sending notification email");
        e.printStackTrace();
      }
    }
  	public void XsaveTestResult() {  		
  		try{
  			TestResult test = new TestResult();
  			test.setModuleId(module.getModulesId());
  			test.setDocumentId(module.getDocumentId());
  			test.setMemberId(this.getMember().getMemberId());
  			test.setStartTime(new Date());
  			test.setEndTime(new Date());
  			test.setTotalQuestions(10L);
  			test.setTotalCorrect(7L);
  			test.setIsPassed(true);
  			test.setRecordCreatorId(this.getMember().getMemberId()+"");
  			test.setRecordCreateDate(new Date());
  			test.setLastUpdatedDate(new Date());
  			test.setRecordUpdatorId(this.getMember().getMemberId()+"");
  			
  			TestResultDetails details1 = new TestResultDetails();
  			details1.setIsCorrect(true);
  			details1.setRecordCreatorId(this.getMember().getMemberId()+"");
  			details1.setRecordCreateDate(new Date());
  			details1.setLastUpdatedDate(new Date());
  			details1.setRecordUpdatorId(this.getMember().getMemberId()+"");
  			details1.setMemberAns("A");
  			details1.setQuestionId(1L);
  			details1.setTestResult(test);
  			Set<TestResultDetails> res = new HashSet<TestResultDetails>();
  			res.add(details1);
  			test.setTestResultDetails(res);
  			
  			this.testService.save(test);
  		} catch(Exception ex){
  			log.error(ex.getMessage());
  			ex.printStackTrace();
  		}
  	}

  	public void renderPage(){
  		try{
  			this.decideQuestion();
  			mediaBean.dispose();
  			mediaBean = new MediaBean(this.currentPage.getPageAudio());
  			mediaBean.play();
  			mediaBean.setPageContent(this.currentPage.getPageText());
  		}
  		catch(Exception ex){
  			ex.printStackTrace();
  		}
  	}
	public void playPreviousPage(){
		try{
			//log.debug(this.hasQuestionAns);
			log.debug("Playing previous page");
			if(pageIndex > 0){
				pageIndex -= 1;
	  			log.debug("index:"+pageIndex);
				this.currentPage = this.tutorials.get(pageIndex);
				this.renderPage();
			}
		}
		catch(Exception ex){
			
		}
		log.debug("index:"+pageIndex);
	}
	public void playFirstPage(){
		try{
			pageIndex  = 0;
  			this.currentPage = this.tutorials.get(pageIndex);
			this.renderPage();	
		}
		catch(Exception ex){
			
		}
	}	
	
	public void playNextPage(){
		try{
			if(pageIndex < tutorials.size()-1){
				pageIndex += 1;
				log.debug("index:"+pageIndex);
	  		this.currentPage = this.tutorials.get(pageIndex);
				this.renderPage();
			} else {
			  if (this.getMember()!=null && this.getMember().getTypeId().equals(CommonConstants.MENTOR)) {
			    this.notEndExam = false;
			    this.showNext = false;
			    this.processResult();	        
			  }
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		log.debug("index:"+pageIndex);
	}
	public void playLastPage(){
		try{
			pageIndex  = tutorials.size()-1;
  			this.currentPage = this.tutorials.get(pageIndex);
			this.renderPage();			
		}
		catch(Exception ex){
			
		}
	}	
  	private void playModuleIntroduction(){
  		try{
  			mediaBean = new MediaBean(this.getModule().getModuleIntroAudio());
  			mediaBean.play();
  			mediaBean.setPageContent(this.getModule().getModuleText());
			this.setCurrentPage(null);
  		}
  		catch(Exception ex){
  			ex.printStackTrace();
  		}
  	}
  	public void playModule(){
  		try{
  			if(this.pageIndex == -1){
  				this.playModuleIntroduction();
  			}
  			
  		}
  		catch(Exception ex){
  			ex.printStackTrace();
  		}
  	}
	public void getInit(){
	  this.setExamStartDate(new Date());
	  if (this.getMember()!=null && this.getMember().getTypeId().equals(CommonConstants.PROTEGE)) {
      this.disabledNext = true;
      this.disabledLast = true;
    }
		String moduleId = this.getRequest().getParameter("moduleId");
		if(moduleId == null || moduleId.equals("")){
			return;
		}		
		try {			
		  this.showFirst = true;
	    this.showPrevious = true;
	    this.showStop = true;
	    this.showNext = true;
	    this.showLast = true;
	    this.notEndExam = true;
		  module =  this.modulesService.loadById(Long.parseLong(moduleId));
			tutorials = this.questionService.getTutorialByModule(module.getModulesId());
			this.pageIndex = -1;
			this.hasQuestionAns = false;
			this.playModuleIntroduction();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public TestTutorialModules getModule() {
		return module;
	}

	public void setModule(TestTutorialModules module) {
		this.module = module;
	}

	public TestTutorialQuestionAns getCurrentPage() {
		return currentPage;
	}
	

	public void setCurrentPage(TestTutorialQuestionAns currentPage) {
		this.currentPage = currentPage;
	}
	

	public ModulesService getModulesService() {
		return modulesService;
	}
	@Autowired
	public void setModulesService(ModulesService modulesService) {
		this.modulesService = modulesService;
	}


	public String getModulePlay() {
		this.playModuleIntroduction();
		return "";
	}
	public void setModulePlay(String modulePlay) {
		this.modulePlay = modulePlay;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	public MediaBean getMediaBean() {
		return mediaBean;
	}


	public void setMediaBean(MediaBean mediaBean) {
		this.mediaBean = mediaBean;
	}

	public QuestionAnsService getQuestionService() {
		return questionService;
	}
	
	@Autowired
	public void setQuestionService(QuestionAnsService questionService) {
		this.questionService = questionService;
	}
	public Boolean getHasQuestionAns() {
		return hasQuestionAns;
	}
	public void setHasQuestionAns(Boolean hasQuestionAns) {
		this.hasQuestionAns = hasQuestionAns;
	}

	public void XdecideQuestion() {
		this.hasQuestionAns = true;
		if(this.currentPage == null){
			this.hasQuestionAns = false;
		}
		if(this.getCurrentPage().getQuestion() ==null || this.currentPage.getQuestion().equals("")){
			this.hasQuestionAns = false;
		}
		log.debug(this.hasQuestionAns);
	}
	
	public void decideQuestion() {
    this.hasQuestionAns = true;
    if((this.currentPage == null) || 
        (this.getCurrentPage().getQuestion() ==null || this.currentPage.getQuestion().equals(""))){
      this.hasQuestionAns = false;
    } else {
      if ( this.showFirst ) {
        this.showFirst = false;
        this.showPrevious = false;
        this.showStop = false;
        //this.showNext = true;
        this.showLast = false;        
      }
    }
    log.debug(this.hasQuestionAns);
  }
	/*
	public void setHasQuestionAns(Boolean hasQuestionAns) {
		this.hasQuestionAns = hasQuestionAns;
	}	*/

	public TestResultService getTestService() {
		return testService;
	}
	
	@Autowired
	public void setTestService(TestResultService testService) {
		this.testService = testService;
	}

	public TestResultDetailsService getTestDetailsService() {
		return testDetailsService;
	}
	
	@Autowired
	public void setTestDetailsService(TestResultDetailsService testDetailsService) {
		this.testDetailsService = testDetailsService;
	}

  /**
   * @return the examStartDate
   */
  public Date getExamStartDate() {
    return examStartDate;
  }

  /**
   * @param examStartDate the examStartDate to set
   */
  public void setExamStartDate(Date examStartDate) {
    this.examStartDate = examStartDate;
  }


}
