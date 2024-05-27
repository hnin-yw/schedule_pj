package com.example.schedule.entity;

import java.time.LocalTime;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "attendees")
public class Attendee {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "schedule_id")
	private int scheduleId;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "response_status_flg")
	private Boolean responseStatusFlg;

	@Column(name = "response_time")
	private LocalTime responseTime;

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

	public Attendee() {
	}

	public Attendee(int id, int scheduleId, String userCode, Boolean responseStatusFlg, LocalTime responseTime,
			Boolean delFlg, String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
			User user) {
		this.id = id;
		this.scheduleId = scheduleId;
		this.userCode = userCode;
		this.responseStatusFlg = responseStatusFlg;
		this.responseTime = responseTime;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
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

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Boolean getResponseStatusFlg() {
		return responseStatusFlg;
	}

	public void setResponseStatusFlg(Boolean responseStatusFlg) {
		this.responseStatusFlg = responseStatusFlg;
	}

	public LocalTime getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(LocalTime responseTime) {
		this.responseTime = responseTime;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PrePersist
	protected void onCreate() {
		this.responseStatusFlg = true;
		this.delFlg = false;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
