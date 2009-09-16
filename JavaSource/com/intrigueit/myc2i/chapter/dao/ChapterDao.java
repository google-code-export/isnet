package com.intrigueit.myc2i.chapter.dao;

import java.util.List;

import com.intrigueit.myc2i.chapter.domain.LocalChapter;
import com.intrigueit.myc2i.common.dao.GenericDao;


public interface ChapterDao extends GenericDao<LocalChapter,Long>{
  public List<LocalChapter> findByProperties(String hsql);	
}
