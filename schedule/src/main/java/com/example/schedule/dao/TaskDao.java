package com.example.schedule.dao;

import java.util.List;

import com.example.schedule.entity.*;

public interface TaskDao {
	List<Task> getAlls();

	Task findTaskById(int id);

	Task save(Task task);

	void deleteById(int id);
}