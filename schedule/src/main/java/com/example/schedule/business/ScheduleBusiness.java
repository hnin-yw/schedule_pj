package com.example.schedule.business;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.schedule.Consts;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

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
		String userCode = getUserUserCode(request);
		String groupCode = getUserGroupCode(request);
		Page<Schedule> listSchedules = scheduleService.findAlls(pageable, userCode, groupCode);
		return listSchedules;
	}

	public Schedule saveSchedule(Schedule schedule, HttpServletRequest request) {
		schedule.setScheduleCode(this.getScheduleCode());
		LocalDateTime startDateTime = toDateTime(schedule.getStartDateTimeString());
		LocalDateTime dbStartDateTime = toDateTime(schedule.getStartDateTimeString());
		LocalDateTime dbEndDateTime = toDateTime(schedule.getEndDateTimeString());
		String groupCode = getUserGroupCode(request);
		String userCode = getUserUserCode(request);
		schedule.setScheduleStartDateTime(dbStartDateTime);
		schedule.setScheduleEndDateTime(dbEndDateTime);
		if (!schedule.getRepeatType().equals(Consts.REPEATE_TYPE_NONE)) {
			LocalDateTime repeatUntilDateTime = toDateTime(schedule.getRepeatUntilDateTimeString());
			schedule.setRepeatUntil(repeatUntilDateTime);
			if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_DAY)) {
				for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(repeatUntilDateTime); dateTime = dateTime
						.plusDays(1)) {
					if (!dateTime.isEqual(dbStartDateTime)) {
						dbStartDateTime = dbStartDateTime.plusDays(1);
						dbEndDateTime = dbEndDateTime.plusDays(1);
					}
					schedule.setScheduleStartDateTime(dbStartDateTime);
					schedule.setScheduleEndDateTime(dbEndDateTime);
					saveMultiSchedule(schedule, userCode, groupCode);
				}
			} else if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_YEAR)) {
				for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(repeatUntilDateTime); dateTime = dateTime
						.plusYears(1)) {
					if (!dateTime.isEqual(dbStartDateTime)) {
						dbStartDateTime = dbStartDateTime.plusYears(1);
						dbEndDateTime = dbEndDateTime.plusYears(1);
					}
					schedule.setScheduleStartDateTime(dbStartDateTime);
					schedule.setScheduleEndDateTime(dbEndDateTime);
					saveMultiSchedule(schedule, userCode, groupCode);
				}
			} else if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_WEEK)) {
				DayOfWeek targetDayOfWeek = getDayOfWeek(schedule.getRepeatDayOfWeek());
				System.out.println(schedule.getRepeatDayOfWeek());
				System.out.println(targetDayOfWeek);
				while (startDateTime.isBefore(repeatUntilDateTime) || startDateTime.isEqual(repeatUntilDateTime)) {
					if (startDateTime.getDayOfWeek() == targetDayOfWeek) {
						dbStartDateTime = startDateTime;

						schedule.setScheduleStartDateTime(dbStartDateTime);
						schedule.setScheduleEndDateTime(dbEndDateTime);
						saveMultiSchedule(schedule, userCode, groupCode);
					}
					startDateTime = startDateTime.plusDays(1);
					dbEndDateTime = dbEndDateTime.plusDays(1);
				}
			} else if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_MONTH)) {
				int dayOfMonth = startDateTime.getDayOfMonth();
				DayOfWeek targetDayOfWeek = startDateTime.getDayOfWeek();
				int diffDay = diffDay(startDateTime, dbEndDateTime);
				if (schedule.getRepeatTypeOfMonth().equals(Consts.REPEATE_TYPE_OF_MONTH_01)) {
					while (startDateTime.isBefore(repeatUntilDateTime) || startDateTime.isEqual(repeatUntilDateTime)) {
						if (startDateTime.getDayOfMonth() == dayOfMonth) {
							dbStartDateTime = startDateTime;
							schedule.setScheduleStartDateTime(dbStartDateTime);
							schedule.setScheduleEndDateTime(dbEndDateTime);
							saveMultiSchedule(schedule, userCode, groupCode);
						}
						startDateTime = startDateTime.plusMonths(1);
						dbEndDateTime = dbEndDateTime.plusMonths(1);
					}
				} else if (schedule.getRepeatTypeOfMonth().equals(Consts.REPEATE_TYPE_OF_MONTH_02)) {
					while (!startDateTime.isAfter(repeatUntilDateTime)) {
						LocalDateTime fourthRepeatDay = startDateTime
								.with(TemporalAdjusters.dayOfWeekInMonth(4, targetDayOfWeek));
						if (!fourthRepeatDay.isAfter(repeatUntilDateTime)) {
							dbStartDateTime = fourthRepeatDay;
							dbEndDateTime = fourthRepeatDay.withYear(fourthRepeatDay.getYear())
									.withMonth(fourthRepeatDay.getMonthValue())
									.withDayOfMonth(fourthRepeatDay.getDayOfMonth() + diffDay)
									.withHour(dbEndDateTime.getHour()).withMinute(dbEndDateTime.getMinute())
									.withSecond(dbEndDateTime.getSecond());

							schedule.setScheduleStartDateTime(dbStartDateTime);
							schedule.setScheduleEndDateTime(dbEndDateTime);
							saveMultiSchedule(schedule, userCode, groupCode);
						}
						startDateTime = startDateTime.plusMonths(1);
						dbEndDateTime = dbEndDateTime.plusMonths(1);
					}
				} else if (schedule.getRepeatTypeOfMonth().equals(Consts.REPEATE_TYPE_OF_MONTH_03)) {
					while (!startDateTime.isAfter(repeatUntilDateTime)) {
						LocalDateTime lastRepeatDay = startDateTime
								.with(TemporalAdjusters.lastInMonth(targetDayOfWeek));
						if (!lastRepeatDay.isAfter(repeatUntilDateTime)) {
							dbStartDateTime = lastRepeatDay;
							dbEndDateTime = lastRepeatDay.withYear(lastRepeatDay.getYear())
									.withMonth(lastRepeatDay.getMonthValue())
									.withDayOfMonth(lastRepeatDay.getDayOfMonth() + diffDay)
									.withHour(dbEndDateTime.getHour()).withMinute(dbEndDateTime.getMinute())
									.withSecond(dbEndDateTime.getSecond());

							schedule.setScheduleStartDateTime(dbStartDateTime);
							schedule.setScheduleEndDateTime(dbEndDateTime);
							saveMultiSchedule(schedule, userCode, groupCode);
						}
						startDateTime = startDateTime.plusMonths(1);
						dbEndDateTime = dbEndDateTime.plusMonths(1);
					}
				}
			}
		} else {
			saveMultiSchedule(schedule, userCode, groupCode);
		}

		return schedule;
	}

	public void saveMultiSchedule(Schedule schedule, String userCode, String groupCode) {
		schedule.setUserCode(userCode);
		schedule.setCreatedBy(userCode);
		schedule.setUpdatedBy(userCode);
		schedule.setGroupCode(groupCode);
		schedule.setDelFlg(false);
		schedule.setScheduleStatusFlg(false);
		schedule.setCreatedAt(LocalDateTime.now());
		schedule.setUpdatedAt(LocalDateTime.now());
		int id = scheduleService.saveSchedule(schedule);
		if (!schedule.getAllDayFlg()) {
			List<ScheduleReminder> reminders = schedule.getScheduleReminders();
			if (reminders.size() > 0) {
				for (int j = 0; j < reminders.size(); j++) {
					ScheduleReminder reminder = reminders.get(j);
					reminder.setScheduleId(id);
					reminder.setCreatedBy(userCode);
					reminder.setUpdatedBy(userCode);
					reminder.setDelFlg(false);
					reminder.setCreatedAt(LocalDateTime.now());
					reminder.setUpdatedAt(LocalDateTime.now());
					scheduleReminderService.saveScheduleReminder(reminder);
				}
			}
		}
	}

	public int diffDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		LocalDate startDate = startDateTime.toLocalDate();
		LocalDate endDate = endDateTime.toLocalDate();
		Period period = Period.between(startDate, endDate);
		return period.getDays();
	}

	public DayOfWeek getDayOfWeek(String repeatDayOfWeek) {
		String dayOfWeek = "SUNDAY";
		if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_MON)) {
			dayOfWeek = Consts.REPEATE_DAY_MON_VALUE;
		} else if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_TUE)) {
			dayOfWeek = Consts.REPEATE_DAY_TUE_VALUE;
		} else if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_WED)) {
			dayOfWeek = Consts.REPEATE_DAY_WED_VALUE;
		} else if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_THU)) {
			dayOfWeek = Consts.REPEATE_DAY_THU_VALUE;
		} else if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_FRI)) {
			dayOfWeek = Consts.REPEATE_DAY_FRI_VALUE;
		} else if (repeatDayOfWeek.equals(Consts.REPEATE_DAY_SAT)) {
			dayOfWeek = Consts.REPEATE_DAY_SAT_VALUE;
		}
		DayOfWeek targetDayOfWeek = DayOfWeek.valueOf(dayOfWeek);
		return targetDayOfWeek;
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

		if (!schedule.getAllDayFlg()) {
			List<ScheduleReminder> reminders = schedule.getScheduleReminders();
			List<ScheduleReminder> updReminders = updSchedule.getScheduleReminders();
			if (updReminders.size() > 0 && reminders.size() > 0) {
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
			} else if (updReminders.size() == 0 && reminders.size() > 0) {
				for (int j = 0; j < reminders.size(); j++) {
					ScheduleReminder reminder = reminders.get(j);
					reminder.setScheduleId(updSchedule.getId());
					userCode = getUserUserCode(request);
					reminder.setCreatedBy(userCode);
					reminder.setUpdatedBy(userCode);
					scheduleReminderService.save(reminder);
				}
			}
		} else {
			scheduleReminderService.deleteBySchedulId(updSchedule.getId());
		}
		return "redirect:/schedules";
	}

	public String updateScheduleStatus(int id, HttpServletRequest request) {
		Schedule updSchedule = scheduleService.findScheduleById(id);
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		updSchedule.setScheduleStatusFlg(true);
		String userCode = getUserUserCode(request);
		updSchedule.setUpdatedBy(userCode);
		scheduleService.save(updSchedule);
		return "redirect:/schedules";
	}

	public String deleteScheduleByCode(@PathVariable String scheduleCode, HttpServletRequest request) {
		List<Schedule> schedules = scheduleService.findScheduleListByScheduleCode(scheduleCode);
		for (Schedule schedule : schedules) {
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
		}
		return scheduleCode;
	}

	public String deleteSchedule(@PathVariable int id, HttpServletRequest request) {
		Schedule schedule = scheduleService.findScheduleById(id);
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
		// データ行の作成。
		int rowNum = 1;
		for (Schedule schedule : schedules) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(schedule.getScheduleTitle());
			
			row.createCell(1).setCellValue(DateTimeToStr(schedule.getScheduleStartDateTime()));
			row.createCell(2).setCellValue(DateTimeToStr(schedule.getScheduleEndDateTime()));
			row.createCell(3).setCellValue(schedule.getAllDayFlg());
			row.createCell(4).setCellValue(schedule.getRepeatType());
			if (schedule.getRepeatUntil() != null) {
				row.createCell(5).setCellValue(DateTimeToStr(schedule.getRepeatUntil()));
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
			row.createCell(16).setCellValue(DateTimeToStr(schedule.getCreatedAt()));
			row.createCell(17).setCellValue(schedule.getUpdatedBy());
			row.createCell(18).setCellValue(DateTimeToStr(schedule.getUpdatedAt()));
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		return outputStream.toByteArray();
	}

	private String DateTimeToStr(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String strDateTime = dateTime.format(formatter);
		return strDateTime;
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

	public String getScheduleCode() {
		String scheduleCode = null;

		// 現在の最大グループをデータベースから取得します。
		Schedule schedule = scheduleService.findScheduleCodeByDesc();

		if (schedule != null) {
			int nextNumber = Integer.parseInt(schedule.getScheduleCode().substring(1)) + 1;
			// 「S000001」パターンにフォーマットします
			scheduleCode = "S" + String.format("%06d", nextNumber);
		} else {
			// グループがまだ存在しない場合
			scheduleCode = "S000001";
		}
		return scheduleCode;
	}
}