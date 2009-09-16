package com.intrigueit.myc2i.member.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.member.dao.MemberExDao;
import com.intrigueit.myc2i.member.domain.Member;



@Service
public class MemberExServiceImpl implements MemberExService{
  protected static final Logger logger = Logger.getLogger( MemberExServiceImpl.class );
	private MemberExDao memberExDao;
	
	@Autowired
	public MemberExServiceImpl(MemberExDao memberExDao) {
		this.memberExDao = memberExDao;
	}
	
	public List<Member> findByProperties(SearchBean searchBean) {
    StringBuffer clause = new StringBuffer();
    clause.append(" from MEMBER ");
    boolean useWhere = true;
    if (searchBean.getRecordId()!= null && searchBean.getRecordId() != 0) {
      clause.append(" where typeId = "+searchBean.getRecordId());
      useWhere = false;
    }  
    
    if (searchBean.getEmail()!= null && !searchBean.getEmail().isEmpty()) {
      clause = ( useWhere ) ?  clause.append(" where "):clause.append(" and ");
      clause.append(" upper(email) like upper('%"+searchBean.getEmail()+"%')");
    }
    if (searchBean.getFirstName()!= null && !searchBean.getFirstName().isEmpty()) {
      clause = ( useWhere ) ?  clause.append(" where "):clause.append(" and ");
      clause.append(" upper(firstName) like upper('%"+searchBean.getFirstName()+"%')");
    }
    if (searchBean.getLastName()!= null && !searchBean.getLastName().isEmpty()) {
      clause = ( useWhere ) ?  clause.append(" where "):clause.append(" and ");
      clause.append(" upper(lastName) like upper('%"+searchBean.getLastName()+"%')");
    }
    if (searchBean.getCity()!= null && !searchBean.getCity().isEmpty()) {
      clause = ( useWhere ) ?  clause.append(" where "):clause.append(" and ");
      clause.append(" upper(city) like upper('%"+searchBean.getCity()+"%')");
    }
    if (searchBean.getState()!= null && !searchBean.getState().isEmpty()) {
      clause = ( useWhere ) ?  clause.append(" where "):clause.append(" and ");
      clause.append(" upper(state) like upper('%"+searchBean.getState()+"%')");
    }
    logger.debug("HSQL::::"+clause.toString());
    return memberExDao.findByProperties(clause.toString());
  }
}
