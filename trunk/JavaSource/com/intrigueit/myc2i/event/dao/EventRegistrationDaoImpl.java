package com.intrigueit.myc2i.event.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.event.domain.EventRegistration;

@Repository
@Transactional
public class EventRegistrationDaoImpl extends GenericDaoImpl<EventRegistration, Long> implements EventRegistrationDao {

}
