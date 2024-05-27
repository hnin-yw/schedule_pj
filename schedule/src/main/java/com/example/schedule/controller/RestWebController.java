package com.example.schedule.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.schedule.business.ScheduleBusiness;
import com.example.schedule.entity.Schedule;

@RestController
@RequestMapping("/api/schedule")
public class RestWebController {
	private final ScheduleBusiness scheduleBusiness;

	@Autowired
	public RestWebController(ScheduleBusiness scheduleBusiness) {
		this.scheduleBusiness = scheduleBusiness;
	}

	/**
	 * Retrieve schedule data to display in the calendar.
	 * 
	 * @return JSON-encoded string representing schedule data.
	 */
	@GetMapping(value = "/allc", produces = "application/json")
	public List<Schedule> getSchedules() {

		System.out.println("RestWebController");
		System.out.println(scheduleBusiness.getUserUserCode());
		List<Schedule> schedules = scheduleBusiness.getAllSchedules();
		return schedules;

	}
}
