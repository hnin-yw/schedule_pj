package com.example.schedule.business;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.schedule.Consts;
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
	private final Consts con;

	private Map<String, ArrayList<String>> msgLists = new HashMap<>();
	private ArrayList<String> errMsgLists = new ArrayList<>();
	private ArrayList<String> sucMsgLists = new ArrayList<>();

	@Autowired
	public UserBusiness(UserService userService, ScheduleService scheduleService, ScheduleBusiness scheduleBusiness,
			Consts con) {
		this.userService = userService;
		this.scheduleService = scheduleService;
		this.scheduleBusiness = scheduleBusiness;
		this.con = con;
	}

	public Page<User> list(Pageable pageable) {
		Page<User> listUsers = userService.findAlls(pageable);
		return listUsers;
	}

	public Map<String, ArrayList<String>> validateCreate(User user) {
		msgLists = new HashMap<>();
		errMsgLists = this.validate(user);
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			errMsgLists.add("パスワードは必須です。");
		}
		if (!errMsgLists.isEmpty()) {
			msgLists.put("errors", errMsgLists);
		}
		return msgLists;
	}

	private ArrayList<String> validate(User user) {
        ArrayList<String> errMsgLists = new ArrayList<>();
        if (user.getUserName() == null || user.getUserName().isBlank()) {
            errMsgLists.add("ユーザ名またはログイン名は必須です。");
        } else if (user.getUserName().length() > con.MAX_CODE_LENGTH) {
            errMsgLists.add("ユーザ名またはログイン名は最大 " + con.MAX_CODE_LENGTH + " 文字までです。");
        }

        if (user.getUserFirstName() == null || user.getUserFirstName().isBlank()) {
            errMsgLists.add("ユーザの名は必須です。");
        } else if (user.getUserFirstName().length() > con.MAX_NAME_LENGTH) {
            errMsgLists.add("ユーザの名は最大 " + con.MAX_NAME_LENGTH + " 文字までです。");
        }

        if (user.getUserLastName() == null || user.getUserLastName().isBlank()) {
            errMsgLists.add("ユーザの姓は必須です。");
        } else if (user.getUserLastName().length() > con.MAX_NAME_LENGTH) {
            errMsgLists.add("ユーザの姓は最大 " + con.MAX_NAME_LENGTH + " 文字までです。");
        }

        if (user.getPostCode() == null || user.getPostCode().isBlank()) {
            errMsgLists.add("郵便番号は必須です。");
        } else if (user.getPostCode().length() > con.MAX_CODE_CONT_LENGTH) {
            errMsgLists.add("郵便番号は最大 " + con.MAX_CODE_CONT_LENGTH + " 文字までです。");
        } else if (!user.getPostCode().matches("[0-9\\-]+")) {
            errMsgLists.add("郵便番号が無効です。");
        }

        if (user.getAddress() == null || user.getAddress().isBlank()) {
            errMsgLists.add("住所は必須です。");
        } else if (user.getAddress().length() > con.MAX_NAME_AREA_LENGTH) {
            errMsgLists.add("住所は最大 " + con.MAX_NAME_AREA_LENGTH + " 文字までです。");
        }

        if (user.getTelNumber() == null || user.getTelNumber().isBlank()) {
            errMsgLists.add("電話番号は必須です。");
        } else if (user.getTelNumber().length() > con.MAX_CODE_CONT_LENGTH) {
            errMsgLists.add("電話番号は最大 " + con.MAX_CODE_CONT_LENGTH + " 文字までです。");
        } else if (!user.getTelNumber().matches("[0-9\\-]+")) {
            errMsgLists.add("電話番号が無効です。");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            errMsgLists.add("メールは必須です。");
        } else if (user.getEmail().length() > con.MAX_NAME_LENGTH) {
            errMsgLists.add("メールは最大 " + con.MAX_NAME_LENGTH + " 文字までです。");
        } else {
            String EMAIL_PATTERN = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            if (!pattern.matcher(user.getEmail()).matches()) {
                errMsgLists.add("メールアドレスが無効です。「例) @ を含める必要があります」");
            }
        }
        return errMsgLists;
    }

	public Map<String, ArrayList<String>> validateUpdate(User user) {
		msgLists = new HashMap<>();
		errMsgLists = this.validate(user);
		if (!errMsgLists.isEmpty()) {
			msgLists.put("errors", errMsgLists);
		}
		return msgLists;
	}

	public Map<String, ArrayList<String>> saveUser(User user, HttpServletRequest request) {
		msgLists = new HashMap<>();
		sucMsgLists = new ArrayList<>();
		user.setPassword(toHexString(encodePassword(user.getPassword())));
		user.setUserCode(this.getUserCode());
		String userCode = scheduleBusiness.getUserUserCode(request);
		user.setCreatedBy(userCode);
		user.setUpdatedBy(userCode);
		User dbUser = userService.save(user);
		if (dbUser != null) {
			sucMsgLists.add("グループは正常に更新されました。");
		}
		msgLists.put("messages", sucMsgLists);
		return msgLists;
	}

	public User findUserById(int id) {
		User User = userService.findUserById(id);
		return User;
	}

	public Map<String, ArrayList<String>> updateUser(User user, HttpServletRequest request) {
		msgLists = new HashMap<>();
		errMsgLists = new ArrayList<>();
		sucMsgLists = new ArrayList<>();
		Boolean isError = false;
		User updUser = userService.findUserById(user.getId());
		if (updUser == null) {
			isError = true;
			errMsgLists.add("更新するユーザが存在しません。");
		}
		if (!isError) {
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
			sucMsgLists.add("ユーザは正常に更新されました。");
			msgLists.put("messages", sucMsgLists);
		} else {
			msgLists.put("errors", errMsgLists);
		}
		return msgLists;
	}

	public Map<String, ArrayList<String>> deleteUser(@PathVariable int id, HttpServletRequest request) {
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
		// ユーザに関連付けられたすべての Cookie を削除します。
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