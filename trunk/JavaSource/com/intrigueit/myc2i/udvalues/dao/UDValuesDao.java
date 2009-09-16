package com.intrigueit.myc2i.udvalues.dao;

import java.util.ArrayList;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;


public interface UDValuesDao extends GenericDao<UserDefinedValues,Long>{
  public ArrayList<String> getCategories();	
}
