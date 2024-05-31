package com.example.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.AttendeeDao;
import com.example.schedule.entity.*;
import jakarta.transaction.Transactional;

@Service
public class AttendeeServiceImpl implements AttendeeService {

	AttendeeDao attendeeDao;

	@Autowired
	public AttendeeServiceImpl(AttendeeDao attendeeDao) {
		this.attendeeDao = attendeeDao;
	}

	@Override
	@Transactional
	public Attendee save(Attendee attendee) {
		return attendeeDao.save(attendee);
	}

	@Override
	@Transactional
	public Attendee saveAttendee(Attendee attendee) {
		return attendeeDao.saveAttendee(attendee);
	}

	@Override
	public Attendee findAttendeeById(int id) {
		return attendeeDao.findAttendeeById(id);
	}

	@Override
	@Transactional
	public int deleteBySchedulId(int scheduleId) {
		attendeeDao.deleteBySchedulId(scheduleId);
		return scheduleId;
	}

	@Override
	@Transactional
	public int deleteBySchedulIdAndUserCode(int scheduleId, String userCode) {
		attendeeDao.deleteBySchedulIdAndUserCode(scheduleId, userCode);
		return scheduleId;
	}
}