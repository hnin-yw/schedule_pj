package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

	private EntityManager entityManager;

	@Autowired
	public UserDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAlls() {
		Query q = (Query) entityManager.createQuery("from user");
		List<User> transactions = q.getResultList();

		return transactions;
	}

	@Override
	public User findUserById(int id) {
		User user = entityManager.find(User.class, id);
		return user;
	}

	// add to the database
	@Override
	public User save(User user) {
		User userdb = entityManager.merge(user);
		user.setId(userdb.getId());
		return user;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from User where id=:userId");
		q.setParameter("userId", id);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public User findUserCodeByDesc() {
		Query query = (Query) entityManager.createQuery("from User ORDER BY userCode DESC LIMIT 1");
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
	        return null;
	    } else {
	        return users.get(0);
	    }
	}
}