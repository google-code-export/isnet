package com.intrigueit.myc2i.member.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.member.domain.Member;

@Repository
@Transactional
public class MemberExDaoImpl extends GenericDaoImpl<Member,Long> implements MemberExDao{
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Member> findByProperties(String hsql) {
    Query query = entityManager.createQuery(hsql);
    return query.getResultList();
  }
}
