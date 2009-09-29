package com.intrigueit.myc2i.tutorial.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;


public interface DocumentService {
  public List<TestTutorialDocument> loadAll();
  public List<TestTutorialDocument> loadDispDocuments();
  public TestTutorialDocument loadById(Long recordId);
  public void addDocument (TestTutorialDocument document);
  public void updateDocument (TestTutorialDocument document);    
  public void deleteDocument(Long recordId);
  public List<TestTutorialDocument> findByProperty(String propertyName, Object value);
  public boolean isDocumentExist(Long recordId,String docName);
}