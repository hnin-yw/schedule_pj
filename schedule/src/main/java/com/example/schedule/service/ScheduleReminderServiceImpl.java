package com.example.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.ScheduleReminderDao;
import com.example.schedule.entity.*;
import jakarta.transaction.Transactional;

@Service
public class ScheduleReminderServiceImpl implements ScheduleReminderService {

	ScheduleReminderDao scheduleReminderDao;

	@Autowired
	public ScheduleReminderServiceImpl(ScheduleReminderDao scheduleReminderDao) {
		this.scheduleReminderDao = scheduleReminderDao;
	}

	@Override
	@Transactional
	public ScheduleReminder save(ScheduleReminder scheduleReminder) {
		return scheduleReminderDao.save(scheduleReminder);
	}

	@Override
	@Transactional
	public ScheduleReminder saveScheduleReminder(ScheduleReminder scheduleReminder) {
		return scheduleReminderDao.saveScheduleReminder(scheduleReminder);
	}

	@Override
	public ScheduleReminder findScheduleReminderById(int id) {
		return scheduleReminderDao.findScheduleReminderById(id);
	}

	@Override
	@Transactional
	public int deleteBySchedulId(int scheduleId) {
		scheduleReminderDao.deleteBySchedulId(scheduleId);
		return scheduleId;
	}
}