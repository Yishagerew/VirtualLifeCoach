package eHealth.rest.business;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Caregiver;
import eHealth.rest.dao.PeopleDao;

public class CareGiverImp {
	public static int addNewCareGiver(Caregiver cgiver) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.persist(cgiver);
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
	
	/**
	 * Return all care giver information
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Caregiver> getAllCareGivers() {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("CareGiver.findAll",Caregiver.class);
		List<Caregiver> vgiver=null;
		try
		{
			vgiver=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return vgiver;
	}

	
	public static int updateCareGivers(Caregiver cgiver) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.merge(cgiver);
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

	
	
	public static Caregiver getCareGiverById(int careGiverId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("CareGiver.findById",Caregiver.class).setParameter("careGiverId", careGiverId);
		try
		{
		Caregiver careGiver=(Caregiver) query.getSingleResult();
		return careGiver;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}	
	}

	public static int deleteCareGiver(Caregiver cGiver) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.remove(cGiver);
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



