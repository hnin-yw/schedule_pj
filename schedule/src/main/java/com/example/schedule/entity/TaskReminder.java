package com.example.schedule.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "task_reminders")
public class TaskReminder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "task_id")
	private int taskId;

	@Column(name = "task_reminder_time")
	private LocalTime taskReminderTime;

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

	public TaskReminder() {
	}

	public TaskReminder(int id, int taskId, LocalTime taskReminderTime, Boolean notiMethodFlg, Boolean delFlg,
			String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
		this.id = id;
		this.taskId = taskId;
		this.taskReminderTime = taskReminderTime;
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

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public LocalTime getTaskReminderTime() {
		return taskReminderTime;
	}

	public void setTaskReminderTime(LocalTime taskReminderTime) {
		this.taskReminderTime = taskReminderTime;
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
