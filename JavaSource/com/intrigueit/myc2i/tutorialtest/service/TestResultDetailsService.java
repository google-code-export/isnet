package com.intrigueit.myc2i.tutorialtest.service;

import com.intrigueit.myc2i.tutorialtest.domain.TestResultDetails;

public interface TestResultDetailsService {
	
    public void save(TestResultDetails entity);

    public void delete(TestResultDetails entity);

	public void update(TestResultDetails entity);
	
	public TestResultDetails findById( Long id);
}
