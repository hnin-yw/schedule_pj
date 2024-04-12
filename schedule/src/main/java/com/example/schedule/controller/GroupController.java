package com.example.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.schedule.entity.Group;
import com.example.schedule.service.GroupService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/groups")
public class GroupController {

	private final GroupService groupService;

	@Autowired
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping()
	public String index(Model model) {
		List<Group> listGroups = groupService.findAlls();
		System.out.println(listGroups.size());

		model.addAttribute("listGroups", listGroups);
		return "groups/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		Group gp = new Group();
		model.addAttribute("group", gp);

		return "create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveGroup(@RequestParam("group_code") String group_code,
			@RequestParam("group_name") String group_name) {
		Group group = new Group();
		group.setGroupCode(group_code);
		group.setGroupName(group_name);
		groupService.save(group);
		return "redirect:/groups";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit");
		Group group = groupService.findGroupById(id);
		mav.addObject("group", group);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateGroup(@RequestParam("id") int id, @RequestParam("group_code") String group_code,
			@RequestParam("group_name") String group_name) {
		Group group = groupService.findGroupById(id);
		if (group == null) {
			throw new RuntimeException("Group to update doesn't exist");
		}
		group.setGroupCode(group_code);
		group.setGroupName(group_name);
		groupService.save(group);
		return "redirect:/groups";
	}

	@RequestMapping("/delete/{id}")
	public String deleteGroup(@PathVariable int id) {
		Group gp = groupService.findGroupById(id);
		if (gp == null) {
			throw new RuntimeException("Group Id not found");
		}
		groupService.deleteById(id);
		return "redirect:/groups";

	}
}