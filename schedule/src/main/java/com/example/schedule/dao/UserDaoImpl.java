package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<User> getAlls(Pageable pageable) {
        Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.delFlg = false");
        long total = (long) query.getSingleResult();
        
        query = entityManager.createQuery("FROM User WHERE delFlg = false");
        int start = (int) pageable.getOffset();
        List<User> users = query.setFirstResult(start).setMaxResults(pageable.getPageSize()).getResultList();
        
        return new PageImpl<>(users, pageable, total);
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

	@Override
	public User findUserByUsername(String username) {
		Query query = (Query) entityManager.createQuery("from User where userName=:userName");
		query.setParameter("userName", username);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
	        return null;
	    } else {
	        return users.get(0);
	    }
	}
}