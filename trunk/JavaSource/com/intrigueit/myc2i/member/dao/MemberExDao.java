package com.intrigueit.myc2i.member.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.member.domain.Member;


public interface MemberExDao extends GenericDao<Member,Long> {
	public List<Member> findByProperties(String hsql);
}
