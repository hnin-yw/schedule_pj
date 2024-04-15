package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.Group;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {

	private EntityManager entityManager;

	@Autowired
	public GroupDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@Override
	@SuppressWarnings("unchecked")
	public List<Group> getAlls() {
		Query q = (Query) entityManager.createQuery("from Group");
		List<Group> transactions = q.getResultList();

		return transactions;
	}

	@Override
	public Group findGroupById(int id) {
		Group group = entityManager.find(Group.class, id);
		return group;
	}

	// add to the database
	@Override
	public Group save(Group group) {
		Group gp = entityManager.merge(group);
		group.setId(gp.getId());
		return group;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from Group where id=:groupId");
		q.setParameter("groupId", id);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group findGroupCodeByDesc() {
		Query query = (Query) entityManager.createQuery("from Group ORDER BY groupCode DESC LIMIT 1");
		List<Group> groups = query.getResultList();
		if (groups.isEmpty()) {
	        return null;
	    } else {
	        return groups.get(0);
	    }
	}
}