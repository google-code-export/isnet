package com.intrigueit.myc2i.tutorialtest.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;

@Repository
@Transactional
public class TestResultDaoImpl extends GenericDaoImpl<TestResult,Long> implements TestResultDao{

	@Override
	public List<TestResult> findUserTestResult(Long userId) {
		String clause = "t.memberId = ?1";
		List<TestResult> results = loadByClause(clause, new Object[] { userId });
		return results;
	}

	@Override
	public TestResult loadUserModuleResult(Long userId, Long moduleId) {
		
		String clause = "t.memberId = ?1 and t.moduleId= ?2";
		
		List<TestResult> results = loadByClause(clause, new Object[] { userId,moduleId });
		TestResult result = (results!= null && results.size() > 0)? results.get(0): null;
		
		return result;

	}

}
