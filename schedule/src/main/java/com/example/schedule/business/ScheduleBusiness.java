package com.example.schedule.business;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.usermodel.Workbook;

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

	public Page<Schedule> list(Pageable pageable, HttpServletRequest request) {
//		LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIN);
//		LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),
//				LocalTime.MAX);
		String userCode = getUserUserCode(request);
		String groupCode = getUserGroupCode(request);
		Page<Schedule> listSchedules = scheduleService.findAlls(pageable, userCode, groupCode);
		return listSchedules;
	}

	public Schedule saveSchedule(Schedule schedule, HttpServletRequest request) {
		schedule.setScheduleStartDateTime(toDateTime(schedule.getStartDateTimeString()));
		schedule.setScheduleEndDateTime(toDateTime(schedule.getEndDateTimeString()));
		schedule.setRepeatUntil(toDateTime(schedule.getRepeatUntilDateTimeString()));
		String userCode = getUserUserCode(request);
		schedule.setUserCode(userCode);
		schedule.setCreatedBy(userCode);
		schedule.setUpdatedBy(userCode);
		String groupCode = getUserGroupCode(request);
		schedule.setGroupCode(groupCode);
		Schedule scheduleDb = scheduleService.save(schedule);
		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders.size() > 0) {
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				reminder.setScheduleId(scheduleDb.getId());
				userCode = getUserUserCode(request);
				reminder.setCreatedBy(userCode);
				reminder.setUpdatedBy(userCode);
				scheduleReminderService.save(reminder);
			}
		}

		return scheduleDb;
	}

	public Schedule findScheduleById(int id) {
		Schedule schedule = scheduleService.findScheduleById(id);
		return schedule;
	}

	public String updateSchedule(Schedule schedule, HttpServletRequest request) {
		Schedule updSchedule = scheduleService.findScheduleById(schedule.getId());
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		updSchedule.setScheduleTitle(schedule.getScheduleTitle());
		updSchedule.setScheduleStartDateTime(toDateTime(schedule.getStartDateTimeString()));
		updSchedule.setScheduleEndDateTime(toDateTime(schedule.getEndDateTimeString()));
		updSchedule.setAllDayFlg(schedule.getAllDayFlg());
		updSchedule.setRepeatType(schedule.getRepeatType());
		updSchedule.setRepeatUntil(toDateTime(schedule.getRepeatUntilDateTimeString()));
		updSchedule.setRepeatInterval(schedule.getRepeatInterval());
		updSchedule.setRepeatIntervalType(schedule.getRepeatIntervalType());
		updSchedule.setRepeatDayOfWeek(schedule.getRepeatDayOfWeek());
		updSchedule.setRepeatDayOfMonth(schedule.getRepeatDayOfMonth());
		updSchedule.setRepeatMonth(schedule.getRepeatMonth());
		updSchedule.setScheduleDisplayFlg(schedule.getScheduleDisplayFlg());
		updSchedule.setLocation(schedule.getLocation());
		updSchedule.setMeetLink(schedule.getMeetLink());
		updSchedule.setScheduleDescription(schedule.getScheduleDescription());
		updSchedule.setScheduleThemeColor(schedule.getScheduleThemeColor());
		updSchedule.setOtherVisibilityFlg(schedule.getOtherVisibilityFlg());
		updSchedule.setEventFlg(schedule.getEventFlg());
		String userCode = getUserUserCode(request);
		updSchedule.setUpdatedBy(userCode);
		scheduleService.save(updSchedule);

		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders.size() > 0) {
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				ScheduleReminder updReminder = scheduleReminderService.findScheduleReminderById(reminder.getId());

				updReminder.setScheduleReminderTime(reminder.getScheduleReminderTime());
				updReminder.setScheduleReminderType(reminder.getScheduleReminderType());
				updReminder.setNotiMethodFlg(reminder.getNotiMethodFlg());
				userCode = getUserUserCode(request);
				updReminder.setUpdatedBy(userCode);
				scheduleReminderService.save(updReminder);
			}
		}
		return "redirect:/schedules";
	}

	public String updateScheduleStatus(Schedule schedule, HttpServletRequest request) {
		Schedule updSchedule = scheduleService.findScheduleById(schedule.getId());
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		updSchedule.setScheduleStatusFlg(true);
		String userCode = getUserUserCode(request);
		updSchedule.setUpdatedBy(userCode);
		scheduleService.save(updSchedule);
		return "redirect:/schedules";
	}

	public String deleteSchedule(@PathVariable int id, HttpServletRequest request) {
		Schedule schedule = scheduleService.findScheduleById(id);
		if (schedule == null) {
			throw new RuntimeException("Schedule Id not found.");
		}
		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders.size() > 0) {
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				reminder.setDelFlg(true);
				String userCode = getUserUserCode(request);
				reminder.setUpdatedBy(userCode);
				scheduleReminderService.save(reminder);
			}
		}
		schedule.setDelFlg(true);
		String userCode = getUserUserCode(request);
		schedule.setUpdatedBy(userCode);
		scheduleService.save(schedule);
		return "redirect:/schedules";
	}

	public byte[] generateExcelBytes(String[] selectedIds) throws IOException {
		Integer[] selectedIdsInt = Arrays.stream(selectedIds).map(Integer::parseInt).toArray(Integer[]::new);
		List<Schedule> schedules = scheduleService.findSelectedAlls(selectedIdsInt);
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("schedules");
		// ヘッダー行を作成。
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Schedule Title");
		headerRow.createCell(1).setCellValue("Schedule Start Date Time");
		headerRow.createCell(2).setCellValue("Schedule End Date Time");
		headerRow.createCell(3).setCellValue("AllDay Flg");
		headerRow.createCell(4).setCellValue("Repeat Type");
		headerRow.createCell(5).setCellValue("Repeat Until");
		headerRow.createCell(6).setCellValue("Schedule Display Flg");
		headerRow.createCell(7).setCellValue("Location");
		headerRow.createCell(8).setCellValue("Meeting Link");
		headerRow.createCell(9).setCellValue("Schedule Description");
		headerRow.createCell(10).setCellValue("Schedule Theme Color");
		headerRow.createCell(11).setCellValue("Other Visibility Flg");
		headerRow.createCell(12).setCellValue("Event Flag");
		headerRow.createCell(13).setCellValue("Schedule Status Flag");
		headerRow.createCell(14).setCellValue("Delete Flag");
		headerRow.createCell(15).setCellValue("Created By");
		headerRow.createCell(16).setCellValue("Created At");
		headerRow.createCell(17).setCellValue("Updated By");
		headerRow.createCell(18).setCellValue("Updated At");

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// データ行の作成。
		int rowNum = 1;
		for (Schedule schedule : schedules) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(schedule.getScheduleTitle());
			row.createCell(1).setCellValue(schedule.getScheduleStartDateTime().format(dateTimeFormatter));
			row.createCell(2).setCellValue(schedule.getScheduleEndDateTime().format(dateTimeFormatter));
			row.createCell(3).setCellValue(schedule.getAllDayFlg());
			row.createCell(4).setCellValue(schedule.getRepeatType());
			if (schedule.getRepeatUntil() != null) {
				row.createCell(5).setCellValue(schedule.getRepeatUntil().format(dateTimeFormatter));
			}
			row.createCell(6).setCellValue(schedule.getScheduleDisplayFlg());
			row.createCell(7).setCellValue(schedule.getLocation());
			row.createCell(8).setCellValue(schedule.getMeetLink());
			row.createCell(9).setCellValue(schedule.getScheduleDescription());
			row.createCell(10).setCellValue(schedule.getScheduleThemeColor());
			row.createCell(11).setCellValue(schedule.getOtherVisibilityFlg());
			row.createCell(12).setCellValue(schedule.getEventFlg());
			row.createCell(13).setCellValue(schedule.getScheduleStatusFlg());
			row.createCell(14).setCellValue(schedule.getDelFlg());
			row.createCell(15).setCellValue(schedule.getCreatedBy());
			row.createCell(16).setCellValue(schedule.getCreatedAt().format(dateTimeFormatter));
			row.createCell(17).setCellValue(schedule.getUpdatedBy());
			row.createCell(18).setCellValue(schedule.getUpdatedAt().format(dateTimeFormatter));
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		return outputStream.toByteArray();
	}

	private LocalDateTime toDateTime(String strDateTime) {
		if (strDateTime == null || strDateTime.isBlank() || strDateTime.isEmpty()) {
			return null;
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(strDateTime, formatter);
			return dateTime;
		}
	}

	public String getUserGroupCode(HttpServletRequest request) {
		String dataValue = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("groupCode")) {
					dataValue = cookie.getValue();
				}
			}
		}
		return dataValue;
	}

	public String getUserUserCode(HttpServletRequest request) {
		String dataValue = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userCode")) {
					dataValue = cookie.getValue();
				}
			}
		}
		return dataValue;
	}
}