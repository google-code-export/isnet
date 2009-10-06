package com.intrigueit.myc2i.memberlog.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.memberlog.dao.MemberLogDao;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;

@Service
public class MemberLogServiceImpl implements MemberLogService{

	private MemberLogDao memberLogDao;
	
	@Autowired
	public MemberLogServiceImpl(MemberLogDao memberLogDao) {
		this.memberLogDao = memberLogDao;
	}


	public void delete(MemberLog entity) {
	  memberLogDao.delete(entity);
	}


	public MemberLog findById(Long id) {
		return memberLogDao.loadById(id);
	}


	public List<MemberLog> findByProperty(String propertyName, Object value) {
		String clause = " t."+propertyName+" = ?1 ";
		return memberLogDao.loadByClause(clause, new Object[]{value});
	}

	public void save(MemberLog entity) {
		memberLogDao.persist(entity);
		
	}

	public MemberLog update(MemberLog entity) {
		memberLogDao.update(entity);
		return entity;
	}

	public List<MemberLog> findAll() {
		return this.memberLogDao.loadAll();
	}


	public List<MemberLog> getAllPendingLog() {
		String clause = " upper(t.status) = ?1 ";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString()});
	}

	public List<MemberLog> getAllCompletedLog() {
		String clause = " upper(t.status) <> ?1 ";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString()});
	}

	public List<MemberLog> getAllCompletedLog(Long memberId) {
		String clause = " upper(t.status) <> ?1 and t.fromMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
	}
	@Override
	public List<MemberLog> getAllPendingLog(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.fromMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
	}
	
	public List<MemberLog> findByProperties (String fromDate, String toDate ) {
		StringBuffer clause = new StringBuffer();    
		clause.append(" to_char(memberLogDateTime,'yyyyMMdd') >= "+fromDate);
	    if ( toDate != null ) {
	      clause.append(" and to_char(memberLogDateTime,'yyyyMMdd') <= "+toDate);
	    }
	    return memberLogDao.loadByClause(clause.toString(), null);
	}

	@Override
	public Boolean isInActiveMember(Long memberId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		String clause = " t.fromMemberId=?1 and t.memberLogDateTime > ?2 ";
		List<MemberLog> logs = memberLogDao.loadByClause(clause, new Object[]{memberId,cal.getTime()});
		return  logs.size() == 0;
	}


	@Override
	public List<MemberLog> getAllProtegePendingRequest(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
	}	
}
