package com.intrigueit.myc2i.chapter.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.chapter.domain.LocalChapter;
import com.intrigueit.myc2i.common.domain.SearchBean;

public interface ChapterService {
  
  public List<LocalChapter> loadAll();
  public LocalChapter loadById(Long recordId);
  public void updateChapter (LocalChapter chapter);
  public void addChapter (LocalChapter chapter);
  public void deleteChapter(Long recordId);
  public List<LocalChapter> findByProperty(String propertyName, Object value);
  public List<LocalChapter> findByProperties(SearchBean searchBean);
}