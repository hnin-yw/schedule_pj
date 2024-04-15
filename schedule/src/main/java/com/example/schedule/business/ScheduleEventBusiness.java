package com.example.schedule.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.EventService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class ScheduleEventBusiness {
	private final EventService eventService;

	@Autowired
	public ScheduleEventBusiness(EventService eventService) {
		this.eventService = eventService;
	}

	public Event saveEvent(Event event) {
		return eventService.save(event);
	}

	public String updateEvent(Event event) {
		Event updEvent = eventService.findEventById(event.getId());
		if (updEvent == null) {
			throw new RuntimeException("Event to update doesn't exist");
		}
		updEvent.setEventTitle(event.getEventTitle());
		eventService.save(updEvent);
		return "redirect:/events";
	}

	public String deleteEvent(@PathVariable int id) {
		Event event = eventService.findEventById(id);
		if (event == null) {
			throw new RuntimeException("Event Id not found");
		}
		eventService.deleteById(id);
		return "redirect:/events";
	}
}