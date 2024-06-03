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
import java.util.Arrays;
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
	@Transactional(readOnly = true)
	public Page<Schedule> findAlls(Pageable pageable, String userCode, String groupCode) {
		Query query = entityManager.createQuery(
				"SELECT COUNT(s) FROM Schedule s WHERE (s.userCode =: userCode OR (s.userCode <>: userCode AND s.groupCode =: groupCode AND s.otherVisibilityFlg = false) OR s.id IN (SELECT a.scheduleId FROM Attendee a WHERE a.userCode =: userCode AND a.responseStatusFlg = true AND a.delFlg = false)) AND s.delFlg = false ORDER BY s.scheduleStartDateTime ASC");
		query.setParameter("userCode", userCode);
		query.setParameter("groupCode", groupCode);
		long total = (long) query.getSingleResult();

		query = entityManager.createQuery(
				"FROM Schedule s WHERE (s.userCode =: userCode OR (s.userCode <>: userCode AND s.groupCode =: groupCode AND s.otherVisibilityFlg = false) OR s.id IN (SELECT a.scheduleId FROM Attendee a WHERE a.userCode =: userCode AND AND a.responseStatusFlg = true a.delFlg = false)) AND s.delFlg = false ORDER BY s.scheduleStartDateTime ASC");
		query.setParameter("userCode", userCode);
		query.setParameter("groupCode", groupCode);
		int start = (int) pageable.getOffset();
		List<Schedule> schedules = query.setFirstResult(start).setMaxResults(pageable.getPageSize()).getResultList();

		return new PageImpl<>(schedules, pageable, total);
	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Schedule> findAllSchedules(String userCode, String groupCode) {
		String queryString;
		if (groupCode != null) {
			queryString = "SELECT s FROM Schedule s WHERE (s.userCode = :userCode OR (s.userCode <> :userCode AND s.groupCode = :groupCode) OR s.id IN (SELECT a.scheduleId FROM Attendee a WHERE a.userCode = :userCode AND a.responseStatusFlg = true AND a.delFlg = false)) AND s.delFlg = false";
		} else {
			queryString = "SELECT s FROM Schedule s WHERE (s.userCode = :userCode OR s.id IN (SELECT a.scheduleId FROM Attendee a WHERE a.userCode = :userCode AND a.responseStatusFlg = true AND a.delFlg = false)) AND s.delFlg = false";
		}

		Query query = entityManager.createQuery(queryString);
		query.setParameter("userCode", userCode);
		if (groupCode != null) {
			query.setParameter("groupCode", groupCode);
		}

		List<Schedule> scheduleList = query.getResultList();
		return scheduleList;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> findScheduleListByScheduleCode(String scheduleCode) {
		Query query = (Query) entityManager
				.createQuery("from Schedule WHERE scheduleCode =: scheduleCode and delFlg = false");
		query.setParameter("scheduleCode", scheduleCode);
		List<Schedule> schedules = query.getResultList();
		return schedules;
	}

	// add to the database
	@Override
	public Schedule save(Schedule schedule) {
		Schedule scheduledb = entityManager.merge(schedule);
		schedule.setId(scheduledb.getId());
		return schedule;
	}

	// add to the database
	@Override
	public int saveSchedule(Schedule schedule) {
		Query query = entityManager.createQuery(
				"INSERT INTO Schedule (scheduleCode, groupCode, userCode, title, start, end, allDay, repeatType, repeatUntil, repeatDayOfWeek, repeatTypeOfMonth, scheduleDisplayFlg, location, meetLink, scheduleDescription, color, otherVisibilityFlg, isTask, scheduleStatusFlg, guestPermissionFlg,delFlg, createdBy, createdAt, updatedBy, updatedAt) "
						+ "VALUES (:scheduleCode, :groupCode, :userCode, :title, :start, :end, :allDay, :repeatType, :repeatUntil, :repeatDayOfWeek, :repeatTypeOfMonth, :scheduleDisplayFlg, :location, :meetLink, :scheduleDescription, :color, :otherVisibilityFlg, :isTask, :scheduleStatusFlg, :guestPermissionFlg, :delFlg, :createdBy, :createdAt, :updatedBy, :updatedAt)");
		query.setParameter("scheduleCode", schedule.getScheduleCode());
		query.setParameter("groupCode", schedule.getGroupCode());
		query.setParameter("userCode", schedule.getUserCode());
		query.setParameter("title", schedule.getTitle());
		query.setParameter("start", schedule.getStart());
		query.setParameter("end", schedule.getEnd());
		query.setParameter("allDay", schedule.getAllDay());
		query.setParameter("repeatType", schedule.getRepeatType());
		query.setParameter("repeatUntil", schedule.getRepeatUntil());
		query.setParameter("repeatDayOfWeek", schedule.getRepeatDayOfWeek());
		query.setParameter("repeatTypeOfMonth", schedule.getRepeatTypeOfMonth());
		query.setParameter("scheduleDisplayFlg", schedule.getScheduleDisplayFlg());
		query.setParameter("location", schedule.getLocation());
		query.setParameter("meetLink", schedule.getMeetLink());
		query.setParameter("scheduleDescription", schedule.getScheduleDescription());
		query.setParameter("color", schedule.getColor());
		query.setParameter("otherVisibilityFlg", schedule.getOtherVisibilityFlg());
		query.setParameter("isTask", schedule.getIsTask());
		query.setParameter("scheduleStatusFlg", schedule.getScheduleStatusFlg());
		query.setParameter("guestPermissionFlg", schedule.getGuestPermissionFlg());
		query.setParameter("delFlg", schedule.getDelFlg());
		query.setParameter("createdBy", schedule.getCreatedBy());
		query.setParameter("createdAt", schedule.getCreatedAt());
		query.setParameter("updatedBy", schedule.getUpdatedBy());
		query.setParameter("updatedAt", schedule.getUpdatedAt());
		query.executeUpdate();

		return (int) entityManager.createQuery("SELECT MAX(id) FROM Schedule ORDER BY scheduleCode DESC")
				.getSingleResult();
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

	@Override
	@SuppressWarnings("unchecked")
	public Schedule findScheduleCodeByDesc() {
		Query query = (Query) entityManager.createQuery("from Schedule ORDER BY scheduleCode DESC LIMIT 1");
		List<Schedule> schedules = query.getResultList();
		if (schedules.isEmpty()) {
			return null;
		} else {
			return schedules.get(0);
		}
	}
}