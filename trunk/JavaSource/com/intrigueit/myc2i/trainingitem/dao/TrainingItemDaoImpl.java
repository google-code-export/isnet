package com.intrigueit.myc2i.trainingitem.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;

@Repository
@Transactional
public class TrainingItemDaoImpl extends GenericDaoImpl<TrainingItem,Long> implements TrainingItemDao{
  
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<String> getCategories() {
    String hsql = "select distinct(itemEIndicator) from TrainingItem ";
    log.debug(hsql);
    Query query = entityManager.createQuery(hsql);
    ArrayList<String> categoryList = (ArrayList<String>)query.getResultList();
    if ( categoryList == null ) {
      return new ArrayList<String>();
    }
    return categoryList;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<TrainingItem> findByProperties(String hsql) {
    Query query = entityManager.createQuery(hsql);
    return query.getResultList();
  } 
}
