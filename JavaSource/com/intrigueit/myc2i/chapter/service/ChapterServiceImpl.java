package com.intrigueit.myc2i.chapter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.chapter.dao.ChapterDao;
import com.intrigueit.myc2i.chapter.domain.LocalChapter;
import com.intrigueit.myc2i.common.domain.SearchBean;

@Service
public class ChapterServiceImpl implements ChapterService {
  protected static final Logger logger = Logger.getLogger( ChapterServiceImpl.class );
	private ChapterDao chapterDao;
	
	@Autowired
	public ChapterServiceImpl(ChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}

	public List<LocalChapter> loadAll() {
		List<LocalChapter> chapterList = chapterDao.loadAll();
		return chapterList;
	}	

	public LocalChapter loadById(Long recordId) {
		return chapterDao.loadById(recordId);
	}
	public void updateChapter (LocalChapter udValues) {
		chapterDao.update(udValues);
	}
	public void addChapter (LocalChapter udValues) {
		chapterDao.persist(udValues);
	}

	public void deleteChapter(LocalChapter chapter) {
		chapterDao.delete(chapter);		
	}
	
	public List<LocalChapter> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return chapterDao.loadByClause(clause, new Object[]{value});
  }
	
	  public List<LocalChapter> findByProperties(SearchBean searchBean) {
	    List<Object> value = new ArrayList<Object>();
	    StringBuffer clause = new StringBuffer();
	    boolean useAnd = false;
	    int i = 1;
	    if (searchBean.getChapterName() != null && !searchBean.getChapterName().equals("")) {
	      clause.append(" upper(chapterName) like ?" + i++);
	      value.add("%" + searchBean.getChapterName().toUpperCase() + "%");
	      useAnd = true;
	    }  
	    
	    if ( searchBean.getRecordId() != null && searchBean.getRecordId() != 0) {
        clause.append(" leadMemberId = ?" + i++);
        value.add(searchBean.getRecordId());
        useAnd = true;
      }
	    
	    if (searchBean.getCity() != null && !searchBean.getCity().equals("")) {
	      clause = (useAnd) ? clause.append(" and upper(chapterCity) like ?" + i++)
	          : clause.append(" upper(chapterCity) like ?" + i++);
	      value.add("%" + searchBean.getCity().toUpperCase() + "%");
	      useAnd = true;
	    }
	   
	    if (searchBean.getState() != null && !searchBean.getState().equals("-1")) {
	      clause = (useAnd) ? clause.append(" and chapterState =?" + i++)
	          : clause.append(" chapterState =?" + i++);
	      value.add(searchBean.getState());      
	    }
	    
	    if (clause.length() == 0) {
	      return chapterDao.loadAll();
	    } else {
	      return chapterDao.loadByClause(clause.toString(), value.toArray());
	    }
	  }
	
	public boolean isRecordExist(Long recordId,String chapterName) {
	  List<Object> value = new ArrayList<Object>();
	  StringBuffer clause = new StringBuffer();
    clause.append(" t.chapterName = ?1");
    value.add(chapterName);
    if (recordId != null && recordId !=0) {
      clause.append(" and t.chapterId != ?2");
      value.add(recordId);
    } 
    return chapterDao.isDuplicateRecord(clause.toString(),value.toArray());
  }
}
