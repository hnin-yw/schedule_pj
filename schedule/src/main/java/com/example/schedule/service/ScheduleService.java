package com.example.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface ScheduleService {
	// public List<Schedule> findAlls(String userCode, String groupCode,
	// LocalDateTime startDateTime, LocalDateTime endDateTime);

	public Page<Schedule> findAlls(Pageable pageable, String userCode, String groupCode);

	public String findById(int id);

	public Schedule findScheduleById(int id);

	public Schedule save(Schedule schedule);

	public int deleteById(int id);

	public List<Schedule> findScheduleListByUserCode(String userCode);

	List<Schedule> findSelectedAlls(Integer[] selectedIds);

	Schedule findScheduleCodeByDesc();

	int saveSchedule(Schedule schedule);
}