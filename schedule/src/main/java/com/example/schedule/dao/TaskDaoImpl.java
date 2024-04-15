package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

	private EntityManager entityManager;

	@Autowired
	public TaskDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getAlls() {
		Query q = (Query) entityManager.createQuery("from Task");
		List<Task> transactions = q.getResultList();

		return transactions;
	}

	@Override
	public Task findTaskById(int id) {
		Task task = entityManager.find(Task.class, id);
		return task;
	}

	// add to the database
	@Override
	public Task save(Task task) {
		Task taskdb = entityManager.merge(task);
		task.setId(taskdb.getId());
		return task;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from Task where id=:taskId");
		q.setParameter("taskId", id);
		q.executeUpdate();
	}
}