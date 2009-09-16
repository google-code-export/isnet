package com.intrigueit.myc2i.tutorial.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.intrigueit.myc2i.tutorial.dao.DocumentDao;
import com.intrigueit.myc2i.tutorial.domain.TestTutorialDocument;

@Service
public class DocumentServiceImpl implements DocumentService {

	private DocumentDao documentDao;
	
	@Autowired
	public DocumentServiceImpl(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public List<TestTutorialDocument> loadAll() {
		List<TestTutorialDocument> categoryList= documentDao.loadAll();
		return categoryList;
	}
	
	public List<TestTutorialDocument> loadDispDocuments() {
	  StringBuffer clause = new StringBuffer();
    clause.append("SELECT NEW TestTutorialDocument(td.documentId,td.documentName)")
          .append(" FROM TestTutorialDocument td") ;
    return documentDao.findByProperties(clause.toString());
	}
	
	public TestTutorialDocument loadById(Long recordId) {
		return documentDao.loadById(recordId);
	}
	public void updateDocument (TestTutorialDocument document) {
		documentDao.update(document);
	}
	public void addDocument (TestTutorialDocument document) {
		documentDao.persist(document);
	}

	public void deleteDocument(Long recordId) {
		TestTutorialDocument trainingItem = documentDao.loadById(recordId);
		documentDao.delete(trainingItem);		
	}
	
	public List<TestTutorialDocument> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return documentDao.loadByClause(clause, new Object[]{value});
  }
}
