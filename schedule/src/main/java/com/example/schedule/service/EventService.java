package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface EventService {
	public List<Event> findAlls();

	public String findById(int id);

	public Event findEventById(int id);

	public Event save(Event event);

	public int deleteById(int id);
}