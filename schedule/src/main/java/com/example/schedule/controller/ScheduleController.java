package com.example.schedule.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schedule.business.*;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@RequestMapping("")
	public ModelAndView calendar() {
		return new ModelAndView("schedules/list");
	}

	@GetMapping(value = "/api/all", produces = "application/json")
    @ResponseBody
    public List<Schedule> getSchedules() {
        List<Schedule> schedules = scheduleBusiness.getAllSchedules();
        return schedules;
    }

	@RequestMapping("/more-options")
	public String moreOptions(Model model) {
		List<User> userLists = userBusiness.getUserLists();
		model.addAttribute("userLists", userLists);
		return "schedules/more-options";
	}

	@RequestMapping("/guest_lists/{id}")
	public String guestListsBySchedule(@PathVariable(name = "id") int id, Model model) {
		Schedule schedule = scheduleBusiness.findScheduleById(id);
		model.addAttribute("schedule", schedule);
		return "schedules/guest_lists";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("schedule") Schedule schedule, RedirectAttributes atts) {
		scheduleBusiness.saveSchedule(schedule);

		atts.addFlashAttribute("susMsg", "スケジュールが正常に保存されました。");
		return "redirect:/schedules";
	}

	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveData(@RequestBody Schedule schedule) {
		scheduleBusiness.saveSchedule(schedule);

		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールが正常に保存されました。");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
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
	public String update(@ModelAttribute("schedule") Schedule schedule, RedirectAttributes atts) {
		scheduleBusiness.updateSchedule(schedule);

		atts.addFlashAttribute("susMsg", "スケジュールが正常に更新されました。");
		return "redirect:/schedules"; // Redirect to the URL mapped to "/schedules"
	}

	@RequestMapping(value = "/status/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateScheduleStatus(@RequestParam int id) {
		scheduleBusiness.updateScheduleStatus(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールのステータスが正常に更新されました。"); // Corrected message
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		scheduleBusiness.deleteScheduleById(id);
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
	public ResponseEntity<?> deleteByCode(@PathVariable("scheduleCode") String scheduleCode) {
		scheduleBusiness.deleteScheduleByCode(scheduleCode);

		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールは正常に削除されました。");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deleteById/{scheduleCode}")
	public ResponseEntity<?> deleteById(@PathVariable("scheduleCode") int scheduleCode) {
		scheduleBusiness.deleteScheduleById(scheduleCode);

		Map<String, String> response = new HashMap<>();
		response.put("message", "スケジュールは正常に削除されました。");
		return ResponseEntity.ok(response);
	}
}