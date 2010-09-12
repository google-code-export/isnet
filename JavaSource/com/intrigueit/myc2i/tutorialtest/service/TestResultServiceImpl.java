package com.intrigueit.myc2i.tutorialtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.tutorialtest.dao.TestResultDao;
import com.intrigueit.myc2i.tutorialtest.dao.TestResultDetailsDao;
import com.intrigueit.myc2i.tutorialtest.domain.TestResult;
import com.intrigueit.myc2i.tutorialtest.domain.TestResultDetails;

@Service
@Transactional
public class TestResultServiceImpl implements TestResultService{

	private TestResultDao testResultDao;
	
	private TestResultDetailsDao testResultDetailDao;
	
	@Autowired
	public TestResultServiceImpl(TestResultDao testResultDao) {
		this.testResultDao = testResultDao;
	}
	
	@Override
	public void delete(TestResult entity) {
		this.testResultDao.delete(entity);
		
	}

	@Override
	public TestResult findById(Long id) {
		return this.testResultDao.loadById(id);
	}

	@Override
	public void save(TestResult entity) {
		this.testResultDao.persist(entity);
		
	}

	@Override
	public void update(TestResult entity) {
		this.testResultDao.update(entity);
		
	}

	@Override
	public List<TestResult> findUserTestResult(Long userId) {
		return this.testResultDao.findUserTestResult(userId);
	}

	@Override
	public List<TestResult> findAll() {
		return testResultDao.loadAll();
	}

	@Override
	public TestResult loadUserModuleResult(Long userId, Long moduleId) {
		return testResultDao.loadUserModuleResult(userId, moduleId);
	}

	@Override
	public int deleteResultDetails(Long testId) {
		// TODO Auto-generated method stub
		return this.testResultDetailDao.deleteResults(testId);
		
	}
	
	@Autowired
	public void setTestResultDetailsDao(TestResultDetailsDao testResultDetailDao) {
		this.testResultDetailDao = testResultDetailDao;
	}

}
