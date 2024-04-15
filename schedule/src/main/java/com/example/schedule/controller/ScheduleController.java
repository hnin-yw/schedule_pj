package com.example.schedule.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
	private final UserBusiness userBusiness;

	@Autowired
	public ScheduleController(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

	@GetMapping()
	public String list(Model model) {
		List<User> listUsers = userBusiness.list();
		model.addAttribute("listUsers", listUsers);
		return "Users/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		User gp = new User();
		model.addAttribute("User", gp);

		return "Users/create";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("users/edit");
		User user = userBusiness.findUserById(id);
		mav.addObject("User", user);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User User) {
		userBusiness.updateUser(User);
		return "redirect:/users";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userBusiness.deleteUser(id);
		return "redirect:/users";
	}
}