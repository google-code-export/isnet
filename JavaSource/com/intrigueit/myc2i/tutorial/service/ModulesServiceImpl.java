package com.intrigueit.myc2i.tutorial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.tutorial.dao.ModulesDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;

@Service
public class ModulesServiceImpl implements ModulesService {

	private ModulesDao modulesDao;
	
	@Autowired
	public ModulesServiceImpl(ModulesDao modulesDao) {
		this.modulesDao = modulesDao;
	}

	public List<TestTutorialModules> loadAll() {
		List<TestTutorialModules> categoryList= modulesDao.loadAll();
		return categoryList;
	}
	
	public TestTutorialModules loadById() {
		//return modulesDao.loadById(recordId);
		return null;
	}
	
	public TestTutorialModules loadById(Long recordId) {
		return modulesDao.loadById(recordId);
	}
	public void updateModules (TestTutorialModules modules) {
		modulesDao.update(modules);
	}
	public void addModules (TestTutorialModules modules) {
		modulesDao.persist(modules);
	}

	public void deleteModules(Long recordId) {
		TestTutorialModules modules = modulesDao.loadById(recordId);
		modulesDao.delete(modules);		
	}
	
	public ArrayList<String> getCategories() {   
    return modulesDao.getCategories();
  }
  public List<TestTutorialModules> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return modulesDao.loadByClause(clause, new Object[]{value});
  }
  
  public List<TestTutorialModules> findByProperties(String docTypeId, String userId) {
    StringBuffer clause = new StringBuffer();
    clause.append("SELECT NEW TestTutorialModules(tm.modulesId,tm.moduleName,tm.moduleText)")
    .append(" FROM TestTutorialModules tm") ;
    
    boolean useWhere = true;  
    if (docTypeId != null && !docTypeId.isEmpty()) {
      clause.append(" where tm.documentId ="+docTypeId);
      useWhere = false;
    }
    if (userId!= null && !userId.isEmpty()) {
      if ( useWhere ) {
        clause.append(" where ");
      } else {
        clause.append(" and ");
      }
      clause.append(" tm.memberTypeIndicator ="+userId);
    }
    return modulesDao.findByProperties(clause.toString());
  }
  
  @Override
  public boolean isModuleExist(Long recordId,Long docId,String moduleName) {
    List<Object> value = new ArrayList<Object>();
    StringBuffer clause = new StringBuffer();
    clause.append(" t.documentId = ?1")
          .append(" and t.moduleName = ?2");
    value.add(docId);
    value.add(moduleName);
    if (recordId != null && recordId !=0) {
      clause.append(" and t.modulesId != ?3");
      value.add(recordId);
    }
    return modulesDao.isDuplicateRecord(clause.toString(),value.toArray());
  }
}
