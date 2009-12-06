package com.intrigueit.myc2i.tutotialtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.tutotialtest.dao.TestResultDetailsDao;
import com.intrigueit.myc2i.tutotialtest.domain.TestResultDetails;

@Service
public class TestResultDetailsServiceImpl implements TestResultDetailsService{

	private TestResultDetailsDao testResultDetailsDao;
	
	@Autowired
	public TestResultDetailsServiceImpl(TestResultDetailsDao testResultDetailsDao) {
		this.testResultDetailsDao = testResultDetailsDao;
	}
	
	@Override
	public void delete(TestResultDetails entity) {
		this.testResultDetailsDao.delete(entity);
		
	}

	@Override
	public TestResultDetails findById(Long id) {
		return this.testResultDetailsDao.loadById(id);
	}

	@Override
	public void save(TestResultDetails entity) {
		this.testResultDetailsDao.persist(entity);
		
	}

	@Override
	public void update(TestResultDetails entity) {
		this.testResultDetailsDao.update(entity);
		
	}

}
