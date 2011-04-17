package com.intrigueit.myc2i.tutorial.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;

@Repository
@Transactional
public class ModulesDaoImpl extends GenericDaoImpl<TestTutorialModules, Long>
		implements ModulesDao {

	@SuppressWarnings("unchecked")

	public ArrayList<String> getCategories() {
		String hsql = "select distinct(itemEIndicator) from TrainingItem ";
		log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		ArrayList<String> categoryList = (ArrayList<String>) query
				.getResultList();
		if (categoryList == null) {
			return new ArrayList<String>();
		}
		return categoryList;
	}

	@SuppressWarnings("unchecked")

	public List<TestTutorialModules> findByProperties(String hsql) {
		Query query = entityManager.createQuery(hsql);
		return query.getResultList();
	}


	public List<TestTutorialModules> findModulesByUserType(Long type) {
	  	String clause = " t.memberTypeIndicator =?1 ORDER BY t.serial ASC";
	  	List<TestTutorialModules> modules =  loadByClause(clause, new Object[] {type});
		return modules;
	}
}
