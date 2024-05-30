package com.example.schedule.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface UserDao {

	Page<User> getAlls(Pageable pageable);

	User findUserById(int id);

	//User save(User user);

	int save(User user);

	int updateUser(User user);

	void deleteById(int id);

	User findUserCodeByDesc();
	
	User findUserByUserName(String userName);

	User findUserByLoginData(String userName,String password);

	List<User> findUserListByGroupCode(String groupCode);

	List<User> getUserLists();

	String updateProvider(String userName, String provider);
}