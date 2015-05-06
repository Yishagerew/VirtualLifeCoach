package eHealth.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PeopleDao {
instance;
private static EntityManagerFactory emf;
/**
 * Demonstrates the use of ThreadLocal design pattern 
 * EntityManager is not threadsafe
 * We can implement ThreadLocal pattern for avoiding thread safety issues.
 * An entity manager will be checked if its alive
 */
public static final ThreadLocal<EntityManager>threadlocal=new ThreadLocal<EntityManager>();
public static EntityManagerFactory getEntityManagerfactory()
{
if(emf==null)
{
emf=Persistence.createEntityManagerFactory("VirtualLifeCoachApp");
}
return emf;
}
public EntityManager getEntityManager()
{
EntityManager em = threadlocal.get();
if(em==null)
{
em=getEntityManagerfactory().createEntityManager();
threadlocal.set(em);
 
}
return em;
}
public void closeConnections(EntityManager em) {
	em.close();
}
}
