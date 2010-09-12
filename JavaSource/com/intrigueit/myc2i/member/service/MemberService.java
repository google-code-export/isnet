package com.intrigueit.myc2i.member.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.role.domain.RolePageAccess;


public interface MemberService {


	public void save(Member entity);

  	public void delete(Member entity);

	public Member update(Member entity);
	
	public Member findById( Long id);

	public List<Member> findByProperty(String propertyName, Object value);

	public List<Member> findAll();	
	
	public Boolean isMemberExist(String email);
	
	public Member loadMemberByEmail(String email);
	
	public List<Member> getMentorProtege(Long mentorId);
	
	public List<Member> getMemberByDynamicHsql(String hsql);
	
	public List<Member> findByProperties(SearchBean searchBean);
	
	public List<Member> findMentorByIds(List<String> idList);
	
	public int getMentorProtegeCout(Long memberId);
	
	public List<Member> loadProtegeWithoutMentor();
	
	public List<RolePageAccess> loadUserPrivilegePages(Long memberTypeId);
	
	public boolean isMembershipExpired(Long memberId, int expiryDateLimit);
	
	public List<Member> findUnVerifiedMember();
	
	public List<Member> getMyFriendList(Long memberId);
	
	public List<Member> getRecentlyJoinedMemberList();
	
}