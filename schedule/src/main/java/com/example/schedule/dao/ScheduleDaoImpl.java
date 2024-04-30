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

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Schedule> findAlls(String userCode, String groupCode, LocalDateTime startDateTime,
//			LocalDateTime endDateTime) {
//		Query query = (Query) entityManager.createQuery(
//				"FROM Schedule WHERE (userCode =: userCode  OR (userCode <>: userCode AND groupCode =: groupCode AND otherVisibilityFlg = false)) AND scheduleStartDateTime BETWEEN :startDateTime AND :endDateTime and delFlg = false ORDER BY scheduleStartDateTime ASC");
//		query.setParameter("userCode", userCode);
//		query.setParameter("groupCode", groupCode);
//		query.setParameter("startDateTime", startDateTime);
//		query.setParameter("endDateTime", endDateTime);
//
//		List<Schedule> transactions = query.getResultList();
//
//		return transactions;
//	}

	// get all the transactions from the database
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Schedule> findAlls(Pageable pageable, String userCode, String groupCode) {
		Query query = entityManager.createQuery(
				"SELECT COUNT(s) FROM Schedule s WHERE (userCode =: userCode  OR (userCode <>: userCode AND groupCode =: groupCode AND otherVisibilityFlg = false)) AND delFlg = false ORDER BY scheduleStartDateTime ASC");
		query.setParameter("userCode", userCode);
		query.setParameter("groupCode", groupCode);
		long total = (long) query.getSingleResult();

		query = entityManager.createQuery(
				"FROM Schedule WHERE (userCode =: userCode  OR (userCode <>: userCode AND groupCode =: groupCode AND otherVisibilityFlg = false)) AND delFlg = false ORDER BY scheduleStartDateTime ASC");
		query.setParameter("userCode", userCode);
		query.setParameter("groupCode", groupCode);
		int start = (int) pageable.getOffset();
		List<Schedule> schedules = query.setFirstResult(start).setMaxResults(pageable.getPageSize()).getResultList();

		return new PageImpl<>(schedules, pageable, total);
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
				"INSERT INTO Schedule (scheduleCode, groupCode, userCode, scheduleTitle, scheduleStartDateTime, scheduleEndDateTime, allDayFlg, repeatType, repeatUntil, repeatDayOfWeek, repeatTypeOfMonth, scheduleDisplayFlg, location, meetLink, scheduleDescription, scheduleThemeColor, otherVisibilityFlg, eventFlg, scheduleStatusFlg, delFlg, createdBy, createdAt, updatedBy, updatedAt) "
						+ "VALUES (:scheduleCode, :groupCode, :userCode, :scheduleTitle, :scheduleStartDateTime, :scheduleEndDateTime, :allDayFlg, :repeatType, :repeatUntil, :repeatDayOfWeek, :repeatTypeOfMonth, :scheduleDisplayFlg, :location, :meetLink, :scheduleDescription, :scheduleThemeColor, :otherVisibilityFlg, :eventFlg, :scheduleStatusFlg, :delFlg, :createdBy, :createdAt, :updatedBy, :updatedAt)");
		query.setParameter("scheduleCode", schedule.getScheduleCode());
		query.setParameter("groupCode", schedule.getGroupCode());
		query.setParameter("userCode", schedule.getUserCode());
		query.setParameter("scheduleTitle", schedule.getScheduleTitle());
		query.setParameter("scheduleStartDateTime", schedule.getScheduleStartDateTime());
		query.setParameter("scheduleEndDateTime", schedule.getScheduleEndDateTime());
		query.setParameter("allDayFlg", schedule.getAllDayFlg());
		query.setParameter("repeatType", schedule.getRepeatType());
		query.setParameter("repeatUntil", schedule.getRepeatUntil());
		query.setParameter("repeatDayOfWeek", schedule.getRepeatDayOfWeek());
		query.setParameter("repeatTypeOfMonth", schedule.getRepeatTypeOfMonth());
		query.setParameter("scheduleDisplayFlg", schedule.getScheduleDisplayFlg());
		query.setParameter("location", schedule.getLocation());
		query.setParameter("meetLink", schedule.getMeetLink());
		query.setParameter("scheduleDescription", schedule.getScheduleDescription());
		query.setParameter("scheduleThemeColor", schedule.getScheduleThemeColor());
		query.setParameter("otherVisibilityFlg", schedule.getOtherVisibilityFlg());
		query.setParameter("eventFlg", schedule.getEventFlg());
		query.setParameter("scheduleStatusFlg", schedule.getScheduleStatusFlg());
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