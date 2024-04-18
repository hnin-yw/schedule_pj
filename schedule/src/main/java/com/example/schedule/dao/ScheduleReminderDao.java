package com.example.schedule.dao;

import com.example.schedule.entity.*;

public interface ScheduleReminderDao {
	ScheduleReminder save(ScheduleReminder scheduleReminder);

	ScheduleReminder findScheduleReminderById(int id);
	
	void deleteBySchedulId(int scheduleId);
}