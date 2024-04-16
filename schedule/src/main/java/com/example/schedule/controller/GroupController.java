package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.*;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/groups")
public class GroupController {
	private final GroupBusiness groupBusiness;

	@Autowired
	public GroupController(GroupBusiness groupBusiness) {
		this.groupBusiness = groupBusiness;
	}

	@GetMapping()
	public String list(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<Group> listGroups = groupBusiness.list(PageRequest.of(page, 10));
        model.addAttribute("listGroups", listGroups);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listGroups.getTotalPages());
		return "groups/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		Group gp = new Group();
		model.addAttribute("group", gp);

		return "groups/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("group") @Validated Group group, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			System.out.println(result.hasFieldErrors("groupName") == true ? result.getFieldError("groupName").getDefaultMessage() : "test");
//			return "groups/create";
//		}
		groupBusiness.saveGroup(group);
		return "redirect:/groups";
	}
	

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("groups/edit");
		Group group = groupBusiness.findGroupById(id);
		mav.addObject("group", group);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Group group) {
		groupBusiness.updateGroup(group);
		return "redirect:/groups";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		groupBusiness.deleteGroup(id);
		return "redirect:/groups";
	}
}