package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.EventDao;
import com.example.schedule.entity.*;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService {

	EventDao eventDao;

	@Autowired
	public EventServiceImpl(EventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	public List<Event> findAlls() {
		return eventDao.getAlls();
	}

	@Override
	public Event findEventById(int id) {
		return eventDao.findEventById(id);
	}

	@Override
	@Transactional
	public Event save(Event event) {
		return eventDao.save(event);
	}

	@Override
	@Transactional
	public int deleteById(int id) {
		eventDao.deleteById(id);
		return id;
	}

	@Override
	public String findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}