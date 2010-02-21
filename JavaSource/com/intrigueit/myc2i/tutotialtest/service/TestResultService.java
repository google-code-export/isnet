package com.intrigueit.myc2i.tutotialtest.service;

import com.intrigueit.myc2i.tutotialtest.domain.TestResult;

public interface TestResultService {
	
  public void save(TestResult entity);

  public void delete(TestResult entity);

	public void update(TestResult entity);
	
	public TestResult findById( Long id);
}
