package com.example.schedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.schedule.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class AttendeeDaoImpl implements AttendeeDao {

	private EntityManager entityManager;

	@Autowired
	public AttendeeDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	// add to the database
	@Override
	public Attendee save(Attendee attendee) {
		Attendee attendeedb = entityManager.merge(attendee);
		attendee.setId(attendeedb.getId());
		return attendee;
	}

	// add to the database
	@Override
	public Attendee saveAttendee(Attendee attendee) {
		Query query = entityManager.createQuery(
				"INSERT INTO Attendee (scheduleId, userCode, responseStatusFlg, responseTime, delFlg, createdBy, createdAt, updatedBy, updatedAt) "
						+ "VALUES (:scheduleId, :userCode, :responseStatusFlg, :responseTime, :delFlg, :createdBy, :createdAt, :updatedBy, :updatedAt)");
		query.setParameter("scheduleId", attendee.getScheduleId());
		query.setParameter("userCode", attendee.getUserCode());
		query.setParameter("responseStatusFlg", attendee.getResponseStatusFlg());
		query.setParameter("responseTime", attendee.getResponseTime());
		query.setParameter("delFlg", attendee.getDelFlg());
		query.setParameter("createdBy", attendee.getCreatedBy());
		query.setParameter("createdAt", attendee.getCreatedAt());
		query.setParameter("updatedBy", attendee.getUpdatedBy());
		query.setParameter("updatedAt", attendee.getUpdatedAt());
		query.executeUpdate();
		return attendee;
	}

	@Override
	public Attendee findAttendeeById(int id) {
		Attendee attendee = entityManager.find(Attendee.class, id);
		return attendee;
	}

	@Override
	public void deleteBySchedulId(int scheduleId) {
		Query q = (Query) entityManager.createQuery("delete from Attendee where scheduleId=:scheduleId");
		q.setParameter("scheduleId", scheduleId);
		q.executeUpdate();
	}
}