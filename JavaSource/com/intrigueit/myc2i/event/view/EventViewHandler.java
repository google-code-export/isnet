package com.intrigueit.myc2i.event.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.event.domain.Event;
import com.intrigueit.myc2i.event.service.EventService;

@Component("eventViewHandler")
@Scope("session")
public class EventViewHandler extends BasePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Autowired EventService eventService;
	
	private List<Event> events;

	public List<Event> getEvents() {
		
		this.events = this.eventService.getEventList();
		
		return events;
	}
	
	public String getEventName(){
		String name = "MyC2i Event Registration";
		try{
			String id = (String) this.getSession().getAttribute("event");
			name = eventService.getEventById(id).getName();
		}
		catch(Exception ex){
			
		}
		return name;
	}
	
	public String getEventId(){
		return this.getRequest().getParameter("EVENT_ID");
	}
}
