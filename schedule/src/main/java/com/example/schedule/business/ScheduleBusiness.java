package com.example.schedule.business;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

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
		Schedule scheduleDb = scheduleService.save(schedule);

		ScheduleReminder reminder = schedule.getScheduleReminder();
		reminder.setScheduleId(scheduleDb.getId());
		scheduleReminderService.save(reminder);

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
		return "redirect:/schedules";
	}

	public String deleteSchedule(@PathVariable int id) {
		Schedule schedule = scheduleService.findScheduleById(id);
		if (schedule == null) {
			throw new RuntimeException("Schedule Id not found.");
		}
		ScheduleReminder reminder = scheduleReminderService
				.findScheduleReminderById(schedule.getScheduleReminder().getId());
		if (reminder != null) {
			throw new RuntimeException("Cannot delete Schedule because other table use.");
		}
		scheduleReminderService.deleteById(schedule.getScheduleReminder().getId());
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
}