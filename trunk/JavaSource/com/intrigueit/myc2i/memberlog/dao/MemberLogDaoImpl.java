package com.intrigueit.myc2i.memberlog.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;

@Repository
@Transactional
public class MemberLogDaoImpl extends GenericDaoImpl<MemberLog,Long> implements MemberLogDao{
  @SuppressWarnings("unchecked")
  @Override  
  public List<MemberLog> findByProperties(final String hsql) {    
    Query query = entityManager.createNativeQuery(hsql,MemberLog.class);
    return query.getResultList();
  }
}
