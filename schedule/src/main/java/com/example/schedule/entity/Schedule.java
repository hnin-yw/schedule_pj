package com.example.schedule.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule {
	@OneToMany(mappedBy = "schedule")
	protected List<ScheduleReminder> scheduleReminders;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "schedule_title")
	private String scheduleTitle;

	@Column(name = "schedule_start_date_time")
	private LocalDateTime scheduleStartDateTime;

	@Column(name = "schedule_end_date_time")
	private LocalDateTime scheduleEndDateTime;

	@Column(name = "allday_flg")
	private Boolean allDayFlg;

	@Column(name = "repeat_type")
	private String repeatType;

	@Column(name = "repeat_until")
	private LocalDateTime repeatUntil;

	@Column(name = "repeat_interval")
	private int repeatInterval;

	@Column(name = "repeat_interval_type")
	private String repeatIntervalType;

	@Column(name = "repeat_day_of_week")
	private int repeatDayOfWeek;

	@Column(name = "repeat_day_of_month")
	private int repeatDayOfMonth;

	@Column(name = "repeat_month")
	private int repeatMonth;

	@Column(name = "schedule_display_flg")
	private Boolean scheduleDisplayFlg;

	@Column(name = "location")
	private String location;

	@Column(name = "meet_link")
	private String meetLink;

	@Column(name = "schedule_description")
	private String scheduleDescription;

	@Column(name = "schedule_theme_color")
	private String scheduleThemeColor;

	@Column(name = "other_visibility_flg")
	private Boolean otherVisibilityFlg;

	@Column(name = "event_flg")
	private Boolean eventFlg;

	@Column(name = "schedule_status_flg")
	private Boolean scheduleStatusFlg;

	@Column(name = "del_flg")
	private Boolean delFlg;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String startDateTimeString;
	private String endDateTimeString;
	private @Nullable String repeatUntilDateTimeString;

	public Schedule() {
	}

	public Schedule(int id, String groupCode, String userCode, String userName, String scheduleTitle,
			LocalDateTime scheduleStartDateTime, LocalDateTime scheduleEndDateTime, Boolean allDayFlg,
			String repeatType, LocalDateTime repeatUntil, int repeatInterval, String repeatIntervalType,
			int repeatDayOfWeek, int repeatDayOfMonth, int repeatMonth, Boolean scheduleDisplayFlg, String location,
			String meetLink, String scheduleDescription, String scheduleThemeColor, Boolean otherVisibilityFlg,
			Boolean eventFlg, Boolean scheduleStatusFlg, Boolean delFlg, String createdBy, LocalDateTime createdAt,
			String updatedBy, LocalDateTime updatedAt, List<ScheduleReminder> scheduleReminders,
			String startDateTimeString, String endDateTimeString, String repeatUntilDateTimeString, User user) {
		this.id = id;
		this.groupCode = groupCode;
		this.userCode = userCode;
		this.scheduleTitle = scheduleTitle;
		this.scheduleStartDateTime = scheduleStartDateTime;
		this.scheduleEndDateTime = scheduleEndDateTime;
		this.allDayFlg = allDayFlg;
		this.repeatType = repeatType;
		this.repeatUntil = repeatUntil;
		this.repeatInterval = repeatInterval;
		this.repeatIntervalType = repeatIntervalType;
		this.repeatDayOfWeek = repeatDayOfWeek;
		this.repeatDayOfMonth = repeatDayOfMonth;
		this.repeatMonth = repeatMonth;
		this.scheduleDisplayFlg = scheduleDisplayFlg;
		this.location = location;
		this.meetLink = meetLink;
		this.scheduleDescription = scheduleDescription;
		this.scheduleThemeColor = scheduleThemeColor;
		this.otherVisibilityFlg = otherVisibilityFlg;
		this.eventFlg = eventFlg;
		this.scheduleStatusFlg = scheduleStatusFlg;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.scheduleReminders = scheduleReminders;
		this.startDateTimeString = startDateTimeString;
		this.endDateTimeString = endDateTimeString;
		this.repeatUntilDateTimeString = repeatUntilDateTimeString;
		this.user = user;
	}

	/*
	 * getterとsetterを定義する
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public LocalDateTime getScheduleStartDateTime() {
		return scheduleStartDateTime;
	}

	public void setScheduleStartDateTime(LocalDateTime scheduleStartDateTime) {
		this.scheduleStartDateTime = scheduleStartDateTime;
	}

	public LocalDateTime getScheduleEndDateTime() {
		return scheduleEndDateTime;
	}

	public void setScheduleEndDateTime(LocalDateTime scheduleEndDateTime) {
		this.scheduleEndDateTime = scheduleEndDateTime;
	}

	public Boolean getAllDayFlg() {
		return allDayFlg;
	}

	public void setAllDayFlg(Boolean allDayFlg) {
		this.allDayFlg = allDayFlg;
	}

	public String getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

	public LocalDateTime getRepeatUntil() {
		return repeatUntil;
	}

	public void setRepeatUntil(LocalDateTime repeatUntil) {
		this.repeatUntil = repeatUntil;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getRepeatIntervalType() {
		return repeatIntervalType;
	}

	public void setRepeatIntervalType(String repeatIntervalType) {
		this.repeatIntervalType = repeatIntervalType;
	}

	public int getRepeatDayOfWeek() {
		return repeatDayOfWeek;
	}

	public void setRepeatDayOfWeek(int repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
	}

	public int getRepeatDayOfMonth() {
		return repeatDayOfMonth;
	}

	public void setRepeatDayOfMonth(int repeatDayOfMonth) {
		this.repeatDayOfMonth = repeatDayOfMonth;
	}

	public int getRepeatMonth() {
		return repeatMonth;
	}

	public void setRepeatMonth(int repeatMonth) {
		this.repeatMonth = repeatMonth;
	}

	public Boolean getScheduleDisplayFlg() {
		return scheduleDisplayFlg;
	}

	public void setScheduleDisplayFlg(Boolean scheduleDisplayFlg) {
		this.scheduleDisplayFlg = scheduleDisplayFlg;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMeetLink() {
		return meetLink;
	}

	public void setMeetLink(String meetLink) {
		this.meetLink = meetLink;
	}

	public String getScheduleDescription() {
		return meetLink;
	}

	public void setScheduleDescription(String scheduleDescription) {
		this.scheduleDescription = scheduleDescription;
	}

	public String getScheduleThemeColor() {
		return scheduleThemeColor;
	}

	public void setScheduleThemeColor(String scheduleThemeColor) {
		this.scheduleThemeColor = scheduleThemeColor;
	}

	public Boolean getOtherVisibilityFlg() {
		return otherVisibilityFlg;
	}

	public void setOtherVisibilityFlg(Boolean otherVisibilityFlg) {
		this.otherVisibilityFlg = otherVisibilityFlg;
	}

	public Boolean getEventFlg() {
		return eventFlg;
	}

	public void setEventFlg(Boolean eventFlg) {
		this.eventFlg = eventFlg;
	}

	public Boolean getScheduleStatusFlg() {
		return scheduleStatusFlg;
	}

	public void setScheduleStatusFlg(Boolean scheduleStatusFlg) {
		this.scheduleStatusFlg = scheduleStatusFlg;
	}

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<ScheduleReminder> getScheduleReminders() {
		return scheduleReminders;
	}

	public void setScheduleReminders(List<ScheduleReminder> scheduleReminders) {
		this.scheduleReminders = scheduleReminders;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PrePersist
	protected void onCreate() {
		this.scheduleStatusFlg = false;
		this.delFlg = false;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public String getStartDateTimeString() {
		return startDateTimeString;
	}

	public void setStartDateTimeString(String startDateTimeString) {
		this.startDateTimeString = startDateTimeString;
	}

	public String getEndDateTimeString() {
		return endDateTimeString;
	}

	public void setEndDateTimeString(String endDateTimeString) {
		this.endDateTimeString = endDateTimeString;
	}

	public String getRepeatUntilDateTimeString() {
		return repeatUntilDateTimeString;
	}

	public void setRepeatUntilDateTimeString(String repeatUntilDateTimeString) {
		this.repeatUntilDateTimeString = repeatUntilDateTimeString;
	}
}
