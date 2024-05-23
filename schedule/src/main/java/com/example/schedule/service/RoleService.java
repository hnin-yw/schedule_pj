package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface RoleService {
    List<Role> getRoleLists();

	public UserRole saveUserRoles(UserRole userRole);

	public int deleteUserRolesByUserId(int userId);
}
