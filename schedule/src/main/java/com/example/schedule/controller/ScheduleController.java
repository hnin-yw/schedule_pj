package com.example.schedule.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.ScheduleBusiness;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
	private final ScheduleBusiness scheduleBusiness;

	@Autowired
	public ScheduleController(ScheduleBusiness scheduleBusiness) {
		this.scheduleBusiness = scheduleBusiness;
	}

	@GetMapping()
	public String list(Model model) {
		List<Schedule> listSchedules = scheduleBusiness.list();
		model.addAttribute("listSchedules", listSchedules);
		return "schedules/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		Schedule schedule = new Schedule();
		model.addAttribute("schedule", schedule);

		return "schedules/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("schedule") Schedule schedule,
			BindingResult result, Model model) {
		scheduleBusiness.saveSchedule(schedule);
		return "redirect:/schedules";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("schedules/edit");
		Schedule schedule = scheduleBusiness.findScheduleById(id);
		mav.addObject("schedule", schedule);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Schedule Schedule) {
		scheduleBusiness.updateSchedule(Schedule);
		return "redirect:/schedules";
	}

	@RequestMapping(value = "status/update", method = RequestMethod.POST)
	public String updateScheduleStatus(Schedule Schedule) {
		scheduleBusiness.updateSchedule(Schedule);
		return "redirect:/schedules";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		scheduleBusiness.deleteSchedule(id);
		return "redirect:/schedules";
	}

	@RequestMapping("/download")
	public String download(Model model) {
		scheduleBusiness.exportUsersToExcel();
		return "redirect:/schedules";
	}
}