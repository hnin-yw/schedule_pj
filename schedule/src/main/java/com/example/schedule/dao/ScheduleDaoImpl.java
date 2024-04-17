package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

	private EntityManager entityManager;

	@Autowired
	public ScheduleDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> getAlls() {
		Query q = (Query) entityManager.createQuery("from Schedule");
		List<Schedule> transactions = q.getResultList();

		return transactions;
	}

	@Override
	public Schedule findScheduleById(int id) {
		Schedule schedule = entityManager.find(Schedule.class, id);
		return schedule;
	}

	// add to the database
	@Override
	public Schedule save(Schedule schedule) {
		Schedule scheduledb = entityManager.merge(schedule);
		schedule.setId(scheduledb.getId());
		return schedule;
	}

	@Override
	public void deleteById(int id) {
		Query q = (Query) entityManager.createQuery("delete from Schedule where id=:scheduleId");
		q.setParameter("scheduleId", id);
		q.executeUpdate();
	}

	@Override
	public List<Schedule> findScheduleListByUserCode(String userCode) {
		Query query = (Query) entityManager.createQuery("from Schedule WHERE userCode =: userCode and delFlg = false");
		query.setParameter("userCode", userCode);
		List<Schedule> schedules = query.getResultList();
		return schedules;
	}
}