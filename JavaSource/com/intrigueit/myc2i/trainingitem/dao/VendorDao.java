package com.intrigueit.myc2i.trainingitem.dao;

import java.util.ArrayList;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;


public interface VendorDao extends GenericDao<ItemVendor,Long>{
  public ArrayList<String> getCategories();
}
