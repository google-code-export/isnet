package com.intrigueit.myc2i.memberlog.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.cache.CachingManager;
import com.intrigueit.myc2i.memberlog.dao.MemberLogDao;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;

@Service
public class MemberLogServiceImpl implements MemberLogService{

	private MemberLogDao memberLogDao;
	private CachingManager cacheMan;
	
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

	public List<MemberLog> getAllCompletedLog(Long memberId,Date date) {
		String clause = " upper(t.status) <> ?1 and t.fromMemberId=?2 and t.memberLogDateTime >= ?3 ";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId,date});
	}

	public List<MemberLog> getAllPendingLog(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.fromMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
	}
	

	public List<MemberLog> findMemberLogByDate (String fromDate, String toDate ) {
		StringBuffer clause = new StringBuffer();    
		clause.append(" to_char(memberLogDateTime,'yyyyMMdd') >= "+fromDate);
	  if ( toDate != null ) {
	    clause.append(" and to_char(memberLogDateTime,'yyyyMMdd') <= "+toDate);
	  }
	  return memberLogDao.loadByClause(clause.toString(), null);
	}
	

	public List<MemberLog> loadMemberLogByActivityType( Long memberId ) {
	  StringBuffer clause = new StringBuffer();
    clause.append(" toMemberId = "+memberId);
    clause.append(" and memberActivityType = "+CommonConstants.ACTIVITY_TYPE_MESSAGE);
    return memberLogDao.loadByClause(clause.toString(), null);
	}
	

	public Boolean isInActiveMember(Long memberId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		String clause = " t.fromMemberId=?1 and t.memberLogDateTime > ?2 ";
		List<MemberLog> logs = memberLogDao.loadByClause(clause, new Object[]{memberId,cal.getTime()});
		return  logs.size() == 0;
	}



	public List<MemberLog> getAllProtegePendingRequest(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
	}
	
	public List<MemberLog> loadByQuery(String sql, Object[] params){
		return memberLogDao.loadByQuery(sql, params);
	}
	public List<MemberLog> getProtegePendingMentorRequests(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){
			if(log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase()) &&
					log.getUserDefinedValues().getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_MENTOR)){
				logs.add(log);
			}	
		}
		return logs;
	}	
	public List<MemberLog> getProtegePendingActivities(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){
			if(!log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase())){
				logs.add(log);
			}
		}
		return logs;
	}

	/**
	 * Return list of all pending request for mentor
	 * @param memberId
	 * @return
	 */
	public List<MemberLog> getMentorPendingProtegeRequests(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){

			if(log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_PROTEGE_REQUEST.toLowerCase()) &&
					log.getUserDefinedValues().getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_PROTEGE)){
				logs.add(log);
			}			
		}
		return logs;
	}
	
	public List<MemberLog> getMentorPendingMentorRequests(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){
			if(log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase()) &&
					log.getUserDefinedValues().getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_MENTOR)){
				logs.add(log);
			}

			
		}
		return logs;
	}	
	public List<MemberLog> getMentorPendingLeadMentorRequests(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){
			if(log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_LEAD_MENTOR_REQUEST.toLowerCase()) &&
					log.getUserDefinedValues().getUdValuesCategory().equals(CommonConstants.ACTIVITY_LOG_MENTOR)){
				logs.add(log);
			}

			
		}
		return logs;
	}
	
	/**
	 * @param memberId
	 * @return
	 */
	public List<MemberLog> getMentorPendingActivities(Long memberId) {
		String clause = " upper(t.status) = ?1 and t.toMemberId=?2";
		List<MemberLog> memberLogs =  memberLogDao.loadByClause(clause, new Object[]{CommonConstants.ACTIVITY_STATUS.PENDING.toString(),memberId});
		List<MemberLog> logs = new ArrayList<MemberLog>();

		for(MemberLog log: memberLogs){
			if(!log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase()) 
					&& !log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_PROTEGE_REQUEST.toLowerCase())
					&& !log.getUserDefinedValues().getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_LEAD_MENTOR_REQUEST.toLowerCase())
					){
				logs.add(log);
			}

			
		}
		return logs;
	}
	

	public List<MemberLog> getAllMentorReleaseLog(Long memberId,Long logTypeId) {
		String clause = " t.memberActivityType = ?1 and t.fromMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{logTypeId,memberId});
	}



	public List<MemberLog> getAllProtegeReleaseLog(Long memberId,Long logTypeId) {
		String clause = " t.memberActivityType = ?1 and t.toMemberId=?2";
		return memberLogDao.loadByClause(clause, new Object[]{logTypeId,memberId});
	}



	public List<MemberLog> getMemberConversation(Long memberId) {
		String clause = " t.fromMemberId=?1  OR t.toMemberId=?2 ORDER BY t.memberLogDateTime DESC ";
		return memberLogDao.loadByClause(clause, new Object[]{memberId,memberId});
	}



	public List<MemberLog> getRecentConversation(Long memberId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		String clause = " (t.fromMemberId=?1 or  t.toMemberId=?1) and t.memberLogDateTime > ?2 ";
		List<MemberLog> logs = memberLogDao.loadByClause(clause, new Object[]{memberId,cal.getTime()});
		return logs;
	}
	

  public Integer deleteMemLogByFrMemId(Long memberId) {
    String clause = " t.fromMemberId=?1 ";
    return memberLogDao.deleteByClause(clause, new Object[] {memberId}); 
  }
	

  public Integer deleteMemLogByToMemId(Long memberId) {
    String clause = " t.toMemberId=?1 ";
    return memberLogDao.deleteByClause(clause, new Object[] {memberId}); 
  }
	
	@Autowired
	public void setCacheMan(CachingManager cacheMan) {
		this.cacheMan = cacheMan;
	}	
	
}
