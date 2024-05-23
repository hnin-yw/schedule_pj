package com.example.schedule.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "role_name")
	private String roleName;
	public Role() {
	}

	public Role(int id, String roleName) {
		this.id = id;
		this.roleName = roleName;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPerRoleName() {
		return "ROLE_" + roleName;
	}
}
