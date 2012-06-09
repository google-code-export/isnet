package com.intrigueit.myc2i.event.service;

import java.util.List;

import com.intrigueit.myc2i.event.domain.Event;

public interface EventService {

	public List<Event> getEventList();
	
	public Event getEventById(String id);
	
}
