package com.example.schedule.business;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class UserBusiness {
	private final UserService userService;
	private final ScheduleService scheduleService;
	private final ScheduleBusiness scheduleBusiness;

	private Map<String, ArrayList> msgLists = new HashMap<>();
	private ArrayList<String> errMsgLists = new ArrayList<>();
	private ArrayList<String> sucMsgLists = new ArrayList<>();

	@Autowired
	public UserBusiness(UserService userService, ScheduleService scheduleService, ScheduleBusiness scheduleBusiness) {
		this.userService = userService;
		this.scheduleService = scheduleService;
		this.scheduleBusiness = scheduleBusiness;
	}

	public Page<User> list(Pageable pageable) {
		Page<User> listUsers = userService.findAlls(pageable);
		return listUsers;
	}

	public User saveUser(User user, HttpServletRequest request) {
		user.setPassword(toHexString(encodePassword(user.getPassword())));
		user.setUserCode(this.getUserCode());
		String userCode = scheduleBusiness.getUserUserCode(request);
		user.setCreatedBy(userCode);
		user.setUpdatedBy(userCode);
		return userService.save(user);
	}

	public User findUserById(int id) {
		User User = userService.findUserById(id);
		return User;
	}

	public String updateUser(User user, HttpServletRequest request) {
		User updUser = userService.findUserById(user.getId());
		if (updUser == null) {
			throw new RuntimeException("User to update doesn't exist");
		}
		updUser.setGroupCode(user.getGroupCode());
		updUser.setUserName(user.getUserName());
		updUser.setUserFirstName(user.getUserFirstName());
		updUser.setUserLastName(user.getUserLastName());
		updUser.setPostCode(user.getPostCode());
		updUser.setAddress(user.getAddress());
		updUser.setTelNumber(user.getTelNumber());
		updUser.setEmail(user.getEmail());
		String userCode = scheduleBusiness.getUserUserCode(request);
		updUser.setUpdatedBy(userCode);
		userService.save(updUser);
		return "redirect:/users";
	}

	public Map<String, ArrayList> deleteUser(@PathVariable int id, HttpServletRequest request) {
		msgLists = new HashMap<>();
		errMsgLists = new ArrayList<>();
		sucMsgLists = new ArrayList<>();

		Boolean isError = false;
		User user = userService.findUserById(id);
		if (user == null) {
			isError = true;
			errMsgLists.add("ユーザが見つかりません。");
		} else {
			List<Schedule> schedules = scheduleService.findScheduleListByUserCode(user.getUserCode());
			if (schedules.size() > 0) {
				isError = true;
				errMsgLists.add("このユーザは削除できません。");
			}
		}
		if (!isError) {
			user.setDelFlg(true);
			String userCode = scheduleBusiness.getUserUserCode(request);
			user.setUpdatedBy(userCode);
			userService.save(user);
			sucMsgLists.add("ユーザは正常に削除されました。");
			msgLists.put("messages", sucMsgLists);
		} else {
			msgLists.put("errors", errMsgLists);
		}
		return msgLists;
	}

	public String getUserCode() {
		String userCode = null;

		// 現在の最大グループをデータベースから取得します。
		User user = userService.findUserCodeByDesc();

		if (user != null) {
			int nextNumber = Integer.parseInt(user.getUserCode().substring(1)) + 1;
			// 「G000001」パターンにフォーマットします
			userCode = "U" + String.format("%06d", nextNumber);
		} else {
			// グループがまだ存在しない場合
			userCode = "U000001";
		}
		return userCode;
	}

	private static byte[] encodePassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			return md.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null; // Handle the error appropriately in your application
		}
	}

	public static String toHexString(byte[] encodedPassword) {
		BigInteger number = new BigInteger(1, encodedPassword);
		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));
		// Pad with leading zeros
		while (hexString.length() < 64) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

	public Boolean login(User user, HttpServletResponse response) {
		boolean isLoginFail = false;
		if (user.getUserName() == null || user.getPassword() == null) {
			isLoginFail = true;
		} else {
			String password = DigestUtils.sha256Hex(user.getPassword());
			User userdb = userService.findUserByLoginData(user.getUserName(), password);
			if (userdb == null) {
				isLoginFail = true;
			} else {
				Cookie c1 = new Cookie("userCode", userdb.getUserCode());
				c1.setDomain("localhost");
				c1.setPath("/schedule");
				c1.setHttpOnly(true);
				response.addCookie(c1);
				Cookie c2 = new Cookie("userName", userdb.getUserFirstName() + "_" + userdb.getUserLastName());
				c2.setDomain("localhost");
				c2.setPath("/schedule");
				c2.setHttpOnly(true);
				response.addCookie(c2);
				Cookie c3 = new Cookie("groupCode", userdb.getGroupCode());
				c3.setDomain("localhost");
				c3.setPath("/schedule");
				c3.setHttpOnly(true);
				response.addCookie(c3);
			}
		}
		return isLoginFail;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// ユーザーに関連付けられたすべての Cookie を削除します。
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (!cookie.getName().equals("JSESSIONID")) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
}