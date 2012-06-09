package com.intrigueit.myc2i.event.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.event.dao.EventDao;
import com.intrigueit.myc2i.event.domain.Event;

@Service
public class EventServiceImpl implements EventService {

	@Autowired EventDao eventDao;
	
	public List<Event> getEventList() {
		List<Event> events = new ArrayList<Event>();
		events = eventDao.loadAll();

		return events;
	}

	public Event getEventById(String id) {
		return eventDao.loadById(Long.parseLong(id));
	}

}
