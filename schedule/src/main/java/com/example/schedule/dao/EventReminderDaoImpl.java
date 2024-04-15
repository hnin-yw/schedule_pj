package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class EventReminderDaoImpl implements EventReminderDao {

	private EntityManager entityManager;

	@Autowired
	public EventReminderDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// add to the database
	@Override
	public EventReminder save(EventReminder eventReminder) {
		EventReminder eventReminderdb = entityManager.merge(eventReminder);
		eventReminder.setId(eventReminderdb.getId());
		return eventReminder;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from EventReminder where id=:eventReminderId");
		q.setParameter("eventReminderId", id);
		q.executeUpdate();
	}
}