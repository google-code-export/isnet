package com.intrigueit.myc2i.member.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.common.domain.SearchBean;
import com.intrigueit.myc2i.member.domain.Member;


public interface MemberExService {  
	public List<Member> findByProperties(SearchBean searchBean);
}