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
    List<TestTutorialQuestionAns> categoryList = questionAnsDao.loadAll();
    return categoryList;
  }

  public TestTutorialQuestionAns loadById(Long recordId) {
    return questionAnsDao.loadById(recordId);
  }

  public void updateQuestionAns(TestTutorialQuestionAns tutorialQA) {
    questionAnsDao.update(tutorialQA);
  }

  public void addQuestionAns(TestTutorialQuestionAns tutorialQA) {
    questionAnsDao.persist(tutorialQA);
  }

  public void deleteQuestionAns(Long recordId) {
    TestTutorialQuestionAns tutorialQA = questionAnsDao.loadById(recordId);
    questionAnsDao.delete(tutorialQA);
  }

  public ArrayList<String> getCategories() {
    return questionAnsDao.getCategories();
  }

  public List<TestTutorialQuestionAns> findByProperty(String propertyName,
      Object value) {
    String clause = " t." + propertyName + " like ?1 ";
    return questionAnsDao.loadByClause(clause, new Object[] { value });
  }

  public List<TestTutorialQuestionAns> findByProperties(String moduleId) {
    StringBuffer clause = new StringBuffer();
    clause
        .append(
            "SELECT NEW TestTutorialQuestionAns(tq.questionAnsId,tq.pageNumber,tq.pageTitle)")
        .append(" FROM TestTutorialQuestionAns tq");

    if (moduleId != null && !moduleId.isEmpty()) {
      clause.append(" where tq.modulesId =" + moduleId);
    }
    return questionAnsDao.findByProperties(clause.toString());
  }

  public Long getMaxPageNo(Long moduleId) {
    String hsql = "select max(pageNumber) from TestTutorialQuestionAns where modulesId ="
        + moduleId;
    return questionAnsDao.getMaxNumber(hsql);
  }

  @Override
  public boolean isQuestionExist(Long recordId, Long moduleId, String question) {
    List<Object> value = new ArrayList<Object>();
    StringBuffer clause = new StringBuffer();
    clause.append(" t.modulesId = ?1").append(" and t.question = ?2");
    value.add(moduleId);
    value.add(question);
    if (recordId != null && recordId != 0) {
      clause.append(" and t.questionAnsId != ?3");
      value.add(recordId);
    }
    return questionAnsDao.isDuplicateRecord(clause.toString(), value.toArray());
  }

  @Override
  public boolean isPageNoExist(Long recordId, Long moduleId, Long pageNo) {
    List<Object> value = new ArrayList<Object>();
    StringBuffer clause = new StringBuffer();
    clause.append(" t.modulesId = ?1").append(" and t.pageNumber = ?2");
    value.add(moduleId);
    value.add(pageNo);
    if (recordId != null && recordId != 0) {
      clause.append(" and t.questionAnsId != ?3");
      value.add(recordId);
    }
    return questionAnsDao.isDuplicateRecord(clause.toString(), value.toArray());
  }

  @Override
  public List<TestTutorialQuestionAns> getTutorialByModule(Long mdouleId) {
    String clause = " t.modulesId" + " =?1 order by pageNumber asc";
    return questionAnsDao.loadByClause(clause, new Object[] { mdouleId });
  }
}
