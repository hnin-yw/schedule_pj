package com.example.schedule.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.lang.Nullable;

import com.example.schedule.Consts;
import com.example.schedule.CustomBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "schedules")
public class Schedule {
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_id", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<ScheduleReminder> scheduleReminders;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
	private User user;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_id", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<Attendee> attendees;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "schedule_code")
	private String scheduleCode;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "user_code")
	private String userCode;

	@NotEmpty(message = "スケジュールタイトルは必須です。")
	@Size(max = Consts.MAX_NAME_LENGTH, message = "スケジュールタイトルは最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Column(name = "schedule_title")
	private String title;

	@Column(name = "schedule_start_date_time")
	private LocalDateTime start;

	@Column(name = "schedule_end_date_time")
	private LocalDateTime end;

	@Column(name = "allday_flg")
	private Boolean allDay;

	@Column(name = "repeat_type")
	private String repeatType;

	@Column(name = "repeat_until")
	private LocalDateTime repeatUntil;

	@Column(name = "repeat_day_of_week")
	private String repeatDayOfWeek;

	@Column(name = "repeat_type_of_month")
	private String repeatTypeOfMonth;

    @JsonDeserialize(using = CustomBooleanDeserializer.class)
	@Column(name = "schedule_display_flg")
	private Boolean scheduleDisplayFlg;

	@Size(max = Consts.MAX_NAME_LENGTH, message = "ロケーションは最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Column(name = "location")
	private String location;

	@Size(max = Consts.MAX_NAME_LENGTH, message = "ミーティングのリンクは最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Column(name = "meet_link")
	private String meetLink;

	@Size(max = Consts.MAX_NAME_AREA_LENGTH, message = "スケジュールの説明は最大 " + Consts.MAX_NAME_AREA_LENGTH + " 文字までです。")
	@Column(name = "schedule_description")
	private String scheduleDescription;

	@Column(name = "schedule_theme_color")
	private String color;

    @JsonDeserialize(using = CustomBooleanDeserializer.class)
	@Column(name = "other_visibility_flg")
	private Boolean otherVisibilityFlg;

    @JsonDeserialize(using = CustomBooleanDeserializer.class)
	@Column(name = "event_flg")
	private Boolean isTask;

    @JsonDeserialize(using = CustomBooleanDeserializer.class)
	@Column(name = "schedule_status_flg")
	private Boolean scheduleStatusFlg;

    @JsonDeserialize(using = CustomBooleanDeserializer.class)
	@Column(name = "guest_permission_flg")
	private Boolean guestPermissionFlg;

	@Column(name = "del_flg")
	private Boolean delFlg;

	@Column(name = "created_by")
	private String createdBy;

	@JsonProperty("createdAt")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@JsonProperty("updatedAt")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String startDateTimeString;
	private String endDateTimeString;
	private @Nullable String repeatUntilDateTimeString;
	private String indexArray;

	public Schedule() {
	}

	public Schedule(int id, String scheduleCode, String groupCode, String userCode, String userName, String title,
			LocalDateTime start, LocalDateTime end, Boolean allDay, String repeatType, LocalDateTime repeatUntil,
			String repeatDayOfWeek, String repeatTypeOfMonth, Boolean scheduleDisplayFlg, String location,
			String meetLink, String scheduleDescription, String color, Boolean otherVisibilityFlg, Boolean isTask,
			Boolean scheduleStatusFlg, Boolean guestPermissionFlg, Boolean delFlg, String createdBy,
			LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt, String startDateTimeString,
			String endDateTimeString, String repeatUntilDateTimeString, String indexArray,
			List<ScheduleReminder> scheduleReminders, User user, List<Attendee> attendees) {
		this.id = id;
		this.scheduleCode = scheduleCode;
		this.groupCode = groupCode;
		this.userCode = userCode;
		this.title = title;
		this.start = start;
		this.end = end;
		this.allDay = allDay;
		this.repeatType = repeatType;
		this.repeatUntil = repeatUntil;
		this.repeatDayOfWeek = repeatDayOfWeek;
		this.repeatTypeOfMonth = repeatTypeOfMonth;
		this.scheduleDisplayFlg = scheduleDisplayFlg;
		this.location = location;
		this.meetLink = meetLink;
		this.scheduleDescription = scheduleDescription;
		this.color = color;
		this.otherVisibilityFlg = otherVisibilityFlg;
		this.isTask = isTask;
		this.scheduleStatusFlg = scheduleStatusFlg;
		this.guestPermissionFlg = guestPermissionFlg;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.startDateTimeString = startDateTimeString;
		this.endDateTimeString = endDateTimeString;
		this.repeatUntilDateTimeString = repeatUntilDateTimeString;
		this.indexArray = indexArray;
		this.scheduleReminders = scheduleReminders;
		this.user = user;
		this.attendees = attendees;
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

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
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

	public String getRepeatDayOfWeek() {
		return repeatDayOfWeek;
	}

	public void setRepeatDayOfWeek(String repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
	}

	public String getRepeatTypeOfMonth() {
		return repeatTypeOfMonth;
	}

	public void setRepeatTypeOfMonth(String repeatTypeOfMonth) {
		this.repeatTypeOfMonth = repeatTypeOfMonth;
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
		return scheduleDescription;
	}

	public void setScheduleDescription(String scheduleDescription) {
		this.scheduleDescription = scheduleDescription;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getOtherVisibilityFlg() {
		return otherVisibilityFlg;
	}

	public void setOtherVisibilityFlg(Boolean otherVisibilityFlg) {
		this.otherVisibilityFlg = otherVisibilityFlg;
	}

	public Boolean getIsTask() {
		return isTask;
	}

	public void setIsTask(Boolean isTask) {
		this.isTask = isTask;
	}

	public Boolean getScheduleStatusFlg() {
		return scheduleStatusFlg;
	}

	public void setScheduleStatusFlg(Boolean scheduleStatusFlg) {
		this.scheduleStatusFlg = scheduleStatusFlg;
	}

	public Boolean getGuestPermissionFlg() {
		return guestPermissionFlg;
	}

	public void setGuestPermissionFlg(Boolean guestPermissionFlg) {
		this.guestPermissionFlg = guestPermissionFlg;
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

	public String getIndexArray() {
		return indexArray;
	}

	public void setIndexArray(String indexArray) {
		this.indexArray = indexArray;
	}

	public List<ScheduleReminder> getScheduleReminders() {
		return scheduleReminders;
	}

	public void setScheduleReminders(List<ScheduleReminder> scheduleReminders) {
		this.scheduleReminders = scheduleReminders;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}
}
