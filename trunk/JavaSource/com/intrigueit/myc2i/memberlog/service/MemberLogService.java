package com.intrigueit.myc2i.memberlog.service;

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
	
	public List<MemberLog> getAllCompletedLog(Long memberId);
	
	public List<MemberLog> getAllCompletedLog();
	
	public List<MemberLog> findAll();
	
	public List<MemberLog> findByProperties(String fromDate, String toDate);
	
	public Boolean isInActiveMember(Long memberId);
}
