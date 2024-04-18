package com.example.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.ScheduleReminderDao;
import com.example.schedule.entity.*;
import jakarta.transaction.Transactional;

@Service
public class ScheduleReminderServiceImpl implements ScheduleReminderService {

	ScheduleReminderDao ScheduleReminderDao;

	@Autowired
	public ScheduleReminderServiceImpl(ScheduleReminderDao ScheduleReminderDao) {
		this.ScheduleReminderDao = ScheduleReminderDao;
	}

	@Override
	@Transactional
	public ScheduleReminder save(ScheduleReminder scheduleReminder) {
		return ScheduleReminderDao.save(scheduleReminder);
	}

	@Override
	public ScheduleReminder findScheduleReminderById(int id) {
		return ScheduleReminderDao.findScheduleReminderById(id);
	}

	@Override
	@Transactional
	public int deleteBySchedulId(int scheduleId) {
		ScheduleReminderDao.deleteBySchedulId(scheduleId);
		return scheduleId;
	}
}