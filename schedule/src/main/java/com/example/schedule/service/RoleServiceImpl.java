package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schedule.dao.RoleDao;
import com.example.schedule.entity.*;

import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

	RoleDao roleDao;

	@Autowired
	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> getRoleLists() {
		return roleDao.getRoleLists();
	}

	@Override
	@Transactional
	public UserRole saveUserRoles(UserRole userRole) {
		return roleDao.saveUserRoles(userRole);
	}

	@Override
	@Transactional
	public int deleteUserRolesByUserId(int userId) {
		roleDao.deleteUserRolesByUserId(userId);
		return userId;
	}
}