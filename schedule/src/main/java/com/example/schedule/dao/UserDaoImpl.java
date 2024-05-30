package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
	@Transactional(readOnly = true)
	public List<User> getUserLists() {
		Query query = entityManager.createQuery("from User WHERE delFlg = false");
		return query.getResultList();
	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<User> getAlls(Pageable pageable) {
		Query query = entityManager
				.createQuery("SELECT COUNT(u) FROM User u WHERE u.delFlg = false ORDER BY u.userCode DESC");
		long total = (long) query.getSingleResult();

		query = entityManager.createQuery("FROM User WHERE delFlg = false ORDER BY userCode DESC");
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
	public int save(User user) {
		Query query = entityManager.createQuery(
				"INSERT INTO User (userCode, groupCode, userName, userFirstName, userLastName, postCode, address, telNumber, email, password, provider, delFlg, createdBy, createdAt, updatedBy, updatedAt) "
						+ "VALUES (:userCode, :groupCode, :userName, :userFirstName, :userLastName, :postCode, :address, :telNumber, :email, :password, :provider, :delFlg, :createdBy, :createdAt, :updatedBy, :updatedAt)");
		query.setParameter("userCode", user.getUserCode());
		query.setParameter("groupCode", user.getGroupCode());
		query.setParameter("userName", user.getUserName());
		query.setParameter("userFirstName", user.getUserFirstName());
		query.setParameter("userLastName", user.getUserLastName());
		query.setParameter("postCode", user.getPostCode());
		query.setParameter("address", user.getAddress());
		query.setParameter("telNumber", user.getTelNumber());
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		query.setParameter("provider", user.getProvider());
		query.setParameter("delFlg", user.getDelFlg());
		query.setParameter("createdBy", user.getCreatedBy());
		query.setParameter("createdAt", user.getCreatedAt());
		query.setParameter("updatedBy", user.getUpdatedBy());
		query.setParameter("updatedAt", user.getUpdatedAt());
		query.executeUpdate();

		return (int) entityManager.createQuery("SELECT MAX(id) FROM User ORDER BY userCode DESC").getSingleResult();
	}

	// add to the database
	@Override
	public int updateUser(User user) {
		Query query = entityManager.createQuery(
				"UPDATE User SET groupCode =: groupCode, userFirstName =: userFirstName, userLastName =: userLastName, postCode =: postCode, address =: address, telNumber =: telNumber, email =: email, updatedBy =: updatedBy, updatedAt =: updatedAt WHERE id=: userId");
		// query.setParameter("userCode", user.getUserCode());
		query.setParameter("groupCode", user.getGroupCode());
		// query.setParameter("userName", user.getUserName());
		query.setParameter("userFirstName", user.getUserFirstName());
		query.setParameter("userLastName", user.getUserLastName());
		query.setParameter("postCode", user.getPostCode());
		query.setParameter("address", user.getAddress());
		query.setParameter("telNumber", user.getTelNumber());
		query.setParameter("email", user.getEmail());
		// query.setParameter("password", user.getPassword());

		// query.setParameter("delFlg", user.getDelFlg());
		// query.setParameter("createdBy", user.getCreatedBy());
		// query.setParameter("createdAt", user.getCreatedAt());
		query.setParameter("updatedBy", user.getUpdatedBy());
		query.setParameter("updatedAt", user.getUpdatedAt());
		query.setParameter("userId", user.getId());
		query.executeUpdate();
		return user.getId();
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from User WHERE id=:userId");
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

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByLoginData(String username, String password) {
		Query query = (Query) entityManager
				.createQuery("from User WHERE userName=:userName and password =: password and delFlg = false");
		query.setParameter("userName", username);
		query.setParameter("password", password);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUserName(String username) {
		String queryString = "from User WHERE userName=:userName AND delFlg = false";
		Query query = entityManager.createQuery(queryString, User.class);
		query.setParameter("userName", username);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserListByGroupCode(String groupCode) {
		Query query = (Query) entityManager.createQuery("from User WHERE groupCode =: groupCode and delFlg = false");
		query.setParameter("groupCode", groupCode);
		List<User> users = query.getResultList();
		return users;
	}
	
	@Override
	public String updateProvider(String userName, String provider) {
		Query query = entityManager.createQuery("UPDATE User SET provider =: provider WHERE userName=: userName");
		query.setParameter("provider", provider);
		query.setParameter("userName", userName);
		query.executeUpdate();
		return userName;
	}
}