package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.GroupDao;
import com.example.schedule.entity.Group;

import jakarta.transaction.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

	GroupDao groupDao;

	@Autowired
	public GroupServiceImpl(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public List<Group> getGroupLists() {
		return groupDao.getGroupLists();
	}

	@Override
	public Page<Group> findAlls(Pageable pageable) {
		return groupDao.getAlls(pageable);
	}

	@Override
	public String findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group findGroupById(int id) {
		return groupDao.findGroupById(id);
	}

	@Override
	@Transactional
	public Group save(Group group) {
		return groupDao.save(group);
	}

	@Override
	@Transactional
	public int deleteById(int id) {
		groupDao.deleteById(id);
		return id;
	}

	@Override
	@Transactional
	public Group findGroupCodeByDesc() {
		return groupDao.findGroupCodeByDesc();
	}
}