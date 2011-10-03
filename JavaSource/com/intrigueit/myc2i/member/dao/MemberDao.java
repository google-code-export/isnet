package com.intrigueit.myc2i.member.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.role.domain.ApplicationPages;
import com.intrigueit.myc2i.role.domain.RolePageAccess;


public interface MemberDao extends GenericDao<Member,Long>{

	public List<Member> findByClientPrefix(String prefix);
	
	public Boolean isMemberExist(String email);
	
	public List<Member> getMentorProteges(Long mentorId);
	
	public List<Member> findByDynamicHsql(String clause);
	
	public List<Member> findByClause(String hsql);
	
	public List<RolePageAccess> loadUserPrivilegePages(Long memberTypeId);
	
	public List<Member> getLeadMentorMentorWaitingForCertification(Long mentorId);
	
	public List<ApplicationPages> getUserPrivilegePages(Long memberTypeId);
	
}
