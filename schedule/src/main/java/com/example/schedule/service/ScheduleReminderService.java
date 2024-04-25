package com.example.schedule.service;

import com.example.schedule.entity.*;

public interface ScheduleReminderService {

	public ScheduleReminder save(ScheduleReminder scheduleReminder);

	public int deleteBySchedulId(int scheduleId);

	ScheduleReminder findScheduleReminderById(int id);
}