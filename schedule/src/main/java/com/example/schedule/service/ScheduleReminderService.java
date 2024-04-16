package com.example.schedule.service;

import com.example.schedule.entity.*;

public interface ScheduleReminderService {

	public ScheduleReminder save(ScheduleReminder scheduleReminder);

	public int deleteById(int id);

	ScheduleReminder findScheduleReminderById(int id);
}