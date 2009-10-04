package com.intrigueit.myc2i.common.autocomplete;

import java.util.ArrayList;

import com.intrigueit.myc2i.common.dao.GenericDao;

public interface CommonSearchDao extends GenericDao<CommonSearchDataTmp,Long>{	
  public ArrayList<CommonSearchDataTmp> requestProcess(String tableName,String searchText, String extConds);
}
