package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class ScheduleReminderDaoImpl implements ScheduleReminderDao {

	private EntityManager entityManager;

	@Autowired
	public ScheduleReminderDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// add to the database
	@Override
	public ScheduleReminder save(ScheduleReminder ScheduleReminder) {
		ScheduleReminder ScheduleReminderdb = entityManager.merge(ScheduleReminder);
		ScheduleReminder.setId(ScheduleReminderdb.getId());
		return ScheduleReminder;
	}

	@Override
	public ScheduleReminder findScheduleReminderById(int id) {
		ScheduleReminder scheduleReminder = entityManager.find(ScheduleReminder.class, id);
		return scheduleReminder;
	}

	@Override
	public void deleteBySchedulId(int scheduleId) {
		Query q = (Query) entityManager.createQuery("delete from ScheduleReminder where scheduleId=:scheduleId");
		q.setParameter("scheduleId", scheduleId);
		q.executeUpdate();
	}
}