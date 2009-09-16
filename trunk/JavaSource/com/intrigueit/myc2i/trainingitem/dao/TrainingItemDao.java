package com.intrigueit.myc2i.trainingitem.dao;

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;


public interface TrainingItemDao extends GenericDao<TrainingItem,Long>{
  public ArrayList<String> getCategories();
  public List<TrainingItem> findByProperties(String hsql);
}
