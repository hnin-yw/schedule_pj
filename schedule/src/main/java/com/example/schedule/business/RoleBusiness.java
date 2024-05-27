package com.example.schedule.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class RoleBusiness {
	private final RoleService roleService;

	@Autowired
	public RoleBusiness(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRoleList() {
		List<Role> listRoles = roleService.getRoleLists();
		return listRoles;
	}
}