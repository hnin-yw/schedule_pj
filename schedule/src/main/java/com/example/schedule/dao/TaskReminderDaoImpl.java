package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class TaskReminderDaoImpl implements TaskReminderDao {

	private EntityManager entityManager;

	@Autowired
	public TaskReminderDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// add to the database
	@Override
	public TaskReminder save(TaskReminder taskReminder) {
		TaskReminder taskReminderdb = entityManager.merge(taskReminder);
		taskReminder.setId(taskReminderdb.getId());
		return taskReminder;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from TaskReminder where id=:taskReminderId");
		q.setParameter("taskReminderId", id);
		q.executeUpdate();
	}
}