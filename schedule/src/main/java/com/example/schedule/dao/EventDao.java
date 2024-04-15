package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface EventDao {
	List<Event> getAlls();

	Event findEventById(int id);

	Event save(Event event);

	void deleteById(int id);
}