package com.example.schedule.dao;

import com.example.schedule.entity.*;

public interface EventReminderDao {
	EventReminder save(EventReminder eventReminder);

	void deleteById(int id);
}