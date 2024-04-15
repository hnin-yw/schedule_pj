package com.example.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schedule.dao.TaskDao;
import com.example.schedule.entity.*;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

	TaskDao taskDao;

	@Autowired
	public TaskServiceImpl(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<Task> findAlls() {
		return taskDao.getAlls();
	}

	@Override
	public Task findTaskById(int id) {
		return taskDao.findTaskById(id);
	}

	@Override
	@Transactional
	public Task save(Task task) {
		return taskDao.save(task);
	}

	@Override
	@Transactional
	public int deleteById(int id) {
		taskDao.deleteById(id);
		return id;
	}

	@Override
	public String findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}