package com.example.schedule.controller;

import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schedule.business.*;
import com.example.schedule.entity.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
	public String save(Model model, @ModelAttribute("group") @Valid Group group, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "groups/create";
		} else {
			Map<String, ArrayList<String>> msgLists = groupBusiness.saveGroup(group, request);
			redirectAttributes.addFlashAttribute("msgLists", msgLists);
			return "redirect:/groups";
		}
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("groups/edit");
		Group group = groupBusiness.findGroupById(id);

		mav.addObject("group", group);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @ModelAttribute("group") @Valid Group group, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "groups/edit";
		} else {
			Map<String, ArrayList<String>> msgLists = groupBusiness.updateGroup(group, request);
			redirectAttributes.addFlashAttribute("msgLists", msgLists);
			return "redirect:/groups";
		}
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Map<String, ArrayList<String>> msgLists = groupBusiness.deleteGroup(id, request);
		redirectAttributes.addFlashAttribute("msgLists", msgLists);
		return "redirect:/groups";
	}
}