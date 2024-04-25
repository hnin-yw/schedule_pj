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

	// add to the database
	@Override
	public ScheduleReminder saveScheduleReminder(ScheduleReminder scheduleReminder) {
		Query query = entityManager.createQuery(
				"INSERT INTO ScheduleReminder (scheduleId, scheduleReminderTime, scheduleReminderType, notiMethodFlg, delFlg, createdBy, createdAt, updatedBy, updatedAt) "
						+ "VALUES (:scheduleId, :scheduleReminderTime, :scheduleReminderType, :notiMethodFlg, :delFlg, :createdBy, :createdAt, :updatedBy, :updatedAt)");
		query.setParameter("scheduleId", scheduleReminder.getScheduleId());
		query.setParameter("scheduleReminderTime", scheduleReminder.getScheduleReminderTime());
		query.setParameter("scheduleReminderType", scheduleReminder.getScheduleReminderType());
		query.setParameter("notiMethodFlg", scheduleReminder.getNotiMethodFlg());
		query.setParameter("delFlg", scheduleReminder.getDelFlg());
		query.setParameter("createdBy", scheduleReminder.getCreatedBy());
		query.setParameter("createdAt", scheduleReminder.getCreatedAt());
		query.setParameter("updatedBy", scheduleReminder.getUpdatedBy());
		query.setParameter("updatedAt", scheduleReminder.getUpdatedAt());
		query.executeUpdate();
		return scheduleReminder;
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