package com.example.schedule.dao;

import com.example.schedule.entity.*;

public interface TaskReminderDao {
	TaskReminder save(TaskReminder taskReminder);

	void deleteById(int id);
}