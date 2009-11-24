package com.intrigueit.myc2i.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;
import com.intrigueit.myc2i.tutorial.service.ModulesService;
import com.intrigueit.myc2i.tutorial.service.QuestionAnsService;

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
			//log.debug(this.getHasQuestionAns());
			if(pageIndex < tutorials.size()-1){
				pageIndex += 1;
				log.debug("index:"+pageIndex);
	  			this.currentPage = this.tutorials.get(pageIndex);
	  			//log.debug(this.currentPage.getPageText());
				this.renderPage();
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
	public void init(){
		String moduleId = this.getRequest().getParameter("moduleId");
		if(moduleId == null || moduleId.equals("")){
			return;
		}
		try{
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

	public void decideQuestion() {
		this.hasQuestionAns = true;
		if(this.currentPage == null){
			this.hasQuestionAns = false;
		}
		if(this.getCurrentPage().getQuestion() ==null || this.currentPage.getQuestion().equals("")){
			this.hasQuestionAns = false;
		}
		log.debug(this.hasQuestionAns);
	}
	/*
	public void setHasQuestionAns(Boolean hasQuestionAns) {
		this.hasQuestionAns = hasQuestionAns;
	}	*/
	
}
