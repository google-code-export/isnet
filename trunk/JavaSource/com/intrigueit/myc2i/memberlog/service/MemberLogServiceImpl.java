package com.intrigueit.myc2i.memberlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.memberlog.dao.MemberLogDao;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.domain.MemberLogPK;

@Service
public class MemberLogServiceImpl implements MemberLogService{

	private MemberLogDao memberLogDao;
	
	@Autowired
	public MemberLogServiceImpl(MemberLogDao memberLogDao) {
		this.memberLogDao = memberLogDao;
	}


	public void delete(MemberLog entity) {
		// TODO Auto-generated method stub
		
	}


	public MemberLog findById(MemberLogPK id) {
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

}
