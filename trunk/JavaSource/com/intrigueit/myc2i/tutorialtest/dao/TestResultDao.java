package com.intrigueit.myc2i.tutorialtest.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;


public interface TestResultDao extends GenericDao<TestResult,Long>{

	public List<TestResult> findUserTestResult(Long userId);
	
	public TestResult loadUserModuleResult(Long userId, Long moduleId);
	
}
