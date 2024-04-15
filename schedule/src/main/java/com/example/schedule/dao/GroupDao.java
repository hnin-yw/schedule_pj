package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface GroupDao {
	List<Group> getAlls();

	Group findGroupById(int id);

	Group save(Group group);

	void deleteById(int id);

	Group findGroupCodeByDesc();
}