package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface UserDao {
	List<User> getAlls();

	User findUserById(int id);

	User save(User user);

	void deleteById(int id);

	User findUserCodeByDesc();
}