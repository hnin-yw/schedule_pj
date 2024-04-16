package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.*;

@Controller
public class HomeController {
	private final UserBusiness userBusiness;

	@Autowired
	public HomeController(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "schedule/login", method = RequestMethod.POST)
	public String showWelcomePage(Model model, User user) {

		Boolean isLoginValid = userBusiness.findByUsername(user);

		if (isLoginValid) {
			return "redirect:/schedules";
		} else {
			model.addAttribute("errorMessage", "Invalid username or password");
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(Model model) {
		return "login";
	}

}