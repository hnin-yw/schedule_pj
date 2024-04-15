package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface UserService {
	public List<User> findAlls();

	public User findUserById(int id);

	public User save(User User);

	public int deleteById(int id);

	public User findUserCodeByDesc();
}