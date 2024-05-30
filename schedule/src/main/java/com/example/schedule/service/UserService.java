package com.example.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface UserService {

	Page<User> findAlls(Pageable pageable);

	public User findUserById(int id);

	// public User save(User User);

	public int save(User User);

	public int updateUser(User User);

	public int deleteById(int id);

	public User findUserCodeByDesc();

	public User findUserByUserName(String userName);

	User findUserByLoginData(String userName, String password);

	public List<User> findUserListByGroupCode(String groupCode);

	List<User> getUserLists();

	public String updateProvider(String userName, String provider);
}