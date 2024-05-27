package com.example.schedule.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule_reminders")
public class ScheduleReminder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "schedule_id")
	private int scheduleId;

	@Column(name = "schedule_reminder_time")
	private int scheduleReminderTime;

	@Column(name = "schedule_reminder_type")
	private String scheduleReminderType;

	@Column(name = "noti_method_flg")
	private Boolean notiMethodFlg;

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

	public ScheduleReminder() {
	}

	public ScheduleReminder(int id, int scheduleId, int scheduleReminderTime, String scheduleReminderType,
			Boolean notiMethodFlg, Boolean delFlg, String createdBy, LocalDateTime createdAt, String updatedBy,
			LocalDateTime updatedAt) {
		this.id = id;
		this.scheduleId = scheduleId;
		this.scheduleReminderTime = scheduleReminderTime;
		this.scheduleReminderType = scheduleReminderType;
		this.notiMethodFlg = notiMethodFlg;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
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

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getScheduleReminderTime() {
		return scheduleReminderTime;
	}

	public void setScheduleReminderTime(int scheduleReminderTime) {
		this.scheduleReminderTime = scheduleReminderTime;
	}

	public String getScheduleReminderType() {
		return scheduleReminderType;
	}

	public void setScheduleReminderType(String scheduleReminderType) {
		this.scheduleReminderType = scheduleReminderType;
	}

	public Boolean getNotiMethodFlg() {
		return notiMethodFlg;
	}

	public void setNotiMethodFlg(Boolean notiMethodFlg) {
		this.notiMethodFlg = notiMethodFlg;
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
		this.delFlg = false;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
