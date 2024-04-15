package com.example.schedule.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "event_id")
	private EventReminder eventReminder;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "event_title")
	private String eventTitle;

	@Column(name = "event_start_date_time")
	private LocalDateTime eventStartDateTime;

	@Column(name = "event_end_date_time")
	private LocalDateTime eventEndDateTime;

	@Column(name = "allday_flg")
	private Boolean allDayFlg;

	@Column(name = "repeat_type")
	private int repeatType;

	@Column(name = "repeat_until")
	private int repeatUntil;

	@Column(name = "repeat_interval")
	private int repeatInterval;

	@Column(name = "repeat_day_of_week")
	private int repeatDayOfWeek;

	@Column(name = "repeat_day_of_month")
	private int repeatDayOfMonth;

	@Column(name = "repeat_month")
	private int repeatMonth;

	@Column(name = "event_display_flg")
	private Boolean eventDisplayFlg;

	@Column(name = "location")
	private String location;

	@Column(name = "meet_link")
	private String meetLink;

	@Column(name = "eventDescription")
	private String eventDescription;

	@Column(name = "event_theme_color")
	private String eventThemeColor;

	@Column(name = "other_visibility_flg")
	private Boolean otherVisibilityFlg;

	@Column(name = "event_flg")
	private Boolean eventFlg;

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

	public Event() {
	}

	public Event(int id, String groupCode, String userCode, String userName, String eventTitle,
			LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime, Boolean allDayFlg, int repeatType,
			int repeatUntil, int repeatInterval, int repeatDayOfWeek, int repeatDayOfMonth, int repeatMonth,
			Boolean eventDisplayFlg, String location, String meetLink, String eventDescription, String eventThemeColor,
			Boolean otherVisibilityFlg, Boolean eventFlg, Boolean delFlg, String createdBy, LocalDateTime createdAt, String updatedBy,
			LocalDateTime updatedAt, EventReminder eventReminder) {
		this.id = id;
		this.groupCode = groupCode;
		this.userCode = userCode;
		this.eventTitle = eventTitle;
		this.eventStartDateTime = eventStartDateTime;
		this.eventEndDateTime = eventEndDateTime;
		this.allDayFlg = allDayFlg;
		this.repeatType = repeatType;
		this.repeatUntil = repeatUntil;
		this.repeatInterval = repeatInterval;
		this.repeatDayOfWeek = repeatDayOfWeek;
		this.repeatDayOfMonth = repeatDayOfMonth;
		this.repeatMonth = repeatMonth;
		this.eventDisplayFlg = eventDisplayFlg;
		this.location = location;
		this.meetLink = meetLink;
		this.eventDescription = eventDescription;
		this.eventThemeColor = eventThemeColor;
		this.otherVisibilityFlg = otherVisibilityFlg;
		this.eventFlg = eventFlg;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.eventReminder = eventReminder;
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

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public LocalDateTime getEventStartDateTime() {
		return eventStartDateTime;
	}

	public void setEventStartDateTime(LocalDateTime eventStartDateTime) {
		this.eventStartDateTime = eventStartDateTime;
	}

	public LocalDateTime getEventEndDateTime() {
		return eventEndDateTime;
	}

	public void setEventEndDateTime(LocalDateTime eventEndDateTime) {
		this.eventEndDateTime = eventEndDateTime;
	}

	public Boolean getAllDayFlg() {
		return allDayFlg;
	}

	public void setAllDayFlg(Boolean allDayFlg) {
		this.allDayFlg = allDayFlg;
	}

	public int getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(int repeatType) {
		this.repeatType = repeatType;
	}

	public int getRepeatUntil() {
		return repeatUntil;
	}

	public void setRepeatUntil(int repeatUntil) {
		this.repeatUntil = repeatUntil;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
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

	public Boolean getEventDisplayFlg() {
		return eventDisplayFlg;
	}

	public void setEventDisplayFlg(Boolean eventDisplayFlg) {
		this.eventDisplayFlg = eventDisplayFlg;
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

	public String getEventDescription() {
		return meetLink;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventThemeColor() {
		return eventThemeColor;
	}

	public void setEventThemeColor(String eventThemeColor) {
		this.eventThemeColor = eventThemeColor;
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

	public EventReminder getEventReminder() {
		return eventReminder;
	}

	public void setEventReminder(EventReminder eventReminder) {
		this.eventReminder = eventReminder;
	}
	
	@PrePersist
    protected void onCreate() {
		this.eventFlg = true;
		this.delFlg = false;
		this.createdAt = LocalDateTime.now();
		this.createdBy = "U000001";
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = "U000001";
    }
	
	@PreUpdate
    protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = "U000011";
    }
}
