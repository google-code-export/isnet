package com.intrigueit.myc2i.event.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.event.domain.Event;

@Repository
@Transactional
public class EventDaoImpl extends GenericDaoImpl<Event, Long> implements EventDao {

}
