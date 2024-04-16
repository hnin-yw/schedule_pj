package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface ScheduleDao {
	List<Schedule> getAlls();

	Schedule findScheduleById(int id);

	Schedule save(Schedule schedule);

	void deleteById(int id);
}