package com.example.schedule.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "event_reminders")
public class EventReminder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "event_id")
	private int eventId;

	@Column(name = "event_reminder_time")
	private LocalTime eventReminderTime;

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

	public EventReminder() {
	}

	public EventReminder(int id, int eventId, LocalTime eventReminderTime, Boolean notiMethodFlg, Boolean delFlg,
			String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
		this.id = id;
		this.eventId = eventId;
		this.eventReminderTime = eventReminderTime;
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

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public LocalTime getEventReminderTime() {
		return eventReminderTime;
	}

	public void setEventReminderTime(LocalTime eventReminderTime) {
		this.eventReminderTime = eventReminderTime;
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
