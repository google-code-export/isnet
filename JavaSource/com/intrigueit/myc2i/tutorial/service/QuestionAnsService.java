package com.intrigueit.myc2i.tutorial.service;
// default package

import java.util.ArrayList;
import java.util.List;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;

public interface QuestionAnsService {
  public List<TestTutorialQuestionAns> loadAll();
  public TestTutorialQuestionAns loadById(Long recordId);
  public void updateQuestionAns (TestTutorialQuestionAns questionAns);
  public void addQuestionAns (TestTutorialQuestionAns questionAns);
  public TestTutorialQuestionAns loadById();
  public void deleteQuestionAns(Long recordId);
  public ArrayList<String> getCategories();
  public List<TestTutorialQuestionAns> findByProperty(String propertyName, Object value);
  public List<TestTutorialQuestionAns> findByProperties(String moduleId);
  public Long getMaxPageNo(Long moduleId);
  public boolean isQuestionExist(Long recordId,Long moduleId,String question);
  public boolean isPageNoExist(Long recordId,Long moduleId,Long pageNo);
}