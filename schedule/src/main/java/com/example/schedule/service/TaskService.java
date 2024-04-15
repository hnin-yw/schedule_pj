package com.example.schedule.service;

import java.util.List;

import com.example.schedule.entity.*;

public interface TaskService {
	public List<Task> findAlls();

	public String findById(int id);

	public Task findTaskById(int id);

	public Task save(Task task);

	public int deleteById(int id);
}