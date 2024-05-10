package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Group> getGroupLists() {
		Query query = entityManager.createQuery("from Group WHERE delFlg = false");
		return query.getResultList();
	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Group> getAlls(Pageable pageable) {
		Query query = entityManager.createQuery("SELECT COUNT(g) FROM Group g WHERE delFlg=false ORDER BY g.groupCode DESC");
		long total = (long) query.getSingleResult();

		query = entityManager.createQuery("FROM Group WHERE delFlg=false ORDER BY groupCode DESC");
		int start = (int) pageable.getOffset();
		List<Group> groups = query.setFirstResult(start).setMaxResults(pageable.getPageSize()).getResultList();

		return new PageImpl<>(groups, pageable, total);
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