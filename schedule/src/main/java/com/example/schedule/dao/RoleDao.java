package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface RoleDao {
	List<Role> getRoleLists();

	UserRole saveUserRoles(UserRole userRole);

	void deleteUserRolesByUserId(int userId);
}