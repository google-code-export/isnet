package com.intrigueit.myc2i.memberlog.dao;

import java.util.List;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;

public interface MemberLogDao extends GenericDao<MemberLog,Long>{
  public List<MemberLog> findByProperties(String hsql);
}
