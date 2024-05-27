package com.example.schedule.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface ScheduleDao {
	// List<Schedule> findAlls(String userCode, String groupCode, LocalDateTime
	// startDateTime, LocalDateTime endDateTime);

	Schedule findScheduleById(int id);

	Schedule save(Schedule schedule);

	void deleteById(int id);

	List<Schedule> findScheduleListByUserCode(String userCode);

	List<Schedule> findSelectedAlls(Integer[] selectedIds);

	Page<Schedule> findAlls(Pageable pageable, String userCode, String groupCode);

	List<Schedule> findAllSchedules(String userCode, String groupCode);

	Schedule findScheduleCodeByDesc();

	int saveSchedule(Schedule schedule);

	List<Schedule> findScheduleListByScheduleCode(String scheduleCode);
}