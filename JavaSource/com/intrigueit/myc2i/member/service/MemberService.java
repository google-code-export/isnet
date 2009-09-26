package com.intrigueit.myc2i.member.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.member.domain.Member;


public interface MemberService {


    public void save(Member entity);

    public void delete(Member entity);

	public Member update(Member entity);
	
	public Member findById( Long id);

	public List<Member> findByProperty(String propertyName, Object value);

	public List<Member> findAll();	
	
	public Boolean isMemberExist(String email);
	
	public List<Member> getMentorProtege(Long mentorId);
	
	public List<Member> getMemberByDynamicHsql(String hsql);
	
}