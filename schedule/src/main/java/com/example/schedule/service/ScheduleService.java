package com.example.schedule.service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.schedule.entity.*;

public interface ScheduleService {
	public List<Schedule> findAlls(String userCode, String groupCode, LocalDateTime startDateTime, LocalDateTime endDateTime);

	public String findById(int id);

	public Schedule findScheduleById(int id);

	public Schedule save(Schedule schedule);

	public int deleteById(int id);

	public List<Schedule> findScheduleListByUserCode(String userCode);

	List<Schedule> findSelectedAlls(Integer[] selectedIds);
}