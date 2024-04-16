package com.example.schedule.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.UserService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class UserBusiness {
	private final UserService userService;
	
	@Autowired
	public UserBusiness(UserService userService) {
        this.userService = userService;
	}

	public User login(User user) {
		if (user.getUserName() == null || user.getPassword() == null) {
			return user;
		} else {
			user = userService.findUserByUsername(user.getUserName());
		}
		return user;
	}

	public Page<User> list(Pageable pageable) {
		Page<User> listUsers = userService.findAlls(pageable);
		return listUsers;
	}

	public User saveUser(User user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
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
			throw new RuntimeException("User Id not found");
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

	public Boolean findByUsername(User user) {
		User userdb = userService.findUserByUsername(user.getUserName());
        if (user != null) {
            //return passwordEncoder.matches(user.getPassword(), userdb.getPassword());
        }
        return false;
	}
}