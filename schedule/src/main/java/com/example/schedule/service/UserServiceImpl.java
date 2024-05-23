package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<User> getUserLists() {
		return userDao.getUserLists();
	}

	@Override
	public Page<User> findAlls(Pageable pageable) {
		return userDao.getAlls(pageable);
	}

	@Override
	public User findUserById(int id) {
		return userDao.findUserById(id);
	}

//	@Override
//	@Transactional
//	public User save(User user) {
//		return userDao.save(user);
//	}
	@Override
	@Transactional
	public int save(User user) {
		return userDao.save(user);
	}
	
	@Override
	@Transactional
	public int updateUser(User user) {
		return userDao.updateUser(user);
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

	@Override
	public User findUserByUserName(String userName) {
		User user = userDao.findUserByUserName(userName);
		return user;
	}


	@Override
	public User findUserByLoginData(String userName, String password) {
		User user = userDao.findUserByLoginData(userName, password);
		return user;
	}

	@Override
	public List<User> findUserListByGroupCode(String groupCode) {
		return userDao.findUserListByGroupCode(groupCode);
	}
}