package com.example.schedule.dao;

import com.example.schedule.entity.*;

public interface AttendeeDao {
	Attendee save(Attendee attendee);

	Attendee findAttendeeById(int id);

	void deleteBySchedulId(int scheduleId);

	void deleteBySchedulIdAndUserCode(int scheduleId, String userCode);

	Attendee saveAttendee(Attendee attendee);
}