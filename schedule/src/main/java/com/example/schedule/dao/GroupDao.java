package com.example.schedule.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.entity.*;

public interface GroupDao {

	Group findGroupById(int id);

	Group save(Group group);

	void deleteById(int id);

	Group findGroupCodeByDesc();

	Page<Group> getAlls(Pageable pageable);

	List<Group> getGroupLists();
}