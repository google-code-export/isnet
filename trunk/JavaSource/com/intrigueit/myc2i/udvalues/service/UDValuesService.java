package com.intrigueit.myc2i.udvalues.service;
// default package

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;


public interface UDValuesService {
  public List<UserDefinedValues> loadAll();
  public UserDefinedValues loadById(Long recordId);
  public void updateUDValues (UserDefinedValues udValues);
  public void addUDValues (UserDefinedValues udValues);
  public void deleteUDValues(Long recordId);
  public ArrayList<String> getCategories();
  public List<UserDefinedValues> findByProperty(String propertyName, Object value);
  public boolean isUDValueExist(Long recordId,String categoryName,String value);
}