package com.intrigueit.myc2i.memberlog.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.memberlog.domain.MemberLog;
import com.intrigueit.myc2i.memberlog.domain.MemberLogPK;

@Repository
@Transactional
public class MemberLogDaoImpl extends GenericDaoImpl<MemberLog,MemberLogPK> implements MemberLogDao{


}
