package com.example.schedule.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "task_id")
	private TaskReminder taskReminder;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "task_title")
	private String taskTitle;

	@Column(name = "task_due_date_time")
	private LocalDateTime taskDueDateTme;

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

	@Column(name = "task_status_flg")
	private Boolean taskStatusFlg;

	@Column(name = "taskDescription")
	private String taskDescription;

	@Column(name = "task_theme_color")
	private String taskThemeColor;

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

	public Task() {
	}

	public Task(int id, String groupCode, String userCode, String userName, String taskTitle,
			LocalDateTime taskDueDateTme, Boolean allDayFlg, int repeatType, int repeatUntil, int repeatInterval,
			int repeatDayOfWeek, int repeatDayOfMonth, int repeatMonth, Boolean taskStatusFlg, String taskDescription,
			String taskThemeColor, Boolean delFlg, Boolean eventFlg, String createdBy, LocalDateTime createdAt, String updatedBy,
			LocalDateTime updatedAt, TaskReminder taskReminder) {
		this.id = id;
		this.groupCode = groupCode;
		this.userCode = userCode;
		this.taskTitle = taskTitle;
		this.taskDueDateTme = taskDueDateTme;
		this.allDayFlg = allDayFlg;
		this.repeatType = repeatType;
		this.repeatUntil = repeatUntil;
		this.repeatInterval = repeatInterval;
		this.repeatDayOfWeek = repeatDayOfWeek;
		this.repeatDayOfMonth = repeatDayOfMonth;
		this.repeatMonth = repeatMonth;
		this.taskStatusFlg = taskStatusFlg;
		this.taskDescription = taskDescription;
		this.taskThemeColor = taskThemeColor;
		this.eventFlg = eventFlg;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.taskReminder = taskReminder;
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

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public LocalDateTime getTaskDueDateTme() {
		return taskDueDateTme;
	}

	public void setTaskDueDateTme(LocalDateTime taskDueDateTme) {
		this.taskDueDateTme = taskDueDateTme;
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

	public Boolean getTaskStatusFlg() {
		return taskStatusFlg;
	}

	public void setTaskStatusFlg(Boolean taskStatusFlg) {
		this.taskStatusFlg = taskStatusFlg;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskThemeColor() {
		return taskThemeColor;
	}

	public void setTaskThemeColor(String taskThemeColor) {
		this.taskThemeColor = taskThemeColor;
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

	public TaskReminder getTaskReminder() {
		return taskReminder;
	}

	public void setTaskReminder(TaskReminder taskReminder) {
		this.taskReminder = taskReminder;
	}
	
	@PrePersist
    protected void onCreate() {
		this.eventFlg = false;
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
