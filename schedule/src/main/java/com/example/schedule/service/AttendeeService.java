package com.example.schedule.service;

import com.example.schedule.entity.*;

public interface AttendeeService {

	public Attendee save(Attendee attendee);

	public int deleteBySchedulId(int scheduleId);

	Attendee findAttendeeById(int id);

	Attendee saveAttendee(Attendee attendee);
}