package eHealth.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Measuredefinition;
import model.Person;
import model.Personalgoal;
import eHealth.rest.business.GoalImpl;
import eHealth.rest.business.MeasureDefImpl;
import eHealth.rest.business.PersonImpl;

public class SpecificGoal {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int goalId;

	public SpecificGoal(UriInfo uriInfo, Request request, int personId,
			String measureName, int goalId) {
		this.uriInfo= uriInfo;
		this.request= request;
		this.personId=personId;
		this.goalId=goalId;
		this.measureName=measureName;
				
		
	}
	public SpecificGoal(UriInfo uriInfo2, Request request2, int personId2,
			int goalId2) {
			this.uriInfo=uriInfo2;
			this.request=request2;
			this.personId=personId2;
			this.goalId=goalId2;
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updateGoal(Personalgoal pgoal) {
		
		/**
		 * calls a method which returns the care giver with the given id
		 */
		
		Personalgoal olddata =GoalImpl.getGoalById(goalId);
		Response res;
		if(olddata==null)
		{
			/**
			 * Add the new person 
			 */
			res=Response.status(Response.Status.NO_CONTENT).header("Location", uriInfo.getAbsolutePath().getPath()).tag("The resource you are looking for not found in the system").build();
		}
		else
		{
			/**
			 * update the field
			 */
			pgoal.setPersongoalid(goalId);
			
			//get person
			Person person= PersonImpl.findPersonById(personId);
			
			// get measure
			Measuredefinition measure= MeasureDefImpl.getMeasureByName(measureName);
			if(measure!= null & person!= null )
			{
				pgoal.setMeasuredefinition(measure);
				pgoal.setPerson(person);
			}
			int status=GoalImpl.updateGoal(pgoal);
			if(status==1)
			{
			res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(pgoal).build();
			}
			else
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating Personal goal information").build();	
			}
			}
		return res;

}
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getGoal()
	{
		System.out.println("We were here");
		Response res=null;
		Personalgoal persongoal=GoalImpl.getGoalById(goalId);
		if(persongoal==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(persongoal)
				    .tag("personla goal information found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		
	}
	@DELETE
	public Response removeGoal()
	{
		Response res=null;
		int status=-1;
		Personalgoal pgoal=GoalImpl.getGoalById(goalId);
		if(pgoal==null){	
			status=1;
		}
		else{
			 status=GoalImpl.deleteGoal(pgoal);
			 
		}
		if(status==1)
		{
			res=Response.status(Response.Status.OK).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting personal Goal").build();
		}
		return res;
		
	}
	@Path("goaltracking")
	public GoalTrackingService getPersonalGoalTrackingResource()
	{
		System.out.println("Atleast unde goal tttracking");
		return new GoalTrackingService(uriInfo,request,personId,measureName,goalId);
	}
	@Path("dailystatus")
	public DailyStatusService getDailyStatus()
	{
		return new DailyStatusService(uriInfo,request,personId,measureName,goalId);
	}
}
