package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {

	private EntityManager entityManager;

	@Autowired
	public EventDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAlls() {
		Query q = (Query) entityManager.createQuery("from Event");
		List<Event> transactions = q.getResultList();

		return transactions;
	}

	@Override
	public Event findEventById(int id) {
		Event event = entityManager.find(Event.class, id);
		return event;
	}

	// add to the database
	@Override
	public Event save(Event event) {
		Event eventdb = entityManager.merge(event);
		event.setId(eventdb.getId());
		return event;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from Event where id=:eventId");
		q.setParameter("eventId", id);
		q.executeUpdate();
	}
}