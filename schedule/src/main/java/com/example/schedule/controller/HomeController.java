package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {
	private final UserBusiness userBusiness;

	@Autowired
	public HomeController(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "ユーザー名またはパスワードが正しくありません！");
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String showWelcomePage(User user, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		Boolean isLoginFail = userBusiness.login(user, response);

		if (isLoginFail) {
			redirectAttributes.addFlashAttribute("error", "ユーザー名またはパスワードが正しくありません！");
			return "redirect:/login";
		} else {
			return "redirect:/schedules";
		}
	}

	@RequestMapping(value = "/main_menu", method = RequestMethod.GET)
	public String menu() {
		return "main_menu";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request, HttpServletResponse response) {
		userBusiness.logout(request, response);
		return "redirect:/";
	}

}