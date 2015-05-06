package eHealth.rest.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import eHealth.rest.dao.PeopleDao;
import model.Measuredefinition;
import model.Measuredefinitionrange;

public class MeasureRangeImpl {
@SuppressWarnings("unchecked")

/**
 * A method for retrieving all measure range information
 * @return
 */
public static List<Measuredefinitionrange> getMeasureranges()
{
	EntityManager em=PeopleDao.instance.getEntityManager();
	Query query=em.createNamedQuery("Measuredefinitionrange.findAll", Measuredefinitionrange.class);
	List<Measuredefinitionrange>ranges=query.getResultList();
	return ranges;
}
/**
 * A Method for retrieving measure range information given a measure range name
 * @param rangeName
 *        A Range name
 * @param measureName 
 * @return
 */
public static Measuredefinitionrange getMeasureRange(String rangeName, String measureName)
{
	EntityManager em=PeopleDao.instance.getEntityManager();
	Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
	Query query=em.createNamedQuery("Measuredefinitionrange.findAllByMeasureNameRangeName",Measuredefinitionrange.class).setParameter("rangeName", rangeName).setParameter("measureDefinition", mDef);
	try
	{
		Measuredefinitionrange mRange=(Measuredefinitionrange) query.getSingleResult();
	return mRange;
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		return null;
	}	
}
/**
 * A method for adding new measure range information
 * @param mRange
 *        A measure definition range object
 */
public static int addNewRangeInfo(Measuredefinitionrange mRange)
{
EntityManager em=PeopleDao.instance.getEntityManager();
EntityTransaction tx=em.getTransaction();
try
{
tx.begin();
em.persist(mRange);
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
 * A method for deleting  measure range information
 * @param mRange
 *        A measure definition range object
 */
public static int deleteRangeInfo(Measuredefinitionrange mRange)
{
EntityManager em=PeopleDao.instance.getEntityManager();
EntityTransaction tx=em.getTransaction();
try
{
tx.begin();
em.remove(mRange);
tx.commit();
return -1;
}
catch(PersistenceException e)
{
	System.out.println(e.getMessage());
	return 1;
}

}
public static int updateRangeInfo(Measuredefinitionrange mRange)
{
EntityManager em=PeopleDao.instance.getEntityManager();
EntityTransaction tx=em.getTransaction();
try
{
tx.begin();
em.merge(mRange);
tx.commit();
return -1;
}
catch(Exception e)
{
	System.out.println(e.getMessage());
	return 1;
}
}

/**
 * A method for getting all ranges of a certain measure
 */
@SuppressWarnings("unchecked")
public static List<Measuredefinitionrange> getMeasureRanges(String measureName)
{
	Measuredefinition mDefinition=MeasureDefImpl.getMeasureByName(measureName);
	
	EntityManager em=PeopleDao.instance.getEntityManager();
	Query query=null;
	try
	{
	query=em.createNamedQuery("Measuredefinitionrange.findAllByMeasureName",Measuredefinitionrange.class).setParameter("measureDefinition",mDefinition );
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
	try
	{
		List<Measuredefinitionrange> mRange=query.getResultList();
	return mRange;
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		return null;
	}	
}






}
