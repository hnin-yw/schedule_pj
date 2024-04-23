package com.example.schedule.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.example.schedule.entity.*;

public interface ScheduleDao {
	List<Schedule> findAlls(String userCode, String groupCode, LocalDateTime startDateTime, LocalDateTime endDateTime);

	Schedule findScheduleById(int id);

	Schedule save(Schedule schedule);

	void deleteById(int id);

	List<Schedule> findScheduleListByUserCode(String userCode);

	List<Schedule> findSelectedAlls(Integer[] selectedIds);
}