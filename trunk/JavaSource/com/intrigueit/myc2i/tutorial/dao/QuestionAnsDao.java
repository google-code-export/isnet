package com.intrigueit.myc2i.tutorial.dao;

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;


public interface QuestionAnsDao extends GenericDao<TestTutorialQuestionAns,Long>{
  public ArrayList<String> getCategories();
  public List<TestTutorialQuestionAns> findByProperties(String hsql);
  public Long getMaxNumber( String hsql );
}
