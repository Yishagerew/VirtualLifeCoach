package eHealth.rest.business;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.PeopleDao;
import model.Activity;
import model.Currentmeasure;
import model.Measuredefinitionrange;


public class CurrentMeasureImpl {	

	public static int addNewCurrentMeasure(Currentmeasure cmeasure) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.persist(cmeasure);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
		return 1;
		
	}

	public static int updateCurrentMeasure(Currentmeasure cmeasure) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.merge(cmeasure);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
		
	return 1;
		
	}
	
	public static Currentmeasure getCurrentMeasureByPersonIdMeasure(String measureName,
			int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Currentmeasure.findByPersonIdMeasure",Currentmeasure.class).setParameter("measureName", measureName).setParameter("personId",personId);
		Currentmeasure cmeasure=null;
		try
		{
			cmeasure=(Currentmeasure) query.getSingleResult();
		return cmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return cmeasure;
		}	
	}
	/**
	 * A method for getting all measures of a person
	 * @param measureName
	 * @param personId
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static List<Currentmeasure> getCurrentMeasureByPersonIdMeasureAll(int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Currentmeasure.findByPersonId",Currentmeasure.class).setParameter("personId",personId);
		List<Currentmeasure> cmeasure=null;
		try
		{
			cmeasure=query.getResultList();
		return cmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return cmeasure;
		}	
	}
	
	

	/*@SuppressWarnings("unchecked")
	public static List<Currentmeasure> getCurrentMeasurebyPersonMeasure(
			int personId, String measureName) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("CurrentMeasure.findByPersonIdMeasure",Currentmeasure.class).setParameter("measureName", measureName).setParameter("personId", personId);
		List<Currentmeasure> cmeasure=null;
		try
		{
			cmeasure=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return cmeasure;
	}*/

	public static Currentmeasure getCurrentMeasureById(int cMeasureId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Currentmeasure.findById",Currentmeasure.class).setParameter("cmeasureId", cMeasureId);
		try
		{
			Currentmeasure cmeasure=(Currentmeasure) query.getSingleResult();
		return cmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}	
	}

	public static int deleteCurrentMeasure(Currentmeasure cmeasure) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.remove(cmeasure);
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
	public static List<Currentmeasure> getCurrentMeasuresByPersonId(int personId)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Currentmeasure.findByPersonId",Currentmeasure.class).setParameter("personId", personId);
		try
		{
			List<Currentmeasure> cmeasures=query.getResultList();
		return cmeasures;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}	
	}
	/**
	 * A method for getting the indexes of current measures indexes
	 * @param measures
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<Activity> getListOfRelevantActivities(List<Currentmeasure> measures)
	{
		List<Activity>listOfActivities=null;
		for(int i=0;i<measures.size();i++)
		{
			int status=checkMeasuresAboveRange(measures.get(i).getMeasuredefinition().getMeasurename(),measures.get(i).getMeasuredvalue());
			if(status!=01)
			{
				Activity activityForMeasure=ActivityImpl.getActivityLimited(measures.get(i).getMeasuredefinition().getMeasurename(),status);
				listOfActivities.add(activityForMeasure);
			}
		}
	return listOfActivities;
	
	}
	/**
	 * A method for checking if the measure is in the minimum and max range of values
	 * @param measureName
	 * @param measureValue
	 * @return
	 */
	public static int checkMeasuresAboveRange(String measureName,float measureValue)
	{
		/**
		 * return 0 if the person is safe which is between the min and max
		 * return -1 if the person has to decrease his measure
		 * return +1 if the person has to increase his measure
		 */
		List<Measuredefinitionrange>measureRanges=MeasureRangeImpl.getMeasureRanges(measureName);
		float startValue=measureRanges.get(0).getStartvalue();
		float endValue=measureRanges.get(0).getEndvalue();
		if((measureValue < endValue) && (measureValue > startValue))
				{
			return 0;
		}
		else if(measureValue < startValue)
		{
			return 1;
		}
		return -1;	
	}
	

}
