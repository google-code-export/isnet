package com.intrigueit.myc2i.chapter.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.chapter.domain.LocalChapter;
import com.intrigueit.myc2i.common.dao.GenericDaoImpl;

@Repository
@Transactional
public class ChapterDaoImpl extends GenericDaoImpl<LocalChapter,Long> implements ChapterDao{
  
  @SuppressWarnings("unchecked")
  @Override
  public List<LocalChapter> findByProperties(String hsql) {
    Query query = entityManager.createQuery(hsql);
    return query.getResultList();
  }	
}
