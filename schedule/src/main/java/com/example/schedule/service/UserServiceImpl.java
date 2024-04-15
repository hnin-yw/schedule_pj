package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.UserDao;
import com.example.schedule.entity.*;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> findAlls() {
		return userDao.getAlls();
	}

	@Override
	public User findUserById(int id) {
		return userDao.findUserById(id);
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public int deleteById(int id) {
		userDao.deleteById(id);
		return id;
	}

	@Override
	@Transactional
	public User findUserCodeByDesc() {
		return userDao.findUserCodeByDesc();
	}
}