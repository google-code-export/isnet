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
		List<UserDefinedValues> categoryList = udValuesDao.loadAll();
		return categoryList;
	}

	public UserDefinedValues loadById(Long recordId) {
		return udValuesDao.loadById(recordId);
	}

	public void updateUDValues(UserDefinedValues udValues) {
		udValuesDao.update(udValues);
	}

	public void addUDValues(UserDefinedValues udValues) {
		udValuesDao.persist(udValues);
	}

	public void deleteUDValues(Long recordId) {
		UserDefinedValues udValues = udValuesDao.loadById(recordId);
		udValuesDao.delete(udValues);
	}

	public ArrayList<String> getCategories() {
		return udValuesDao.getCategories();
	}

	public List<UserDefinedValues> findByProperty(String propertyName,
			Object value) {
		String clause = " t." + propertyName
				+ " = ?1 order by t.udValuesDesc ASC ";
		return udValuesDao.loadByClause(clause, new Object[] { value });
	}

	public boolean isUDValueExist(Long recordId, String categoryName,
			String udValue) {
		List<Object> value = new ArrayList<Object>();
		StringBuffer clause = new StringBuffer();
		clause.append(" t.udValuesCategory = ?1").append(
				" and t.udValuesValue = ?2");
		value.add(categoryName);
		value.add(udValue);
		if (recordId != null && recordId != 0) {
			clause.append(" and t.udValuesId != ?3");
			value.add(recordId);
		}
		return udValuesDao
				.isDuplicateRecord(clause.toString(), value.toArray());
	}

	public UserDefinedValues getUDValue(String propertyName, Object value) {
		String clause = " t." + propertyName + " = ?1 ";
		List<UserDefinedValues> values = udValuesDao.loadByClause(clause,
				new Object[] { value });
		if (values != null && values.size() > 0) {
			return values.get(0);
		}
		return null;

	}

	public List<UserDefinedValues> getUDValues(String sql) {
		return udValuesDao.loadByQuery(sql, new Object[] {});
	}
}
