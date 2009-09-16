package com.intrigueit.myc2i.tutorial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.tutorial.dao.QuestionAnsDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;

@Service
public class QuesAnsServiceImpl implements QuestionAnsService {

	private QuestionAnsDao questionAnsDao;
	
	@Autowired
	public QuesAnsServiceImpl(QuestionAnsDao questionAnsDao) {
		this.questionAnsDao = questionAnsDao;
	}

	public List<TestTutorialQuestionAns> loadAll() {
		List<TestTutorialQuestionAns> categoryList= questionAnsDao.loadAll();
		return categoryList;
	}
	
	public TestTutorialQuestionAns loadById() {
		//return questionAnsDao.loadById(recordId);
		return null;
	}
	
	public TestTutorialQuestionAns loadById(Long recordId) {
		return questionAnsDao.loadById(recordId);
	}
	public void updateQuestionAns (TestTutorialQuestionAns tutorialQA) {
		questionAnsDao.update(tutorialQA);
	}
	public void addQuestionAns (TestTutorialQuestionAns tutorialQA) {
		questionAnsDao.persist(tutorialQA);
	}

	public void deleteQuestionAns(Long recordId) {
		TestTutorialQuestionAns tutorialQA = questionAnsDao.loadById(recordId);
		questionAnsDao.delete(tutorialQA);		
	}
	
	public ArrayList<String> getCategories() {   
    return questionAnsDao.getCategories();
  }
  public List<TestTutorialQuestionAns> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return questionAnsDao.loadByClause(clause, new Object[]{value});
  }
  public List<TestTutorialQuestionAns> findByProperties(String moduleId) {
    StringBuffer clause = new StringBuffer();
    clause.append("SELECT NEW TestTutorialQuestionAns(tq.questionAnsId,tq.pageNumber,tq.pageText)")
    .append(" FROM TestTutorialQuestionAns tq") ;
    
    if (moduleId != null && !moduleId.isEmpty()) {
      clause.append(" where tq.modulesId ="+moduleId);
    }   
    return questionAnsDao.findByProperties(clause.toString());
  }
  public Long getMaxPageNo( Long moduleId ) {
    String hsql = "select max(pageNumber) from TestTutorialQuestionAns where modulesId ="+moduleId;
    return questionAnsDao.getMaxNumber(hsql);
  }
}
