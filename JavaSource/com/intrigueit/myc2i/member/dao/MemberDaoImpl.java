package com.intrigueit.myc2i.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.member.domain.Member;

@Repository
@Transactional
public class MemberDaoImpl extends GenericDaoImpl<Member,Long> implements MemberDao{

	public List<Member> findByClientPrefix(String prefix) {
		return null;
	}

	@Override
	public Boolean isMemberExist(String email) {
		String clause = " t.email = ?1 ";
		List<Member> members = loadByClause(clause, new Object[]{email});
		return members.size()>0;
	}

	@Override
	public List<Member> getMentorProteges(Long mentorId) {
		String clause = " t.mentoredByMemberId = ?1 ";
		List<Member> members = loadByClause(clause, new Object[]{mentorId});
		return members;
	}
}
