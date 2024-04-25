package com.example.schedule.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface GroupService {
	public Page<Group> findAlls(Pageable pageable);

	public String findById(int id);

	public Group findGroupById(int id);

	public Group save(Group group);

	public int deleteById(int id);

	public Group findGroupCodeByDesc();

	List<Group> getGroupLists();
}