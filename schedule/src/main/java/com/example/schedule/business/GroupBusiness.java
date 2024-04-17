package com.example.schedule.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class GroupBusiness {
	private final GroupService groupService;
	private final UserService userService;

	@Autowired
	public GroupBusiness(GroupService groupService,UserService userService) {
		this.groupService = groupService;
		this.userService = userService;
	}

	public List<Group> getGroupLists() {
		List<Group> listGroups = groupService.getGroupLists();
		return listGroups;
	}

	public Page<Group> list(Pageable pageable) {
		Page<Group> listGroups = groupService.findAlls(pageable);
		return listGroups;
	}

	public Group saveGroup(Group group) {
		group.setGroupCode(this.getGroupCode());
		return groupService.save(group);
	}

	public Group findGroupById(int id) {
		Group group = groupService.findGroupById(id);
		return group;
	}

	public String updateGroup(Group group) {
		Group updGroup = groupService.findGroupById(group.getId());
		if (updGroup == null) {
			throw new RuntimeException("Group to update doesn't exist");
		}
		updGroup.setGroupName(group.getGroupName());
		groupService.save(updGroup);
		return "redirect:/groups";
	}

	public String deleteGroup(@PathVariable int id) {
		Group gp = groupService.findGroupById(id);
		if (gp == null) {
			throw new RuntimeException("Group Id not found.");
		}else {
			List<User> users = userService.findUserListByGroupId(id);
			if(users.size()>0) {
				throw new RuntimeException("Cannot delete group.");
			}
		}
		groupService.deleteById(id);
		return "redirect:/groups";
	}

	public String getGroupCode() {
		String groupCode = null;

		// 現在の最大グループをデータベースから取得します。
		Group group = groupService.findGroupCodeByDesc();

		if (group != null) {
			int nextNumber = Integer.parseInt(group.getGroupCode().substring(1)) + 1;
			// 「G000001」パターンにフォーマットします
			groupCode = "G" + String.format("%06d", nextNumber);
		} else {
			// グループがまだ存在しない場合
			groupCode = "G000001";
		}
		return groupCode;
	}
}