package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Measuredefinition;
import eHealth.rest.dao.PeopleDao;

public class MeasureDefImpl {

	public static int addNewMeasureDef(Measuredefinition mdef) {
		System.out.println("under new add measure");
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		System.out.println("we after transaction begin");
		tx.begin();
		try
		{
			em.persist(mdef);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
	return 1;
	}

	/**
	 * Return all measure information
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Measuredefinition> getAllMeasures() {
		System.out.println("Under the method");
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Measuredefinition.findAll",Measuredefinition.class);
		List<Measuredefinition> mdef=null;
		try
		{
		mdef=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return mdef;
	}
	public static Measuredefinition getMeasureByName(String measureName)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Measuredefinition.findByName",Measuredefinition.class).setParameter("measureName", measureName);
		Measuredefinition mDef=null;
		try
		{
		mDef=(Measuredefinition) query.getSingleResult();
		return mDef;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return mDef;
		}	
	}
	
	/**
	 * A method for updating the contents of a given measure information
	 */
	public static int updateMeasureInformation(Measuredefinition mDef)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.merge(mDef);
		tx.commit();
		return 1;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	/**
	 * A method for implementation for deleting measure from database
	 */
	public static int deleteMeasureInformation(Measuredefinition mDef)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.remove(mDef);
		tx.commit();
		return 1;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return -1;
	}
}
