package com.intrigueit.myc2i.media;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


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
	public static final String DEFAULT_FILE_LOCATION = "/images/upload/";
	
  	public  String writeToFile(String fileName, byte data[])	throws IOException {
		String me = "FileUtils.WriteToFile";

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String imagePath = servletContext.getRealPath("");
		String filePath = imagePath +""+DEFAULT_FILE_LOCATION+fileName;
		System.out.println(filePath);
		File theFile = new File(filePath);

		// Check if a file exists.
		if (theFile.exists()) {
			String msg = theFile.isDirectory() ? "directory" : (!theFile.canWrite() ? "not writable" : null);
			if (msg != null) {
				throw new IOException(me + ": file '" + fileName + "' is " + msg);
			}
		}

		// Create directory for the file, if requested.
		if (theFile.getParentFile() != null) {
			theFile.getParentFile().mkdirs();
		}

		BufferedOutputStream fOut = null;
		try	{
			fOut = new BufferedOutputStream(new FileOutputStream(theFile));
			fOut.write(data);
		} catch (Exception e) {
			throw new IOException(me + " failed, got: " + e.toString());
		} finally {
			fOut.close();
		}
		return filePath;
	}

  	public void renderPage(){
  		try{
  			mediaBean.dispose();
  			mediaBean = new MediaBean(this.currentPage.getPageAudio());
  			mediaBean.play();
  			mediaBean.setPageContent("<h1 style=\"text-align: center;\"><strong>"+ this.currentPage.getPageText() +"</strong></h1><ul><li><h2>Seam text support out of the box using built-in converter</h2></li><li><h2>RichFaces skinnability</h2></li><li><h2>Implementation sm</h2></li></ul>");
  			mediaBean.setPageContent(this.currentPage.getPageText());
  			log.debug(this.mediaBean.getPageContent());

  		}
  		catch(Exception ex){
  			ex.printStackTrace();
  		}
  	}
	public void playPreviousPage(){
		try{
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
			}
		}
		catch(Exception ex){
			
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
  			mediaBean.setPageContent("<h1 style=\"text-align: center;\"><strong>Introduction to Da'wah</strong></h1><ul><li><h2>Seam text support out of the box using built-in converter</h2></li><li><h2>RichFaces skinnability</h2></li><li><h2>Implementation of manageable configurations mechanism</h2></li></ul>");
  		}
  		catch(Exception ex){
  			ex.printStackTrace();
  		}
  	}
  	public void playModule(){
  		try{
  			this.playModuleIntroduction();
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
		}
		catch(Exception ex){
			
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
	
}
