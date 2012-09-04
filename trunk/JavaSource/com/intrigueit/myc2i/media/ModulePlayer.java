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
import com.intrigueit.myc2i.member.view.ProtegeProfileViewHandler;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;
import com.intrigueit.myc2i.tutorial.service.ModulesService;
import com.intrigueit.myc2i.tutorial.service.QuestionAnsService;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;
import com.intrigueit.myc2i.tutorialtest.domain.TestResultDetails;
import com.intrigueit.myc2i.tutorialtest.service.TestResultService;

/**
 * @author Shamim Ahmmed
 * 
 */
@Component("modulePlayer")
@Scope("session")
public class ModulePlayer extends BasePage {

	private TestTutorialModules module;
	private TestTutorialQuestionAns currentPage;
	private List<TestTutorialQuestionAns> tutorials;
	private ModulesService modulesService;
	private QuestionAnsService questionService;

	private static int PERCENT_MULTIPLIER = 100;
	private static int DEFAULT_PASS_MARKS = 60;

	/** Flash slide path */
	private String flashSlidePath;

	private int pageIndex = -1;
	private String pageContent;
	private Boolean hasQuestionAns;

	private TestResultService testService;

	private String initPage;
	private boolean disabledNext;
	private boolean disabledLast;
	private boolean notEndPlay = true;
	private Integer noOfQuestion;
	private Integer noOfCorrectAns;
	private String passStatus;
	private Integer perOfMarks;
	private String currentAction = "";
	private Integer lastViewedPageIndex = 0;
	
	private List<Integer> questionsIndexList;
	private Integer questionParticipated = 0;
	private Integer tutorialLastIndex;

	private TestResult testResult;
	
	@Autowired
	ProtegeProfileViewHandler protegeProfileViewHandler;
	

	/**
	 * @return the lastViewedPageIndex
	 */
	public Integer getLastViewedPageIndex() {
		return lastViewedPageIndex;
	}

	/**
	 * @param lastViewedPageIndex
	 *            the lastViewedPageIndex to set
	 */
	public void setLastViewedPageIndex(Integer lastViewedPageIndex) {
		this.lastViewedPageIndex = lastViewedPageIndex;
	}

	public String getInitPage() {
		this.getInit();
		return initPage;
	}

	public void setInitPage(String initPage) {
		this.initPage = initPage;
	}

	private TestResultDetails createTestResultDetails(
			TestTutorialQuestionAns tQuestionAns) {

		TestResultDetails testDetails = new TestResultDetails();
		if (this.isValidAnswer(tQuestionAns)) {
			testDetails.setIsCorrect(true);
		} else {
			testDetails.setIsCorrect(false);
		}
		testDetails.setRecordCreatorId(this.getMember().getMemberId() + "");
		testDetails.setRecordCreateDate(new Date());
		testDetails.setLastUpdatedDate(new Date());
		testDetails.setRecordUpdatorId(this.getMember().getMemberId() + "");
		testDetails.setMentorAns(tQuestionAns.getExamineeAns());
		testDetails.setQuestionId(tQuestionAns.getQuestionAnsId());

		return testDetails;
	}

	private TestResultDetails updateTestResultDetails(
			TestResultDetails testDetails, TestTutorialQuestionAns tQuestionAns) {

		if (this.isValidAnswer(tQuestionAns)) {
			testDetails.setIsCorrect(true);
		} else {
			testDetails.setIsCorrect(false);
		}
		testDetails.setLastUpdatedDate(new Date());
		testDetails.setRecordUpdatorId(this.getMember().getMemberId() + "");
		testDetails.setMentorAns(tQuestionAns.getExamineeAns());

		return testDetails;
	}

	/**
	 * Check if the record has any question answer.
	 * 
	 * @param tQuestionAns
	 * @return
	 */
	private boolean hasQuestion(TestTutorialQuestionAns tQuestionAns) {
		return PlayerHelper.hasQuestion(tQuestionAns);
	}

	/**
	 * Check if the question answer is valid or not
	 * 
	 * @param tQuestionAns
	 * @return
	 */
	private boolean isValidAnswer(TestTutorialQuestionAns tQuestionAns) {

		//log.debug(""+tQuestionAns.getQuestionAnsId()+ " right ans:" + tQuestionAns.getQuestionCorrectAnswer()				+ "  user answer: " + tQuestionAns.getExamineeAns());

		if ((tQuestionAns.getQuestionCorrectAnswer() != null && tQuestionAns
				.getExamineeAns() != null)
				&& (tQuestionAns.getQuestionCorrectAnswer().equals(tQuestionAns
						.getExamineeAns()))) {
			return true;
		}
		return false;
	}

	public String getQuestionsProgress(){
		
		String status = "";
		status = questionParticipated+"/"+ questionsIndexList.size();
		
		log.debug(status);
		
		return status;
	}
	private boolean isCompletedTest() {
		try {
			TestResult result = getUserModuleTestResult(this.getMember()
					.getMemberId(), this.module.getModulesId());
			
			/** No question answer */
			if(result == null || result.getTestResultDetails() == null || result.getTestResultDetails().size()== 0){
				return false;
			}
			
			int totalCorrectAnswer = this.getTotalCorrectAnswer(result);
			int totalQuestions = questionsIndexList.size();//result.getTestResultDetails().size();
			int percentOfCorrect = this.getExamPercenetOfMark(result);

			int passMark = DEFAULT_PASS_MARKS;
			String confPassMark = this.getText("tutorial_pass_marks");
			try {
				passMark = Integer.parseInt(confPassMark);
			} catch (NumberFormatException nfEx) {
				log
						.error("Error occured during loading minimum pass mark from resources file. "
								+ nfEx.getMessage());
				log
						.debug("Default pass mark will be using. System default pass mark is:"
								+ DEFAULT_PASS_MARKS);
			}

			boolean isPassed = false;
			if (percentOfCorrect >= passMark) {
				isPassed = true;
			}

			result.setLastAccessPage(0L);

			/** Updating the test result */
			result.setIsCompleted(isPassed);
			result.setIsPassed(isPassed);
			result.setEndTime(new Date());
			result.setTotalQuestions(Long.parseLong(totalQuestions + ""));
			result.setTotalCorrect(Long.parseLong(totalCorrectAnswer + ""));

			this.testService.update(result);

			this.updateUIDate(totalQuestions, totalCorrectAnswer,
					percentOfCorrect);

			/** Preparing message content */
			passStatus = (isPassed) ? this.getText("tutorial_pass_status")
					: this.getText("tutorial_fail_status");
			String msgSub = this.getText("tutorial_test_email_sub",
					new String[] { "" + module.getModuleName() + "" });
			

			String msgBody = this.getText("tutorial_test_email_body",
					new String[] {"" + totalQuestions,	"" + totalCorrectAnswer, "" + percentOfCorrect,	"" + passStatus });

			/** Send email message */
			this.sendNotification(this.getMember().getEmail(), msgSub, msgBody, this.getMemberName());

		} catch (Exception ex) {
			log.error("Error occured during creating test result. "
					+ ex.getMessage());
		}
		return true;
	}

	private void updateUIDate(int noOfQuestion, int correctAns, int percentMarks) {
		this.noOfQuestion = noOfQuestion;
		this.noOfCorrectAns = correctAns;
		this.perOfMarks = percentMarks;
	}

	private int getTotalCorrectAnswer(TestResult result) {
		int correctAnswer = 0;
		for (TestResultDetails detail : result.getTestResultDetails()) {
			if (detail.getIsCorrect()) {
				correctAnswer = correctAnswer + 1;
			}
		}
		return correctAnswer;
	}

	private int getExamPercenetOfMark(TestResult result)
			throws ArithmeticException {

		int totalQuestions = 0;
		int totalCorrectAnswer = 0;
		int percent = 0;

		totalQuestions = result.getTestResultDetails().size();
		if (totalQuestions < 1) {
			return 0;
		}
		totalCorrectAnswer = this.getTotalCorrectAnswer(result);
		percent = (totalCorrectAnswer * PERCENT_MULTIPLIER) / totalQuestions;

		return percent;
	}

	/** Send confirmation email to member */
	private void sendNotification(String email, String emailSubject,
			String msgBody,String name) throws Exception {
		/** Send email notification */
		try {

			
			this.sendEmail(email, emailSubject, msgBody, name);
			
		} catch (Exception e) {
			log.debug("Failed to sending notification email");
			log.error(e.getMessage(), e);
		}
	}

	public void renderPage() {
		try {
			this.decideQuestion();

			this.setFlashSlidePath(this.currentPage.getAudioFileName());

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public void playPreviousPage() {
		this.currentAction = CommonConstants.PREVIOUS;
		try {
			this.notEndPlay = true;
			//log.debug("Playing previous page");
			if (pageIndex > 0) {
				pageIndex -= 1;
				//log.debug("index:" + pageIndex);
				this.currentPage = this.tutorials.get(pageIndex);
				this.renderPage();

				/** Save the current page status */
				this.saveCurrentStage();
			}
		} catch (Exception ex) {
			log.error("Error occur while playing previous page: "
					+ ex.getMessage());
			ex.getStackTrace();
		}
		//log.debug("index:" + pageIndex);
	}

	public void playFirstPage() {
		this.notEndPlay = true;
		this.currentAction = CommonConstants.FIRST;
		try {
			pageIndex = 0;
			this.currentPage = this.tutorials.get(pageIndex);
			this.renderPage();
		} catch (Exception ex) {

		}
	}

	public void playNextPage() {
		this.currentAction = CommonConstants.NEXT;
		try {
			
			//log.debug("Last: "+tutorialLastIndex);
			
			/** Play only tutorial not question answer */
			if (pageIndex < tutorialLastIndex) {
				log.debug("playing slide : "+pageIndex);
				/** Saving question answer before going to next page */
				this.saveQuestionAnswer();

				pageIndex = pageIndex + 1;

				if (lastViewedPageIndex <= pageIndex) {
					lastViewedPageIndex = pageIndex;
				}
				//log.debug("index:" + pageIndex);
					
				this.currentPage = this.tutorials.get(pageIndex);
				

				
				this.renderPage();

				/** Saving last access page */
				this.saveCurrentStage();

			}
			else if(pageIndex >= tutorialLastIndex && questionParticipated < questionsIndexList.size()){
				//log.debug("playing question : "+questionParticipated);
				// if current page is question answer, then select any random question which is not provided earlier
				// and count the question which always less then module max question no

				/** Saving question answer before going to next page */
				this.saveQuestionAnswer();
				
				this.pageIndex = questionsIndexList.get(questionParticipated);
				
				this.currentPage = this.tutorials.get(pageIndex);
				
				this.renderPage();
				
				questionParticipated ++;
				
				/** Saving last access page */
				this.saveCurrentStage();
			}
			/**
			 * User reached last page, now calculate the question and answer
			 * section
			 */
			else {
				log.debug("exam end : "+questionParticipated);
				this.notEndPlay = false;
				
				/** save the last page question before calculating question answer  */
				this.saveQuestionAnswer();
				
				boolean isCompleted = this.isCompletedTest();
				
				/** No question answer just say tutorial is completed */
				if(!isCompleted){
					this.pageIndex  = 0;
					this.saveCurrentStage();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//log.debug("index:" + pageIndex);
	}

	public void playLastPage() {
		this.currentAction = CommonConstants.LAST;
		try {
			// pageIndex = tutorials.size()-1;
			if (pageIndex != -1)
				pageIndex = this.lastViewedPageIndex;
			this.currentPage = this.tutorials.get(pageIndex);
			this.renderPage();
		} catch (Exception ex) {

		}
	}

	/**
	 * Create a new Test result object
	 * 
	 * @return a new TestResult object
	 */
	private TestResult createNewTestResult() {

		TestResult result = new TestResult();
		result.setDocumentId(this.module.getDocumentId());
		result.setLastUpdatedDate(new Date());
		result.setMemberId(this.getMember().getMemberId());
		result.setModuleId(this.module.getModulesId());
		result.setRecordCreateDate(new Date());
		result.setRecordCreatorId(this.getMember().getMemberId().toString());
		result.setRecordUpdatorId(this.getMember().getMemberId().toString());
		result.setIsCompleted(false);
		result.setIsPassed(false);
		result.setStartTime(new Date());

		return result;
	}

	private TestResultDetails getTestResultDetails(Long questionId,
			Set<TestResultDetails> details) {
		for (TestResultDetails detail : details) {
			if (detail.getQuestionId().equals(questionId)) {
				return detail;
			}
		}
		return null;
	}

	/**
	 * Save question answer details after each question
	 */
	private void saveQuestionAnswer() {
		try {
			TestResult result = getUserModuleTestResult(this.getMember()
					.getMemberId(), this.module.getModulesId());

			/** Adding question answer */
			if (this.hasQuestion(this.currentPage)) {
				Set<TestResultDetails> testDetailsList = result.getTestResultDetails();
				if (testDetailsList == null) {

					testDetailsList = new HashSet<TestResultDetails>();
					result.setTestResultDetails(testDetailsList);

					/**
					 * Question answer section started , need to set test start
					 * date
					 */
					result.setStartTime(new Date());
				}
				TestResultDetails detail = this.getTestResultDetails(this.currentPage.getQuestionAnsId(), result.getTestResultDetails());
				if (detail == null) {
					detail = createTestResultDetails(this.currentPage);
					detail.setTestResult(result);
					result.getTestResultDetails().add(detail);
				} else {
					this.updateTestResultDetails(detail, this.currentPage);
				}

				this.testService.update(result);
			}

		} catch (Exception ex) {
			log.error("Error occur during saving question answer: "
					+ ex.getMessage());
			//ex.printStackTrace();
		}
	}

	/**
	 * Save the current stage of the module
	 */
	private void saveCurrentStage() {
		try {

			TestResult result = getUserModuleTestResult(this.getMember()
					.getMemberId(), this.module.getModulesId());
			result.setLastAccessPage(Long.parseLong(this.pageIndex + ""));
			if(!this.notEndPlay){
				result.setIsCompleted(true);
			}
			this.testService.update(result);

			//log.debug(result.getLastAccessPage());
		} catch (Exception ex) {
			log.error(" Error occur while storing tutorial current state: "
					+ ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Load test result from based the parameter provided
	 * 
	 * @param userId
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	private TestResult getUserModuleTestResult(Long userId, Long moduleId)
			throws Exception {
		TestResult result = this.testService.loadUserModuleResult(this
				.getMember().getMemberId(), this.module.getModulesId());
		if (result == null) {
			result = this.createNewTestResult();
			this.testService.save(result);
		}
		return result;
	}

	public void getInit() {
		this.enableDisabledBtn();
		String moduleId = this.getRequest().getParameter("moduleId");
		if (moduleId == null || moduleId.equals("")) {
			return;
		}
		try {

			this.notEndPlay = true;
			module = this.modulesService.loadById(Long.parseLong(moduleId));
			tutorials = this.questionService.getTutorialByModule(module
					.getModulesId());

			this.pageIndex = getLastPageIndex();
			
			Integer moduleQuestionSize = module.getModuleTestQuestions();
			if(moduleQuestionSize== null){
				moduleQuestionSize = 10; //default
			}
			Integer questionEnd = tutorials.size() -1;
			int questionStart = PlayerHelper.getModuleQuestionStartIndex(tutorials);
			questionParticipated = 0;
			
			log.debug(questionStart + " "+ questionEnd + " "+ moduleQuestionSize);
			questionsIndexList = PlayerHelper.createRandomQuestionList(questionStart, questionEnd, moduleQuestionSize);
			
			if(this.pageIndex > questionStart){
				this.pageIndex = questionsIndexList.get(0);
				questionParticipated = 1;
			}
			
			
			tutorialLastIndex = questionStart-1  ;
			
			//log.debug(questionStart + " "+ questionEnd + " "+ moduleQuestionSize);
/*			for(Integer i: questionsIndexList){
				log.debug(i +" "+ tutorials.get(i).getQuestionAnsId() +" "+ tutorials.get(i).getQuestionCorrectAnswer());
			}*/
			this.hasQuestionAns = false;

			this.currentPage = this.tutorials.get(pageIndex);
			
			//this.setCurrentQuestionPage();
			
			this.renderPage();
			
			
			/** Clean previous result details */
			log.debug("Cleaning previous result details"+this.getMember().getMemberId()+" "+ this.module.getModulesId() );
			
			TestResult result = getUserModuleTestResult(this.getMember()
					.getMemberId(), this.module.getModulesId());

			
			this.testService.deleteResultDetails(result.getTutorialTestId());

		} catch (Exception ex) {
			//ex.printStackTrace();
			log.error(ex.getMessage(), ex);
		}
	}

	/**
	 * @return the persisted last page index
	 */
	private int getLastPageIndex() {
		try {
			TestResult result = this.getUserModuleTestResult(this.getMember()
					.getMemberId(), this.module.getModulesId());

			if (result != null) {
				return Integer.parseInt(result.getLastAccessPage() + "");
			}
		} catch (Exception ex) {
			log.error("Error while getting last page access index "
					+ ex.getMessage());
		}
		/** If no index save then start from 0 */
		return 0;
	}

	public void enableDisabledBtn() {
		if ((this.getMember() != null && this.getMember().getTypeId().equals(
				CommonConstants.PROTEGE))
				&& (this.currentAction.equals("") || this.currentAction
						.equals(CommonConstants.NEXT)) && (this.notEndPlay)) {
			this.disabledNext = true;
			this.disabledLast = true;
		} else {
			this.disabledNext = false;
			this.disabledLast = false;
		}
	}

	/**
	 * @return the currentAction
	 */
	public String getCurrentAction() {
		return currentAction;
	}

	/**
	 * @param currentAction
	 *            the currentAction to set
	 */
	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	/**
	 * @return the disabledLast
	 */
	public boolean isDisabledLast() {
		return disabledLast;
	}

	/**
	 * @param disabledLast
	 *            the disabledLast to set
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
	 * @param noOfQuestion
	 *            the noOfQuestion to set
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
	 * @param noOfCorrectAns
	 *            the noOfCorrectAns to set
	 */
	public void setNoOfCorrectAns(Integer noOfCorrectAns) {
		this.noOfCorrectAns = noOfCorrectAns;
	}

	/**
	 * @return the perOfMarks
	 */
	public Integer getPerOfMarks() {
		return perOfMarks;
	}

	/**
	 * @param perOfMarks
	 *            the perOfMarks to set
	 */
	public void setPerOfMarks(Integer perOfMarks) {
		this.perOfMarks = perOfMarks;
	}

	/**
	 * @return the notEndExam
	 */
	public boolean isNotEndPlay() {
		return notEndPlay;
	}

	/**
	 * @param notEndPlay
	 *            the notEndPlay to set
	 */
	public void setNotEndPlay(boolean notEndPlay) {
		this.notEndPlay = notEndPlay;
	}

	/**
	 * @return the disabledNext
	 */
	public boolean isDisabledNext() {
		return disabledNext;
	}

	/**
	 * @param disabledNext
	 *            the disabledNext to set
	 */
	public void setDisabledNext(boolean disabledNext) {
		this.disabledNext = disabledNext;
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

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
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

	public void decideQuestion() {
		this.hasQuestionAns = true;
		if ((this.currentPage == null)
				|| (this.getCurrentPage().getQuestion() == null || this.currentPage
						.getQuestion().equals(""))) {
			this.hasQuestionAns = false;
		}
		//log.debug(this.hasQuestionAns);
	}

	public TestResultService getTestService() {
		return testService;
	}

	@Autowired
	public void setTestService(TestResultService testService) {
		this.testService = testService;
	}

	public String getFlashSlidePath() {
		return flashSlidePath;
	}

	public void setFlashSlidePath(String flashSlidePath) {
		this.flashSlidePath = flashSlidePath;
	}

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public String getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}

}
