package com.intrigueit.myc2i.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.member.dao.MemberDao;
import com.intrigueit.myc2i.member.domain.Member;

@Service
public class MemberServiceImpl implements MemberService {

  private MemberDao memberDao;

  @Autowired
  public MemberServiceImpl(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public void delete(Member entity) {
  }

  public List<Member> findAll() {
    return memberDao.loadAll();
  }

  public Member findById(Long id) {
    return memberDao.loadById(id);
  }

  public List<Member> findByProperty(String propertyName, Object value) {
    String clause = " t." + propertyName + " = ?1 ";
    return memberDao.loadByClause(clause, new Object[] { value });
  }

  public void save(Member entity) {
    memberDao.persist(entity);

  }

  public Member update(Member entity) {
    memberDao.update(entity);
    return entity;
  }

  @Override
  public Boolean isMemberExist(String email) {
    return memberDao.isMemberExist(email);
  }

  @Override
  public List<Member> getMentorProtege(Long mentorId) {
    return memberDao.getMentorProteges(mentorId);
  }

  @Override
  public List<Member> getMemberByDynamicHsql(String hsql) {
    return memberDao.loadByClause(hsql, new Object[] {});
  }

  @Override
  public List<Member> findByProperties(SearchBean searchBean) {
    List<Object> value = new ArrayList<Object>();
    StringBuffer clause = new StringBuffer();
    boolean useAnd = false;
    int i = 1;
    if (searchBean.getRecordId() != null && searchBean.getRecordId() != 0) {
      clause.append(" t.typeId = ?" + i++);
      value.add(searchBean.getRecordId());
      useAnd = true;
    }

    if (searchBean.getEmail() != null && !searchBean.getEmail().isEmpty()) {
      clause = (useAnd) ? clause.append(" and upper(email) like ?" + i++)
          : clause.append(" upper(email) like ?" + i++);
      value.add("%" + searchBean.getEmail().toUpperCase() + "%");
      useAnd = true;
    }

    if (searchBean.getFirstName() != null
        && !searchBean.getFirstName().isEmpty()) {
      clause = (useAnd) ? clause.append(" and upper(firstName) like ?" + i++)
          : clause.append(" upper(firstName) like ?" + i++);
      value.add("%" + searchBean.getFirstName().toUpperCase() + "%");
      useAnd = true;
    }

    if (searchBean.getLastName() != null && !searchBean.getLastName().isEmpty()) {
      clause = (useAnd) ? clause.append(" and upper(lastName) like ?" + i++)
          : clause.append(" upper(lastName) like ?" + i++);
      value.add("%" + searchBean.getLastName().toUpperCase() + "%");
      useAnd = true;
    }

    if (searchBean.getCity() != null && !searchBean.getCity().isEmpty()) {
      clause = (useAnd) ? clause.append(" and upper(city) like ?" + i++)
          : clause.append(" upper(city) like ?" + i++);
      value.add("%" + searchBean.getCity().toUpperCase() + "%");
      useAnd = true;
    }

    if (searchBean.getState() != null && !searchBean.getState().equals("-1")) {
      clause = (useAnd) ? clause.append(" and state =?" + i++)
          : clause.append(" state =?" + i++);
      value.add(searchBean.getState());      
    }
    if (clause.length() == 0) {
      return memberDao.loadAll();
    } else {
      return memberDao.loadByClause(clause.toString(), value.toArray());
    }
  }

@Override
public List<Member> findMentorByIds(List<String> idList) {
    String ids = null;
	for(String str: idList){
    	ids = (ids == null)? str : ids + ","+str; 
    }
	String clause = " t.memberId IN ("+ ids +") ";
    
    return memberDao.loadByClause(clause, new Object[] {});
}

}
