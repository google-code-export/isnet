package com.intrigueit.myc2i.tutorialtest.service;

import java.util.List;

import com.intrigueit.myc2i.tutorialtest.domain.TestResult;

public interface TestResultService {
	
    public void save(TestResult entity);
  
    public void delete(TestResult entity);

	public void update(TestResult entity);
	
	public TestResult findById( Long id);
	
	public List<TestResult> findUserTestResult(Long userId);
	
	public List<TestResult> findAll();
	
	public TestResult loadUserModuleResult(Long userId, Long moduleId);
	
	public int deleteResultDetails(Long testId);
}
