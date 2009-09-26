package com.intrigueit.myc2i.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.member.dao.MemberDao;
import com.intrigueit.myc2i.member.domain.Member;

@Service
public class MemberServiceImpl implements MemberService{

	private MemberDao memberDao;
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void delete(Member entity) {
		// TODO Auto-generated method stub
	}

	public List<Member> findAll() {
		return memberDao.loadAll();
	}

	public Member findById(Long id) {
		return memberDao.loadById(id);
	}

	public List<Member> findByProperty(String propertyName, Object value) {
		String clause = " t."+propertyName+" = ?1 ";
		return memberDao.loadByClause(clause, new Object[]{value});
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
		return memberDao.loadByClause(hsql, new Object[]{});
	}


}
