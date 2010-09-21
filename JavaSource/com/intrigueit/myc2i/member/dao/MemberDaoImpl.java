package com.intrigueit.myc2i.member.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.role.domain.RolePageAccess;

@Repository
@Transactional
public class MemberDaoImpl extends GenericDaoImpl<Member, Long> implements
		MemberDao {

	public List<Member> findByClientPrefix(String prefix) {
		return null;
	}

	@Override
	public Boolean isMemberExist(String email) {
		String clause = " t.email = ?1 ";
		List<Member> members = loadByClause(clause, new Object[] { email });
		return members.size() > 0;
	}

	@Override
	public List<Member> getMentorProteges(Long mentorId) {
		String clause = " t.mentoredByMemberId = ?1 ";
		List<Member> members = loadByClause(clause, new Object[] { mentorId });
		return members;
	}

	@Override
	public List<Member> findByDynamicHsql(String clause) {
		return this.loadByClause(clause, new Object[] {});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> findByClause(String hsql) {
		Query query = entityManager.createQuery(hsql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
  @Override
	public List<RolePageAccess> loadUserPrivilegePages(Long memberTypeId) {
	  return entityManager.createQuery("from RolePageAccess where memberTypeId="+memberTypeId).getResultList();
	}

	@Override
	public List<Member> getLeadMentorMentorWaitingForCertification(Long mentorId) {
		Long typeId = CommonConstants.MENTOR;
		Date currentDate = new Date();
		String clause = " t.mentoredByMemberId = ?1 and t.typeId=?2 and t.memberShipExpiryDate > ?3 and t.isMemberCertified <> 'YES' and t.hasCompletedTutorial = 'YES' ";
		List<Member> members = loadByClause(clause, new Object[] { mentorId, typeId, currentDate });
		return members;
	}
}
