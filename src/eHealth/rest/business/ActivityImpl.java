package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.Activity;
import model.Measuredefinition;
import eHealth.rest.dao.PeopleDao;

public class ActivityImpl {

/**
 * A method for retrieving all activity information
 * @return
 */
	@SuppressWarnings("unchecked")
	public static List<Activity> getAllActivities()
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Activity.findAll", Activity.class);
		List<Activity>activities=query.getResultList();
		return activities;
	}
	
	/**
	 * A method for adding range information
	 * @param mRange
	 * @return
	 */
	public static int addNewActivityInfo(Activity activity)
	{
	EntityManager em=PeopleDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	try
	{
	tx.begin();
	em.persist(activity);
	tx.commit();
	return -1;
	}
	catch(PersistenceException e)
	{
		System.out.println(e.getMessage());
		return 1;
	}

	}
	
	/**
	 * A method for deleting activity information
	 * @param activity
	 * @return
	 */
	public static int deleteactivityInfo(Activity activity)
	{
	EntityManager em=PeopleDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	try
	{
	tx.begin();
	em.remove(activity);
	tx.commit();
	return -1;
	}
	catch(PersistenceException e)
	{
		System.out.println(e.getMessage());
		return 1;
	}

	}
	/**
	 * A method for Activity given its ID 
	 * @param activityId
	 * @return
	 */
	public static Activity getActivity(int activityId)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=null;
		query=em.createNamedQuery("Activity.findById", Activity.class).setParameter("activityId", activityId);	
		try
		{
		Activity activity=(Activity) query.getSingleResult();
		return activity;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * A method for updating activity information
	 * @param activity
	 * @return
	 */
	public static int updateactivityInfo(Activity activity)
	{
	EntityManager em=PeopleDao.instance.getEntityManager();
	EntityTransaction tx=em.getTransaction();
	try
	{
	tx.begin();
	em.merge(activity);
	tx.commit();
	return -1;
	}
	catch(PersistenceException e)
	{
		System.out.println(e.getMessage());
		return 1;
	}
	}
	
	
	/**
	 * A method for getting activities under a measure name
	 * @param measureName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Activity> getActivity(String measureName)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
		Query query=em.createNamedQuery("Activity.findByMeasureName", Activity.class).setParameter("measureDefinition", mDef);
		try
		{
		List<Activity> activities=query.getResultList();
		return activities;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Return a fixed set of elements for the activity and measure name
	 */
	public static Activity getActivityLimited(String measureName,int status)
	{
		String activityFor;
		if(status==1)
		{
			activityFor="decrease";
		}
		else
		{
			activityFor="increase";
		}
		EntityManager em=PeopleDao.instance.getEntityManager();
		Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
		Query query=em.createNamedQuery("Activity.findByMeasureNameActivityFor", Activity.class).setParameter("measureDefinition", mDef).setParameter("activityFor", activityFor).setMaxResults(1);
		try
		{
			/**
			 * can be randomized but ignored for this project
			 */
		Activity activities=(Activity) query.getSingleResult();
		return activities;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
}
