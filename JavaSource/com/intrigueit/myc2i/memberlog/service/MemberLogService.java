package com.intrigueit.myc2i.memberlog.service;

import java.util.List;

import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.domain.MemberLogPK;

public interface MemberLogService {
	
    public void save(MemberLog entity);

    public void delete(MemberLog entity);

	public MemberLog update(MemberLog entity);
	
	public MemberLog findById( MemberLogPK id);

	public List<MemberLog> findByProperty(String propertyName, Object value);
	
	public List<MemberLog> getAllPendingLog();
	
	public List<MemberLog> getAllCompletedLog();
	
	public List<MemberLog> findAll();	
}
