package com.intrigueit.myc2i.udvalues.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.udvalues.dao.UDValuesDao;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;



@Service
public class UDValuesServiceImpl implements UDValuesService {

	private UDValuesDao udValuesDao;
	
	@Autowired
	public UDValuesServiceImpl(UDValuesDao udValuesDao) {
		this.udValuesDao = udValuesDao;
	}

	public List<UserDefinedValues> loadAll() {
		List<UserDefinedValues> categoryList= udValuesDao.loadAll();
		return categoryList;
	}	

	public UserDefinedValues loadById(Long recordId) {
		return udValuesDao.loadById(recordId);
	}
	public void updateUDValues (UserDefinedValues udValues) {
		udValuesDao.update(udValues);
	}
	public void addUDValues (UserDefinedValues udValues) {
		udValuesDao.persist(udValues);
	}

	public void deleteUDValues(Long recordId) {
		UserDefinedValues udValues = udValuesDao.loadById(recordId);
		udValuesDao.delete(udValues);		
	}
	public ArrayList<String> getCategories() {	  
	  return udValuesDao.getCategories();
	}
	public List<UserDefinedValues> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" = ?1 ";
    return udValuesDao.loadByClause(clause, new Object[]{value});
  }
}
