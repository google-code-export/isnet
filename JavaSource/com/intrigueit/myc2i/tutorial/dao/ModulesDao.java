package com.intrigueit.myc2i.tutorial.dao;

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;


public interface ModulesDao extends GenericDao<TestTutorialModules,Long>{
  
	public ArrayList<String> getCategories();
  
	public List<TestTutorialModules> findByProperties(String hsql);
  
	public List<TestTutorialModules> findModulesByUserType(Long type);
  
}
