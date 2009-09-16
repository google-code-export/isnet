package com.intrigueit.myc2i.chapter.service;

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

	public void deleteChapter(Long recordId) {
		LocalChapter udValues = chapterDao.loadById(recordId);
		chapterDao.delete(udValues);		
	}
	
	public List<LocalChapter> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return chapterDao.loadByClause(clause, new Object[]{value});
  }
	
	public List<LocalChapter> findByProperties(SearchBean searchBean) {
	  StringBuffer clause = new StringBuffer();
	  clause.append(" from LocalChapter ");
	  boolean useWhere = true;
	  if (searchBean.getChapterName()!= null && !searchBean.getChapterName().isEmpty()) {
	    clause.append(" where upper(chapterName) like upper('%"+searchBean.getChapterName()+"%')");
	    useWhere = false;
	  }
	  /*if (searchBean.getChapterLeader()!= null && !searchBean.getChapterLeader().isEmpty()) {
      if ( useWhere ) {
        clause.append(" where ");
      } else {
        clause.append(" and ");
      }
      clause.append(" leadMemberName like '%"+searchBean.getChapterLeader()+"%'");
    }*/
	  if (searchBean.getCity()!= null && !searchBean.getCity().isEmpty()) {
      if ( useWhere ) {
        clause.append(" where ");
      } else {
        clause.append(" and ");
      }
      clause.append(" upper(chapterCity) like upper('%"+searchBean.getCity()+"%')");
    }
	  if (searchBean.getState()!= null && !searchBean.getState().isEmpty()) {
      if ( useWhere ) {
        clause.append(" where ");
      } else {
        clause.append(" and ");
      }
      clause.append(" upper(chapterState) like upper('%"+searchBean.getState()+"%')");
    }
	  return chapterDao.findByProperties(clause.toString());
	}
}
