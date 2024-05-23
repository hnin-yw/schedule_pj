package com.example.schedule.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.*;
import com.example.schedule.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
	private final ScheduleBusiness scheduleBusiness;
	private final UserBusiness userBusiness;

	@Autowired
	public ScheduleController(ScheduleBusiness scheduleBusiness, UserBusiness userBusiness) {
		this.scheduleBusiness = scheduleBusiness;
		this.userBusiness = userBusiness;
	}

	@GetMapping()
	public String list(@RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model) {
		Page<Schedule> listSchedules = scheduleBusiness.list(PageRequest.of(page, 10), request);
		model.addAttribute("listSchedules", listSchedules);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listSchedules.getTotalPages());
		return "schedules/list";
	}

	@RequestMapping("/guest_lists/{id}")
	public String guestListsBySchedule(@PathVariable(name = "id") int id, Model model) {
		Schedule schedule = scheduleBusiness.findScheduleById(id);
		model.addAttribute("schedule", schedule);
		return "schedules/guest_lists";
	}

	@RequestMapping("/create")
	public String create(Model model, HttpServletRequest request) {
		List<User> userLists = userBusiness.getUserLists();
		model.addAttribute("userLists", userLists);
		String userCode = scheduleBusiness.getUserUserCode();
		model.addAttribute("userCode", userCode);

		Schedule schedule = new Schedule();
		schedule.setScheduleThemeColor("#FF4013");

		schedule.setUserCode(userCode);
		String groupCode = scheduleBusiness.getUserGroupCode();
		schedule.setGroupCode(groupCode);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startDateTimeString = LocalDateTime.now().format(formatter);
		schedule.setStartDateTimeString(startDateTimeString);
		String endDateTimeString = LocalDateTime.now().plusHours(1).format(formatter);
		schedule.setEndDateTimeString(endDateTimeString);
		schedule.setAllDayFlg(false);
		schedule.setEventFlg(true);
		schedule.setRepeatType("01");
		schedule.setScheduleCode(scheduleBusiness.getScheduleCode());
		schedule.setCreatedBy(userCode);
		schedule.setUpdatedBy(userCode);
		model.addAttribute("schedule", schedule);

		return "schedules/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("schedule") @Valid Schedule schedule, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			List<User> userLists = userBusiness.getUserLists();
			model.addAttribute("userLists", userLists);
			String userCode = scheduleBusiness.getUserGroupCode();
			model.addAttribute("userCode", userCode);
			return "schedules/create";
		} else {
			scheduleBusiness.saveSchedule(schedule, request);
			return "redirect:/schedules";
		}
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedules/edit");

		Schedule schedule = scheduleBusiness.findScheduleById(id);
		schedule = scheduleBusiness.setDatas(schedule);
		mav.addObject("schedule", schedule);

		List<User> userLists = userBusiness.getUserLists();
		mav.addObject("userLists", userLists);
		String userCode = scheduleBusiness.getUserUserCode();
		mav.addObject("userCode", userCode);
		schedule.setUpdatedBy(userCode);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @ModelAttribute("schedule") @Valid Schedule schedule, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			List<User> userLists = userBusiness.getUserLists();
			model.addAttribute("userLists", userLists);
			String userCode = scheduleBusiness.getUserUserCode();
			model.addAttribute("userCode", userCode);

			return "schedules/edit";
		} else {
			scheduleBusiness.updateSchedule(schedule, request);
			return "redirect:/schedules";
		}
	}

	@RequestMapping(value = "/status/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateScheduleStatus(@RequestParam int id, HttpServletRequest request) {
		scheduleBusiness.updateScheduleStatus(id, request);
		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールのステータスが正常に更新されました。"); // Corrected message
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, HttpServletRequest request) {
		scheduleBusiness.deleteScheduleById(id, request);
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

	@DeleteMapping("/deleteByCode/{scheduleCode}")
	public ResponseEntity<?> deleteByCode(String scheduleCode, HttpServletRequest request) {
		scheduleBusiness.deleteScheduleByCode(scheduleCode, request);

		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールは正常に削除されました。");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deleteById/{scheduleCode}")
	public ResponseEntity<?> deleteById(int scheduleCode, HttpServletRequest request) {
		scheduleBusiness.deleteScheduleById(scheduleCode, request);

		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールは正常に削除されました。");
		return ResponseEntity.ok(response);
	}
}