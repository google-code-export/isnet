package com.intrigueit.myc2i.tutorial.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;


public interface DocumentDao extends GenericDao<TestTutorialDocument,Long>{
  public List<TestTutorialDocument> findByProperties(String hsql);
}
