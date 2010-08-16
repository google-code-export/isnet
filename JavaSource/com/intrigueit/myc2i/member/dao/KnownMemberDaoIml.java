package com.intrigueit.myc2i.member.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.member.domain.KnownMember;

@Repository
@Transactional
public class KnownMemberDaoIml extends GenericDaoImpl<KnownMember, Long> implements KnownMemberDao {

}
