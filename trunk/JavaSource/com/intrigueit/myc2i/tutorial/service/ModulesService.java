package com.intrigueit.myc2i.tutorial.service;

// default package

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.tutorial.domain.TestTutorialModules;

public interface ModulesService {
	public List<TestTutorialModules> loadAll();

	public TestTutorialModules loadById(Long recordId);

	public void updateModules(TestTutorialModules modules);

	public void addModules(TestTutorialModules modules);

	public void deleteModules(Long recordId);

	public ArrayList<String> getCategories();

	public List<TestTutorialModules> findByProperty(String propertyName,
			Object value);

	public List<TestTutorialModules> findByProperties(String docTypeId,
			String userId);

	public boolean isModuleExist(Long recordId, Long docId, String moduleName);
}