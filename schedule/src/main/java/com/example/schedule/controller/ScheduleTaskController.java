package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.schedule.business.ScheduleTaskBusiness;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/schedules/tasks")
public class ScheduleTaskController {
	private final ScheduleTaskBusiness scheduleTaskBusiness;

	@Autowired
	public ScheduleTaskController(ScheduleTaskBusiness scheduleTaskBusiness) {
		this.scheduleTaskBusiness = scheduleTaskBusiness;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("task") @Validated Task task, BindingResult result, Model model) {
		scheduleTaskBusiness.saveTask(task);
		return "redirect:/tasks";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Task task) {
		scheduleTaskBusiness.updateTask(task);
		return "redirect:/tasks";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		return "redirect:/tasks";
	}
}