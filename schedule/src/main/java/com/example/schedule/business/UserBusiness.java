package com.example.schedule.business;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.*;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class UserBusiness {
	private final UserService userService;
	private final ScheduleService scheduleService;

	@Autowired
	public UserBusiness(UserService userService,ScheduleService scheduleService) {
		this.userService = userService;
		this.scheduleService = scheduleService;
	}

//	public User login(User user) {
//		if (user.getUserName() == null || user.getPassword() == null) {
//			return user;
//		} else {
//			user = userService.findUserByUsername(user.getUserName());
//		}
//		return user;
//	}

	public Page<User> list(Pageable pageable) {
		Page<User> listUsers = userService.findAlls(pageable);
		return listUsers;
	}

	public User saveUser(User user) {
		user.setPassword(toHexString(encodePassword(user.getPassword())));
		user.setUserCode(this.getUserCode());
		return userService.save(user);
	}

	public User findUserById(int id) {
		User User = userService.findUserById(id);
		return User;
	}

	public String updateUser(User user) {
		User updUser = userService.findUserById(user.getId());
		if (updUser == null) {
			throw new RuntimeException("User to update doesn't exist");
		}
		updUser.setGroupId(user.getGroupId());
		updUser.setUserName(user.getUserName());
		updUser.setUserFirstName(user.getUserFirstName());
		updUser.setUserLastName(user.getUserLastName());
		updUser.setPostCode(user.getPostCode());
		updUser.setAddress(user.getAddress());
		updUser.setTelNumber(user.getTelNumber());
		updUser.setEmail(user.getEmail());
		userService.save(updUser);
		return "redirect:/users";
	}

	public String deleteUser(@PathVariable int id) {
		User user = userService.findUserById(id);
		if (user == null) {
			throw new RuntimeException("User Id not found.");
		}else {
			List<Schedule> schedules = scheduleService.findScheduleListByUserCode(user.getUserCode());
			if(schedules.size()>0) {
				throw new RuntimeException("Cannot delete User.");
			}
		}
		userService.deleteById(id);
		return "redirect:/users";
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

	public Boolean login(User user) {
		boolean isLoginFail = false;
		if (user.getUserName() == null || user.getPassword() == null) {
			isLoginFail = true;
		}else {
			String password = DigestUtils.sha256Hex(user.getPassword());
			User userdb = userService.findUserByLoginData(user.getUserName(), password);
			if (userdb == null) {
				isLoginFail = true;
			}
		}
		return isLoginFail;
	}
}