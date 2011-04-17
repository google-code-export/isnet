package com.intrigueit.myc2i.trainingitem.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;

@Repository
@Transactional
public class VendorDaoImpl extends GenericDaoImpl<ItemVendor,Long> implements VendorDao{
  
  @SuppressWarnings("unchecked")

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
}
