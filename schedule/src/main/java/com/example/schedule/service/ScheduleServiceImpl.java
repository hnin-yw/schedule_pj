package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.*;
import com.example.schedule.entity.*;

import jakarta.transaction.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	ScheduleDao scheduleDao;

	@Autowired
	public ScheduleServiceImpl(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	@Override
	public List<Schedule> findAlls() {
		return scheduleDao.getAlls();
	}

	@Override
	public Schedule findScheduleById(int id) {
		return scheduleDao.findScheduleById(id);
	}

	@Override
	@Transactional
	public Schedule save(Schedule schedule) {
		return scheduleDao.save(schedule);
	}

	@Override
	@Transactional
	public int deleteById(int id) {
		scheduleDao.deleteById(id);
		return id;
	}

	@Override
	public String findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}