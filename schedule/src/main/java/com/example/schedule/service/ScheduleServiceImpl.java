package com.example.schedule.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<Schedule> findAllSchedules(String userCode, String groupCode) {
		return scheduleDao.findAllSchedules(userCode, groupCode);
	}

	@Override
	public Page<Schedule> findAlls(Pageable pageable, String userCode, String groupCode) {
		return scheduleDao.findAlls(pageable, userCode, groupCode);
	}

	@Override
	public List<Schedule> findSelectedAlls(Integer[] selectedIds) {
		return scheduleDao.findSelectedAlls(selectedIds);
	}

	@Override
	public Schedule findScheduleById(int id) {
		return scheduleDao.findScheduleById(id);
	}

	@Override
	public List<Schedule> findScheduleListByScheduleCode(String scheduleCode) {
		return scheduleDao.findScheduleListByScheduleCode(scheduleCode);
	}

	@Override
	@Transactional
	public Schedule save(Schedule schedule) {
		return scheduleDao.save(schedule);
	}

	@Override
	@Transactional
	public int saveSchedule(Schedule schedule) {
		return scheduleDao.saveSchedule(schedule);
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

	@Override
	public List<Schedule> findScheduleListByUserCode(String userCode) {
		return scheduleDao.findScheduleListByUserCode(userCode);
	}

	@Override
	@Transactional
	public Schedule findScheduleCodeByDesc() {
		return scheduleDao.findScheduleCodeByDesc();
	}
}