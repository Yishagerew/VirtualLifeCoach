package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import eHealth.rest.dao.PeopleDao;
import model.Caregiver;
import model.Careperson;

public class CarePersonImp {

	public static int addNewPersonCareGiver(Careperson cperson) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.persist(cperson);
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

	@SuppressWarnings("unchecked")
	public static List<Careperson> getAllCareGiverforperson() {
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Careperson.findAll",Caregiver.class);
		List<Careperson> vperson=null;
		try
		{
			vperson=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return vperson;
	}

	public static Careperson getPersonCareGiverById(int carePersonId) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		Query query=em.createNamedQuery("Careperson.findById",Caregiver.class).setParameter("id", carePersonId);
		try
		{
		Careperson carePerson=(Careperson) query.getSingleResult();
		return carePerson;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static int updateCarePerson(Careperson cperson) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.merge(cperson);
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

	public static int deleteCarePerson(Careperson cPerson) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		try
		{
		tx.begin();
		em.remove(cPerson);
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


