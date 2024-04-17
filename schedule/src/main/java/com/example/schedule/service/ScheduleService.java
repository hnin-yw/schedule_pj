package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface ScheduleService {
	public List<Schedule> findAlls();

	public String findById(int id);

	public Schedule findScheduleById(int id);

	public Schedule save(Schedule schedule);

	public int deleteById(int id);

	public List<Schedule> findScheduleListByUserCode(String userCode);
}