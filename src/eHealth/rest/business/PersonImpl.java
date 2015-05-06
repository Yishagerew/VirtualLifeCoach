package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.Person;
import eHealth.rest.dao.PeopleDao;

public class PersonImpl {
	
	/**
	 * A method for adding new person information
	 * @param person
	 *        A person object
	 */
	public static int addNewPersonInfo(Person person)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{		
		   em.persist(person);
		}
		catch(PersistenceException e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		tx.commit();
		return 1;
				
	}
	public static int deletePersonInfo(int personId)
	{
		EntityManager em=PeopleDao.instance.getEntityManager();
		Person person=findPersonById(personId);
		if(person!=null)
		{
			try
			{
	EntityTransaction tx=em.getTransaction();
	tx.begin();
	em.remove(person);
	tx.commit();
	return 1;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		return -1;
	}

/**
 * A person for retrieving the detail of a person given his personal Id
 * @param id
 * @return
 */
public static Person findPersonById(int id)
{
	EntityManager em=PeopleDao.instance.getEntityManager();
	Query query=em.createNamedQuery("Person.findById",Person.class).setParameter("personId", id);
	try
	{
		Person person=(Person) query.getSingleResult();
		return person;
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		return null;
	}	
}
	@SuppressWarnings("unchecked")
	public static List<Person> getAllPerson() {
		
		EntityManager em=PeopleDao.instance.getEntityManager();
		System.out.println("Entitymanager initialized");
		Query query=em.createNamedQuery("Person.findAll",Person.class);
		List<Person> person=null;
		try
		{
			person=query.getResultList();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return person;
	}
	public static int updatePersonInfo(Person person) {
		EntityManager em=PeopleDao.instance.getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try
		{
			em.merge(person);
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
}
