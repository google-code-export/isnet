package com.intrigueit.myc2i.tutorial.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;

@Repository
@Transactional
public class QuestionAnsDaoImpl extends
		GenericDaoImpl<TestTutorialQuestionAns, Long> implements QuestionAnsDao {

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

	public List<TestTutorialQuestionAns> findByProperties(String hsql) {
		Query query = entityManager.createQuery(hsql);
		return query.getResultList();
	}

	public Long getMaxNumber(String hsql) {
		Query query = entityManager.createQuery(hsql);
		return (Long) query.getSingleResult();

	}
}
