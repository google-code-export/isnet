package com.intrigueit.myc2i.udvalues.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;

@Repository
@Transactional
public class UDValuesDaoImpl extends GenericDaoImpl<UserDefinedValues, Long>
		implements UDValuesDao {

	@SuppressWarnings("unchecked")
	public ArrayList<String> getCategories() {
		String hsql = "select distinct(udValuesCategory) from UserDefinedValues ";
		log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		ArrayList<String> categoryList = (ArrayList<String>) query
				.getResultList();
		if (categoryList == null) {
			return new ArrayList<String>();
		}
		return categoryList;
	}

}
