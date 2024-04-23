package com.example.schedule.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.ScheduleBusiness;
import com.example.schedule.entity.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
	private final ScheduleBusiness scheduleBusiness;

	@Autowired
	public ScheduleController(ScheduleBusiness scheduleBusiness) {
		this.scheduleBusiness = scheduleBusiness;
	}

	@GetMapping()
	public String list(HttpServletRequest request, Model model) {
		List<Schedule> listSchedules = scheduleBusiness.list(request);
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
	public String save(@ModelAttribute("schedule") Schedule schedule, BindingResult result, Model model,
			HttpServletRequest request) {
		scheduleBusiness.saveSchedule(schedule, request);
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
	public String update(Schedule Schedule, HttpServletRequest request) {
		scheduleBusiness.updateSchedule(Schedule, request);
		return "redirect:/schedules";
	}

	@RequestMapping(value = "status/update", method = RequestMethod.POST)
	public String updateScheduleStatus(Schedule Schedule, HttpServletRequest request) {
		scheduleBusiness.updateScheduleStatus(Schedule, request);
		return "redirect:/schedules";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, HttpServletRequest request) {
		scheduleBusiness.deleteSchedule(id, request);
		return "redirect:/schedules";
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> downloadExcel(String[] selectedIds) throws IOException {
		byte[] excelBytes = scheduleBusiness.generateExcelBytes(selectedIds);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(
				MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.setContentDispositionFormData("attachment", "schedules.xlsx");

		return ResponseEntity.ok().headers(headers).body(excelBytes);
	}
}