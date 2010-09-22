package com.intrigueit.myc2i.member.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.member.dao.KnownMemberDao;
import com.intrigueit.myc2i.member.dao.MemberDao;
import com.intrigueit.myc2i.member.domain.KnownMember;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.role.domain.RolePageAccess;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	
	private KnownMemberDao knownMemberDao;

	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void delete(Member entity) {
		memberDao.delete(entity);
	}

	public List<Member> findAll() {
		return memberDao.loadAll();
	}

	public Member findById(Long id) {
		return memberDao.loadById(id);
	}

	public List<Member> findByProperty(String propertyName, Object value) {
		String clause = " t." + propertyName + " = ?1 ";
		return memberDao.loadByClause(clause, new Object[]{value});
	}

	public Member loadMemberByEmail(String email) {
		String clause = " lower(t.email) = ?1 ";
		List<Member> members = memberDao.loadByClause(clause,
				new Object[]{email.toLowerCase()});
		if (members.size() > 0) {
			return members.get(0);
		}
		return null;
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
			clause = (useAnd)
					? clause.append(" and upper(email) like ?" + i++)
					: clause.append(" upper(email) like ?" + i++);
			value.add("%" + searchBean.getEmail().toUpperCase() + "%");
			useAnd = true;
		}

		if (searchBean.getFirstName() != null
				&& !searchBean.getFirstName().isEmpty()) {
			clause = (useAnd) ? clause.append(" and upper(firstName) like ?"
					+ i++) : clause.append(" upper(firstName) like ?" + i++);
			value.add("%" + searchBean.getFirstName().toUpperCase() + "%");
			useAnd = true;
		}

		if (searchBean.getLastName() != null
				&& !searchBean.getLastName().isEmpty()) {
			clause = (useAnd) ? clause.append(" and upper(lastName) like ?"
					+ i++) : clause.append(" upper(lastName) like ?" + i++);
			value.add("%" + searchBean.getLastName().toUpperCase() + "%");
			useAnd = true;
		}

		if (searchBean.getCity() != null && !searchBean.getCity().isEmpty()) {
			clause = (useAnd)
					? clause.append(" and upper(city) like ?" + i++)
					: clause.append(" upper(city) like ?" + i++);
			value.add("%" + searchBean.getCity().toUpperCase() + "%");
			useAnd = true;
		}

		if (searchBean.getState() != null
				&& !searchBean.getState().equals("-1")) {
			clause = (useAnd) ? clause.append(" and state =?" + i++) : clause
					.append(" state =?" + i++);
			value.add(searchBean.getState());
			useAnd = true;
		}
		if (searchBean.getExtraProps() != null
				&& !searchBean.getExtraProps().isEmpty()) {
			clause = (useAnd) ? clause
					.append(" and "+searchBean.getExtraProps()) : clause
					.append(searchBean.getExtraProps());
		}

		if (clause.length() == 0) {
			return memberDao.loadAll();
		} else {
			return memberDao.loadByClause(clause.toString(), value.toArray());
		}
	}

	public List<Member> loadProtegeWithoutMentor() {
		String clause = "from MEMBER where typeId = " + CommonConstants.PROTEGE
				+ " and mentoredByMemberId is null";
		return memberDao.findByClause(clause);
	}

	@Override
	public List<Member> findMentorByIds(List<String> idList) {
		String ids = null;
		for (String str : idList) {
			ids = (ids == null) ? str : ids + "," + str;
		}
		String clause = " t.memberId IN (" + ids + ") ";

		return memberDao.loadByClause(clause, new Object[]{});
	}

	@Override
	public int getMentorProtegeCout(Long memberId) {
		String clause = " t.mentoredByMemberId =?1 ";

		List<Member> members = memberDao.loadByClause(clause,
				new Object[]{memberId});
		if (members == null)
			return 0;
		return members.size();

	}

	public List<RolePageAccess> loadUserPrivilegePages(Long memberTypeId) {
		return memberDao.loadUserPrivilegePages(memberTypeId);
	}

	@Override
	public boolean isMembershipExpired(Long memberId, int expiryDateLimit) {
		Member member = memberDao.loadById(memberId);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		// System.out.println("Membership expiry: "+
		// member.getMemberShipExpiryDate());

		/** Already registered member but membership expired */
		if (member.getMemberShipExpiryDate() != null
				&& cal.getTime().after(member.getMemberShipExpiryDate())) {
			return true;
		}

		Calendar calReg = Calendar.getInstance();
		calReg.setTime(member.getRecordCreate());

		calReg.add(Calendar.DAY_OF_YEAR, expiryDateLimit);
		// System.out.println("Evaluation period date: "+calReg.getTime());
		// System.out.println("Current date: "+cal.getTime());

		/** Not yet registered first time but free evaluation period expired */
		if (member.getMemberShipExpiryDate() == null
				&& cal.getTime().after(calReg.getTime())) {
			return true;
		}

		return false;
	}

	@Override
	public List<Member> findUnVerifiedMember() {
		String clause = "from MEMBER where memberId in (select distinct(memberId) from PayPalLog where verifyStatus is null)";
		return memberDao.loadByQuery(clause.toString(), new Object[]{});
	}

	@Override
	public List<Member> getMyFriendList(Long memberId) {
		String clause = " t.srcMember.memberId =?1 ";

		List<KnownMember> knMebers = knownMemberDao.loadByClause(clause,new Object[]{memberId});
		List<Member> members = new ArrayList<Member>();
		for(KnownMember mem: knMebers){
			members.add(mem.getFriend());
		}
		return members;
	}

	@Autowired
	public void setKnownMemberDao(KnownMemberDao knownMemberDao) {
		this.knownMemberDao = knownMemberDao;
	}

	@Override
	public List<Member> getRecentlyJoinedMemberList() {
		String clause = " t.mentoredByMemberId =?1 ";

		List<Member> members = memberDao.loadByClause(clause,new Object[]{});

		return members;
	}

	@Override
	public List<Member> getLeadMentorsMentorWaitingForCertification(Long mentorId) {
		return memberDao.getLeadMentorMentorWaitingForCertification(mentorId);
	}

	@Override
	public void addToKnownMemberList(Member member, Member friend) {
		KnownMember kMember = new KnownMember();
		kMember.setFriendId(friend.getMemberId());
		kMember.setSrcMember(member);
		this.knownMemberDao.persist(kMember);
		
	}


}
