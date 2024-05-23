package com.example.schedule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "role_id")
	private int roleId;

	public UserRole() {
	}

	public UserRole(int userId, int roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	/*
	 * getterとsetterを定義する
	 */
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
