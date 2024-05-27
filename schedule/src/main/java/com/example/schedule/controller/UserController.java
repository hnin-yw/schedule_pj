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
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/users")
public class UserController {
	private final UserBusiness userBusiness;
	private final GroupBusiness groupBusiness;
	private final RoleBusiness roleBusiness;

	@Autowired
	public UserController(UserBusiness userBusiness, GroupBusiness groupBusiness, RoleBusiness roleBusiness) {
		this.userBusiness = userBusiness;
		this.groupBusiness = groupBusiness;
		this.roleBusiness = roleBusiness;
	}

	@GetMapping()
	public String list(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<User> listUsers = userBusiness.list(PageRequest.of(page, 15));
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listUsers.getTotalPages());
		return "users/list";
	}

	@RequestMapping("/create")
	public String create(Model model) {
		model.addAttribute("isAuthList", "/schedule/users");
		List<Group> gpLists = groupBusiness.getGroupLists();
		model.addAttribute("gpLists", gpLists);
		List<Role> roleLists = roleBusiness.getRoleList();
		model.addAttribute("roleLists", roleLists);
		User user = new User();
		model.addAttribute("user", user);

		return "users/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("user") @Valid User user, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			List<Group> gpLists = groupBusiness.getGroupLists();
			model.addAttribute("gpLists", gpLists);
			List<Role> roleLists = roleBusiness.getRoleList();
			model.addAttribute("roleLists", roleLists);
			return "users/create";
		} else {
			Map<String, ArrayList<String>> msgLists = userBusiness.saveUser(user);
			redirectAttributes.addFlashAttribute("msgLists", msgLists);
			return "redirect:/users";
		}
	}

//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(Model model, @ModelAttribute("user") @Valid User user, BindingResult result,
//			RedirectAttributes redirectAttributes, HttpServletRequest request) {
//		if (result.hasErrors()) {
//			List<Group> gpLists = groupBusiness.getGroupLists();
//			model.addAttribute("gpLists", gpLists);
//			return "users/create";
//		} else {
//			Map<String, ArrayList<String>> msgLists = userBusiness.saveUser(user, request);
//			redirectAttributes.addFlashAttribute("msgLists", msgLists);
//			return "redirect:/users";
//		}
//	}

//	@PostMapping("/save")
//	@ResponseBody
//	public Map<String, String> save(@Valid @RequestBody User user, BindingResult result, HttpServletRequest request) {
//		Map<String, String> response = new HashMap<>();
//		if (result.hasErrors()) {
//			response.put("error", "検証が失敗しました。 入力内容をご確認ください。");
//		} else {
//			userBusiness.saveUser(user, request);
//			response.put("success", "ユーザは正常に更新されました。");
//		}
//		return response;
//	}

	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("users/edit");
		List<Group> gpLists = groupBusiness.getGroupLists();
		mav.addObject("gpLists", gpLists);
		User user = userBusiness.findUserById(id);
		mav.addObject("user", user);
		List<Role> roleLists = roleBusiness.getRoleList();
		mav.addObject("roleLists", roleLists);
		mav.addObject("isAuthList", "/schedule/users");

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @ModelAttribute("user") @Valid User user, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			List<Group> gpLists = groupBusiness.getGroupLists();
			model.addAttribute("gpLists", gpLists);
			return "users/edit";
		} else {
			Map<String, ArrayList<String>> msgLists = userBusiness.updateUser(user);
			redirectAttributes.addFlashAttribute("msgLists", msgLists);
			return "redirect:/users";
		}
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Map<String, ArrayList<String>> msgLists = userBusiness.deleteUser(id, request);
		redirectAttributes.addFlashAttribute("msgLists", msgLists);
		return "redirect:/users";
	}
}