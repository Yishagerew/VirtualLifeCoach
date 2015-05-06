package eHealth.rest.business;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.PeopleDao;
import model.Reminder;

public class ReminderImpl {

	public static int addNewReminder(Reminder reminder) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.persist(reminder);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
		return 1;
	}

	@SuppressWarnings("unchecked")
	public static List<Reminder> getAllReminders() {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Reminder.findAll",Reminder.class);
		List<Reminder> reminder=null;
		try
		{
			reminder=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return reminder;
	}

	public static Reminder getReminderById(int reminderId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Reminder.findById",Reminder.class).setParameter("id", reminderId);
		try
		{
			Reminder reminder=(Reminder) query.getSingleResult();
		return reminder;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static int updateReminder(Reminder reminder) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.merge(reminder);
			System.out.println("after merge");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
		PeopleDao.instance.closeConnections(em);
	return 1;
	}

	public static int deleteReminder(Reminder reminder) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.remove(reminder);
		tx.commit();
		return 1;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public static List<Reminder> getAllRemindersByPerson(int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Reminder.findByPersonId",Reminder.class).setParameter("personId", personId);
		List<Reminder> reminder=null;
		try
		{
			reminder=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return reminder;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Reminder> getAllRemindersByPersonLimited(int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Reminder.findByPersonId",Reminder.class).setParameter("personId", personId).setMaxResults(3);
		List<Reminder> reminder=null;
		try
		{
			reminder=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return reminder;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Reminder> getNewReminders(int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
			/**
			 * Get the current date and time for retrieving uptodate reminders
			 */
	    long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);//DateTime currentDate=new DateTime();
		
		//System.out.println(currentDate);
		System.out.println("we are after date");
Query query = null;
		try
		{
		query=em.createNamedQuery("Reminder.findByPersonIdNew",Reminder.class).setParameter("personId", personId).setParameter("currentDate",ts);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		List<Reminder> reminder=null;
		System.out.println("After query executed");
		try
		{
			reminder=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return reminder;
	}

}
