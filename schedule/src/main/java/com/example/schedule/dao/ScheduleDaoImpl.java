package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.schedule.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

	private EntityManager entityManager;

	@Autowired
	public ScheduleDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> findAlls(String userCode, String groupCode, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		Query query = (Query) entityManager.createQuery(
				"FROM Schedule WHERE (userCode =: userCode  OR (userCode <>: userCode AND groupCode =: groupCode AND otherVisibilityFlg = false)) AND scheduleStartDateTime BETWEEN :startDateTime AND :endDateTime and delFlg = false ORDER BY scheduleStartDateTime ASC");
		query.setParameter("userCode", userCode);
		query.setParameter("groupCode", groupCode);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);

		List<Schedule> transactions = query.getResultList();

		return transactions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> findSelectedAlls(Integer[] selectedIds) {
		Query query = (Query) entityManager.createQuery("FROM Schedule WHERE id IN (:selectedIds)");
		query.setParameter("selectedIds", Arrays.asList(selectedIds));
		List<Schedule> transactions = query.getResultList();

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
		Query q = (Query) entityManager.createQuery("delete from Schedule WHERE id=:scheduleId");
		q.setParameter("scheduleId", id);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> findScheduleListByUserCode(String userCode) {
		Query query = (Query) entityManager.createQuery("from Schedule WHERE userCode =: userCode and delFlg = false");
		query.setParameter("userCode", userCode);
		List<Schedule> schedules = query.getResultList();
		return schedules;
	}
}