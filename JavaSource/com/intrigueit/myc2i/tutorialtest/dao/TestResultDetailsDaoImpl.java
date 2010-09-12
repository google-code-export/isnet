package com.intrigueit.myc2i.tutorialtest.dao;


import org.springframework.stereotype.Repository;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutorialtest.domain.TestResultDetails;


@Repository
public class TestResultDetailsDaoImpl extends GenericDaoImpl<TestResultDetails,Long> implements TestResultDetailsDao{

	@Override
	public Integer deleteResults(Long resultId) {
		String clause = "t.testResult.tutorialTestId = ?1";
		return deleteByClause(clause, new Object[] { resultId });
		
	}

}
