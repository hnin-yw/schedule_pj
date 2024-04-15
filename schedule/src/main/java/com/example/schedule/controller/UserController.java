package com.example.schedule.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/users")
public class UserController {
	private final UserBusiness userBusiness;

	@Autowired
	public UserController(UserBusiness userBusiness) {
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
		User user = new User();
		model.addAttribute("user", user);

		return "Users/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("User") @Validated User user, BindingResult result, Model model) {
		userBusiness.saveUser(user);
		return "redirect:/Users";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("users/edit");
		User user = userBusiness.findUserById(id);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user) {
		userBusiness.updateUser(user);
		return "redirect:/users";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userBusiness.deleteUser(id);
		return "redirect:/users";
	}
}