package com.example.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface UserService {

	Page<User> findAlls(Pageable pageable);

	public User findUserById(int id);

	public User save(User User);

	public int deleteById(int id);

	public User findUserCodeByDesc();

	User findUserByUsername(String username);
}