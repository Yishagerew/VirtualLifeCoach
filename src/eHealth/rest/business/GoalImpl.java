package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.Measuredefinition;
import model.Personalgoal;
import eHealth.rest.dao.PeopleDao;

public class GoalImpl {
	
		public static int addNewGoal(Personalgoal personGoal)
		{
			
			EntityManager em=PeopleDao.instance.getEntityManager();
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			try
			{
			em.persist(personGoal);
			}
			catch(PersistenceException e)
			{
				System.out.println(e.getMessage());
				return -1;
			}
			tx.commit();
			return 1;
			
		}

		public static List<Personalgoal> getAllGoals(int personId) {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressWarnings("unchecked")
		public static List<Personalgoal> getGoalsbyPersonMeasure(int personId,
				String measureName) {
			Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
			
			EntityManager em=PeopleDao.instance.getEntityManager();
			Query query=null;
			try
			{
			query=em.createNamedQuery("Personalgoal.findByPersonIdMeasure", Personalgoal.class).setParameter("measureName", mDef).setParameter("personId", personId);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			List<Personalgoal> personGoal=null;
			try
			{
				personGoal=query.getResultList();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			return personGoal;
		}

		public static Personalgoal getGoalById(int goalId) {
			EntityManager em=PeopleDao.instance.getEntityManager();
			Query query=em.createNamedQuery("Personalgoal.findById",Personalgoal.class).setParameter("goalId", goalId);
			try
			{
			Personalgoal pgoal=(Personalgoal) query.getSingleResult();
			return pgoal;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}	
		}

		public static int updateGoal(Personalgoal pgoal) {
			EntityManager em=PeopleDao.instance.getEntityManager();
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			try
			{
				em.merge(pgoal);
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

		public static int deleteGoal(Personalgoal pgoal) {
			EntityManager em=PeopleDao.instance.getEntityManager();
			EntityTransaction tx=em.getTransaction();
			try
			{
			tx.begin();
			em.remove(pgoal);
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
		public static List<Personalgoal> getGoals(int personId) {
			EntityManager em=PeopleDao.instance.getEntityManager();
			Query query=em.createNamedQuery("Personalgoal.findByMeasureNamePerson",Personalgoal.class).setParameter("personId", personId);
			try
			{
				List<Personalgoal>personalGoals=query.getResultList();
			return personalGoals;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}	
		}
}
