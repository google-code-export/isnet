package com.intrigueit.myc2i.memberlog.service;

import java.util.Date;
import java.util.List;

import com.intrigueit.myc2i.memberlog.domain.MemberLog;

public interface MemberLogService {
	
    public void save(MemberLog entity);

    public void delete(MemberLog entity);

	public MemberLog update(MemberLog entity);
	
	public MemberLog findById(Long id);

	public List<MemberLog> findByProperty(String propertyName, Object value);
	
	public List<MemberLog> getAllPendingLog();
	
	public List<MemberLog> getAllPendingLog(Long memberId);
	
	public List<MemberLog> getAllProtegePendingRequest(Long memberId);
	
	public List<MemberLog> getAllCompletedLog(Long memberId,Date date);
	
	public List<MemberLog> getAllCompletedLog();
	
	public List<MemberLog> findAll();
	
	public List<MemberLog> findMemberLogByDate(String fromDate, String toDate);
	
	public List<MemberLog> loadMemberLogByActivityType(Long memberId);
	
	public Boolean isInActiveMember(Long memberId);
	
	public List<MemberLog> getAllProtegeReleaseLog(Long memberId);
	
	public List<MemberLog> getAllMentorReleaseLog(Long memberId);
	
	public List<MemberLog> getMemberConversation(Long memberId);
	
	public List<MemberLog> getRecentConversation(Long memberId);
	
	
	
	
}
