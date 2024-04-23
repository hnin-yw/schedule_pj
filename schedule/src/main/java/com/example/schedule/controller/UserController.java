package com.example.schedule.controller;

import java.util.ArrayList;
import java.util.List;
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

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/users")
public class UserController {
	private final UserBusiness userBusiness;
	private final GroupBusiness groupBusiness;

	@Autowired
	public UserController(UserBusiness userBusiness, GroupBusiness groupBusiness) {
		this.userBusiness = userBusiness;
		this.groupBusiness = groupBusiness;
	}

	@GetMapping()
	public String list(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<User> listUsers = userBusiness.list(PageRequest.of(page, 10));
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listUsers.getTotalPages());
		return "users/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		List<Group> gpLists = groupBusiness.getGroupLists();
		model.addAttribute("gpLists", gpLists);
		User user = new User();
		model.addAttribute("user", user);

		return "users/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("user") @Validated User user, BindingResult result, Model model,
			HttpServletRequest request) {
		userBusiness.saveUser(user, request);
		return "redirect:/users";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("users/edit");
		List<Group> gpLists = groupBusiness.getGroupLists();
		mav.addObject("gpLists", gpLists);
		User user = userBusiness.findUserById(id);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user, HttpServletRequest request) {
		userBusiness.updateUser(user, request);
		return "redirect:/users";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Map<String, ArrayList> msgLists = userBusiness.deleteUser(id, request);
		redirectAttributes.addFlashAttribute("msgLists", msgLists);
		return "redirect:/users";
	}
}