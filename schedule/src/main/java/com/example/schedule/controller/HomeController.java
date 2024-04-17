package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.*;

@Controller
public class HomeController {
	private final UserBusiness userBusiness;

	@Autowired
	public HomeController(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "schedule/login", method = RequestMethod.POST)
	public String showWelcomePage(User user, RedirectAttributes redirectAttributes) {

		Boolean isLoginFail = userBusiness.login(user);

		if (isLoginFail) {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password!");
			return "redirect:/";
		} else {
			return "redirect:/main_menu";
		}
	}

	@RequestMapping(value = "/main_menu", method = RequestMethod.GET)
	public String menu() {
		return "main_menu";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(Model model) {
		return "login";
	}

}