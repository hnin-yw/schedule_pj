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
import com.example.schedule.MyUserDetails;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class ScheduleBusiness {
	private final ScheduleService scheduleService;
	private final ScheduleReminderService scheduleReminderService;
	private final AttendeeService attendeeService;

	@Autowired
	public ScheduleBusiness(ScheduleService scheduleService, ScheduleReminderService scheduleReminderService,
			AttendeeService attendeeService) {
		this.scheduleService = scheduleService;
		this.scheduleReminderService = scheduleReminderService;
		this.attendeeService = attendeeService;
	}
	
	public List<Schedule> getAllSchedules() {
		String userCode = getUserUserCode();
		String groupCode = getUserGroupCode();

		List<Schedule> schedules = scheduleService.findAllSchedules(userCode, groupCode);
        
        return schedules;
    }
	
	public Page<Schedule> list(Pageable pageable) {
		String userCode = getUserUserCode();
		String groupCode = getUserGroupCode();
		Page<Schedule> listSchedules = scheduleService.findAlls(pageable, userCode, groupCode);
		return listSchedules;
	}

	public Schedule saveSchedule(Schedule schedule) {
		LocalDateTime startDateTime = toDateTime(schedule.getStartDateTimeString());
		LocalDateTime dbStartDateTime = toDateTime(schedule.getStartDateTimeString());
		LocalDateTime dbEndDateTime = toDateTime(schedule.getEndDateTimeString());
		schedule.setStart(dbStartDateTime);
		schedule.setEnd(dbEndDateTime);
		schedule.setScheduleCode(getScheduleCode());
		schedule.setGroupCode(getUserGroupCode());
		schedule.setUserCode(getUserUserCode());
		schedule.setCreatedBy(getUserUserCode());
		schedule.setUpdatedBy(getUserUserCode());
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
					schedule.setStart(dbStartDateTime);
					schedule.setEnd(dbEndDateTime);
					saveMultiSchedule(schedule);
				}
			} else if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_YEAR)) {
				for (LocalDateTime dateTime = startDateTime; !dateTime.isAfter(repeatUntilDateTime); dateTime = dateTime
						.plusYears(1)) {
					if (!dateTime.isEqual(dbStartDateTime)) {
						dbStartDateTime = dbStartDateTime.plusYears(1);
						dbEndDateTime = dbEndDateTime.plusYears(1);
					}
					schedule.setStart(dbStartDateTime);
					schedule.setEnd(dbEndDateTime);
					saveMultiSchedule(schedule);
				}
			} else if (schedule.getRepeatType().equals(Consts.REPEATE_TYPE_WEEK)) {
				DayOfWeek targetDayOfWeek = getDayOfWeek(schedule.getRepeatDayOfWeek());
				while (startDateTime.isBefore(repeatUntilDateTime) || startDateTime.isEqual(repeatUntilDateTime)) {
					if (startDateTime.getDayOfWeek() == targetDayOfWeek) {
						dbStartDateTime = startDateTime;

						schedule.setStart(dbStartDateTime);
						schedule.setEnd(dbEndDateTime);
						saveMultiSchedule(schedule);
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
							schedule.setStart(dbStartDateTime);
							schedule.setEnd(dbEndDateTime);
							saveMultiSchedule(schedule);
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

							schedule.setStart(dbStartDateTime);
							schedule.setEnd(dbEndDateTime);
							saveMultiSchedule(schedule);
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

							schedule.setStart(dbStartDateTime);
							schedule.setEnd(dbEndDateTime);
							saveMultiSchedule(schedule);
						}
						startDateTime = startDateTime.plusMonths(1);
						dbEndDateTime = dbEndDateTime.plusMonths(1);
					}
				}
			}
		} else {
			saveMultiSchedule(schedule);
		}

		return schedule;
	}

	public Schedule setDatas(Schedule schedule) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (schedule.getStart() != null) {
			schedule.setStartDateTimeString(schedule.getStart().format(formatter));
		}
		if (schedule.getEnd() != null) {
			schedule.setEndDateTimeString(schedule.getEnd().format(formatter));
		}
		if (schedule.getRepeatUntil() != null) {
			schedule.setRepeatUntilDateTimeString(schedule.getRepeatUntil().format(formatter));
		}
		String indexArrayString = "";
		List<Attendee> attendees = schedule.getAttendees();
		if (attendees.size() > 0) {
			for (int j = 0; j < attendees.size(); j++) {
				indexArrayString += j + ",";
			}
		}
		if (!indexArrayString.isEmpty()) {
			indexArrayString = indexArrayString.substring(0, indexArrayString.length() - 1);
		}
		schedule.setIndexArray(indexArrayString);

		return schedule;
	}

	public void saveMultiSchedule(Schedule schedule) {
		schedule.setDelFlg(false);
		schedule.setScheduleStatusFlg(false);
		LocalDateTime createdAt = LocalDateTime.now();
		if (schedule.getCreatedAt() != null) {
			createdAt = schedule.getCreatedAt();
		}
		LocalDateTime updatedAt = LocalDateTime.now();
		if (schedule.getUpdatedAt() != null) {
			updatedAt = schedule.getUpdatedAt();
		}
		schedule.setCreatedAt(createdAt);
		schedule.setUpdatedAt(updatedAt);
		int id = scheduleService.saveSchedule(schedule);
		if (!schedule.getAllDay()) {
			List<ScheduleReminder> reminders = schedule.getScheduleReminders();
			if (reminders != null && reminders.size() > 0) {
				for (int j = 0; j < reminders.size(); j++) {
					ScheduleReminder reminder = reminders.get(j);
					reminder.setScheduleId(id);
					reminder.setCreatedBy(schedule.getCreatedBy());
					reminder.setUpdatedBy(schedule.getUpdatedBy());
					reminder.setDelFlg(false);
					reminder.setCreatedAt(createdAt);
					reminder.setUpdatedAt(updatedAt);
					scheduleReminderService.saveScheduleReminder(reminder);
				}
			}
		}
		List<Attendee> attendees = schedule.getAttendees();
		if (attendees != null && attendees.size() > 0) {
			for (int j = 0; j < attendees.size(); j++) {
				Attendee attendee = attendees.get(j);
				if (attendee.getUserCode() != null) {
					attendee.setScheduleId(id);
					attendee.setResponseStatusFlg(true);
					attendee.setResponseTime(LocalTime.now());
					attendee.setDelFlg(false);
					attendee.setCreatedBy(schedule.getCreatedBy());
					attendee.setUpdatedBy(schedule.getUpdatedBy());
					attendee.setCreatedAt(createdAt);
					attendee.setUpdatedAt(updatedAt);
					attendeeService.saveAttendee(attendee);
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

	public String updateSchedule(Schedule schedule) {
		deleteScheduleByCode(schedule.getScheduleCode());
		saveSchedule(schedule);
		return schedule.getScheduleCode();
	}

	public String updateScheduleStatus(int id) {
		Schedule updSchedule = scheduleService.findScheduleById(id);
		if (updSchedule == null) {
			throw new RuntimeException("Schedule to update doesn't exist");
		}
		updSchedule.setScheduleStatusFlg(true);
		String userCode = getUserUserCode();
		updSchedule.setUpdatedBy(userCode);
		scheduleService.save(updSchedule);
		return "redirect:/schedules";
	}

	public String deleteScheduleByCode(@PathVariable String scheduleCode) {
		List<Schedule> schedules = scheduleService.findScheduleListByScheduleCode(scheduleCode);
		for (Schedule schedule : schedules) {
			this.deleteScheduleData(schedule);
		}
		return scheduleCode;
	}

	public String deleteScheduleData(Schedule schedule) {
		List<ScheduleReminder> reminders = schedule.getScheduleReminders();
		if (reminders != null && reminders.size() > 0) {
			for (int j = 0; j < reminders.size(); j++) {
				ScheduleReminder reminder = reminders.get(j);
				reminder.setDelFlg(true);
				String userCode = getUserUserCode();
				reminder.setUpdatedBy(userCode);
				scheduleReminderService.save(reminder);
			}
		}

		List<Attendee> attendees = schedule.getAttendees();
		if (attendees.size() > 0) {
			for (int j = 0; j < attendees.size(); j++) {
				Attendee attendee = attendees.get(j);
				attendee.setDelFlg(true);
				String userCode = getUserUserCode();
				attendee.setUpdatedBy(userCode);
				attendeeService.save(attendee);
			}
		}
		schedule.setDelFlg(true);
		String userCode = getUserUserCode();
		schedule.setUpdatedBy(userCode);
		scheduleService.save(schedule);
		return schedule.getScheduleCode();
	}

	public String deleteScheduleById(@PathVariable int id) {
		Schedule schedule = scheduleService.findScheduleById(id);
		this.deleteScheduleData(schedule);
		return schedule.getScheduleCode();
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
			row.createCell(0).setCellValue(schedule.getTitle());

			row.createCell(1).setCellValue(DateTimeToStr(schedule.getStart()));
			row.createCell(2).setCellValue(DateTimeToStr(schedule.getEnd()));
			row.createCell(3).setCellValue(schedule.getAllDay());
			row.createCell(4).setCellValue(schedule.getRepeatType());
			if (schedule.getRepeatUntil() != null) {
				row.createCell(5).setCellValue(DateTimeToStr(schedule.getRepeatUntil()));
			}
			row.createCell(6).setCellValue(schedule.getScheduleDisplayFlg());
			row.createCell(7).setCellValue(schedule.getLocation());
			row.createCell(8).setCellValue(schedule.getMeetLink());
			row.createCell(9).setCellValue(schedule.getScheduleDescription());
			row.createCell(10).setCellValue(schedule.getColor());
			row.createCell(11).setCellValue(schedule.getOtherVisibilityFlg());
			row.createCell(12).setCellValue(schedule.getIsTask());
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

	public String getUserGroupCode() {
		String groupCode = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.getPrincipal() instanceof MyUserDetails) {
	        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
	    	User user = userDetails.getUser();
	    	groupCode = user.getGroupCode();
	    }
		return groupCode;
	}

	public String getUserUserCode() {
        String userCode = null;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
            User user = userDetails.getUser();
            userCode = user.getUserCode();
        }
        return userCode;
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