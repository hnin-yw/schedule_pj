package com.example.schedule.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface UserDao {

	Page<User> getAlls(Pageable pageable);

	User findUserById(int id);

	User save(User user);

	void deleteById(int id);

	User findUserCodeByDesc();

	User findUserByUsername(String username);
}