package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.schedule.business.ScheduleEventBusiness;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/schedules/events")
public class ScheduleEventController {
	private final ScheduleEventBusiness scheduleEventBusiness;

	@Autowired
	public ScheduleEventController(ScheduleEventBusiness scheduleEventBusiness) {
		this.scheduleEventBusiness = scheduleEventBusiness;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("event") @Validated Event event, BindingResult result, Model model) {
		scheduleEventBusiness.saveEvent(event);
		return "redirect:/ScheduleEvents";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Event event) {
		scheduleEventBusiness.updateEvent(event);
		return "redirect:/ScheduleEvents";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return "redirect:/ScheduleEvents";
	}
}