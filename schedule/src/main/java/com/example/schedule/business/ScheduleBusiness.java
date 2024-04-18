package com.example.schedule.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.schedule.entity.*;
import com.example.schedule.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class ScheduleBusiness {
	private final ScheduleService scheduleService;
	private final ScheduleReminderService scheduleReminderService;

	@Autowired
	public ScheduleBusiness(ScheduleService scheduleService, ScheduleReminderService scheduleReminderService) {
		this.scheduleService = scheduleService;
		this.scheduleReminderService = scheduleReminderService;
	}

	public List<Schedule> list() {
		List<Schedule> listSchedules = scheduleService.findAlls();
		return listSchedules;
	}

	public Schedule saveSchedule(Schedule schedule) {

		schedule.setScheduleStartDateTime(toDateTime(schedule.getStartDateTimeString()));
		schedule.setScheduleEndDateTime(toDateTime(schedule.getEndDateTimeString()));
		schedule.setScheduleEndDateTime(toDateTime(schedule.getRepeatUntilDateTimeString()));

		Schedule scheduleDb = scheduleService.save(schedule);

		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders.size() > 0) {
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				reminder.setScheduleId(scheduleDb.getId());
				scheduleReminderService.save(reminder);
			}
		}

		return scheduleDb;
	}

	public Schedule findScheduleById(int id) {
		Schedule schedule = scheduleService.findScheduleById(id);
		return schedule;
	}

	public String updateSchedule(Schedule schedule) {
		Schedule updSchedule = scheduleService.findScheduleById(schedule.getId());
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		scheduleService.save(updSchedule);

		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders.size() > 0) {
			scheduleReminderService.deleteBySchedulId(updSchedule.getId());
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				reminder.setScheduleId(updSchedule.getId());
				scheduleReminderService.save(reminder);
			}
		}
		return "redirect:/schedules";
	}

	public String updateScheduleStatus(Schedule schedule) {
		Schedule updSchedule = scheduleService.findScheduleById(schedule.getId());
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		updSchedule.setScheduleStatusFlg(true);
		scheduleService.save(updSchedule);
		return "redirect:/schedules";
	}

	public String deleteSchedule(@PathVariable int id) {
		Schedule schedule = scheduleService.findScheduleById(id);
		if (schedule == null) {
			throw new RuntimeException("Schedule Id not found.");
		}
		scheduleReminderService.deleteBySchedulId(schedule.getId());
		scheduleService.deleteById(id);
		return "redirect:/schedules";
	}

	public void exportUsersToExcel() {
		List<Schedule> schedules = scheduleService.findAlls();

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("schedules");

			// Create a header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Name");

			// Create data rows
			int rowNum = 1;
			for (Schedule schedule : schedules) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(schedule.getId());
				row.createCell(1).setCellValue(schedule.getScheduleTitle());
			}

			// Write the workbook to a file
			try (FileOutputStream fileOut = new FileOutputStream("schedules.xlsx")) {
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LocalDateTime toDateTime(String strDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime = LocalDateTime.parse(strDateTime, formatter);

		return dateTime;
	}
}