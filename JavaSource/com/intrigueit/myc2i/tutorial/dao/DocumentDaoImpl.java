package com.intrigueit.myc2i.tutorial.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;

@Repository
@Transactional
public class DocumentDaoImpl extends GenericDaoImpl<TestTutorialDocument,Long> implements DocumentDao{  
  
  @SuppressWarnings("unchecked")
  public List<TestTutorialDocument> findByProperties(String hsql) {
    Query query = entityManager.createQuery(hsql);
    return query.getResultList();
  }
}
