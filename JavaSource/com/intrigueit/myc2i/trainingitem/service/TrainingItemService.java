package com.intrigueit.myc2i.trainingitem.service;
// default package

import java.util.ArrayList;
import java.util.List;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;


public interface TrainingItemService {
  public List<TrainingItem> loadAll();
  public TrainingItem loadById(Long recordId);
  public void updateTrainingItem (TrainingItem trainingItem);
  public void addTrainingItem (TrainingItem trainingItem);
  public TrainingItem loadById();
  public void deleteTrainingItem(Long recordId);
  public ArrayList<String> getCategories();
  public List<TrainingItem> findByProperty(String propertyName, Object value);
  public List<TrainingItem> findByProperties(String vendorId, String itemEnd);
}