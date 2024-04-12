package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface GroupService {
	public List<Group> findAlls();

	public String findById(int id);

	public Group findGroupById(int id);

	public Group save(Group group);

	public int deleteById(int id);
}