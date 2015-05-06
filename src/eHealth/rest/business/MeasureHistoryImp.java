package eHealth.rest.business;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.PeopleDao;
import model.Measurehistory;
import model.Personalgoal;

public class MeasureHistoryImp {

	public static int addNewMeasureHistory(Measurehistory mhistory) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.persist(mhistory);
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
	public static List<Measurehistory> getHistoryMeasureByPersonIdMeasure(
			String measureName, int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Measurehistory.findByPersonIdMeasure",Measurehistory.class).setParameter("measureName", measureName).setParameter("personId",personId);
		List<Measurehistory> hmeasure=null;
		try
		{
			hmeasure=query.getResultList();
		return hmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return hmeasure;
		}	
	}
	@SuppressWarnings("unchecked")
	public static List<Measurehistory> getHistoryMeasureByPersonIdMeasureByDate(
			String measureName, int personId, Date date) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Measurehistory.findByPersonIdMeasureDate",Measurehistory.class).setParameter("measureName", measureName).setParameter("personId",personId).setParameter("measureDate",date);
		List<Measurehistory> hmeasure=null;
		System.out.println("measure in history");
		try
		{
			hmeasure=query.getResultList();
		return hmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return hmeasure;
		}	
	}
	@SuppressWarnings("unchecked")
	public static List<Measurehistory> getHistoryMeasureByPersonIdMeasure(
			int personId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Measurehistory.findByPersonId",Measurehistory.class).setParameter("personId",personId);
		List<Measurehistory> hmeasure=null;
		try
		{
			hmeasure=query.getResultList();
		return hmeasure;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return hmeasure;
		}	
		
	}
	/**
	 * A method for getting the measure history of a goal with a given date time range
	 */
	@SuppressWarnings("unchecked")
	public static List<Measurehistory> getHistoryMeasureByGoalOnDates(int personId,int goalId) {
		//int generalStatus=0;
		/** A variable showing whether the person is below or abovethe expected goal
		 * Can take 3 values
		 * 1:Means the person is fully achieved his/her goal
		 * 0:Means the person is still under his/her goal 
		 * */
		EntityManager em=PeopleDao.instance.getEntityManager();
		Personalgoal personGoal=GoalImpl.getGoalById(goalId);
		Query query=null;
		try
		{
		query=em.createNamedQuery("Measurehistory.findByPersonIdMeasureGoal",Measurehistory.class)
				.setParameter("personId",personId)
				.setParameter("measureDefinition", personGoal.getMeasuredefinition())
				.setParameter("startDate", personGoal.getCreateddate())
				.setParameter("endDate", personGoal.getGoalenddate())
				.setMaxResults(3);	            
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		List<Measurehistory> historyMeasure=null;
		try
		{
			historyMeasure=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return historyMeasure;
	}

}
