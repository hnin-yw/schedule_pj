package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

	private EntityManager entityManager;

	@Autowired
	public RoleDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleLists() {
		Query query = entityManager.createQuery("from Role");
		return query.getResultList();
	}

	// add to the database
	@Override
	public UserRole saveUserRoles(UserRole userRole) {
		Query query = entityManager.createQuery(
				"INSERT INTO UserRole (userId, roleId) "
						+ "VALUES (:userId, :roleId)");
		query.setParameter("userId", userRole.getUserId());
		query.setParameter("roleId", userRole.getRoleId());
		query.executeUpdate();
		return userRole;
	}

	@Override
	public void deleteUserRolesByUserId(int userId) {
		Query q = (Query) entityManager.createQuery("delete from UserRole WHERE userId=:userId");
		q.setParameter("userId", userId);
		q.executeUpdate();
	}
}