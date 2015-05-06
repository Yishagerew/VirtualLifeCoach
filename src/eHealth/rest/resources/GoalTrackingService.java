package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Measurehistory;

import eHealth.rest.business.MeasureHistoryImp;

public class GoalTrackingService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int goalId;

	public GoalTrackingService(UriInfo uriInfo, Request request, int personId,
			String measureName, int goalId) {
		this.uriInfo= uriInfo;
		this.request= request;
		this.personId=personId;
		this.goalId=goalId;
		this.measureName=measureName;				
	}
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getGoal()
	{
		
		System.out.println("Where we are?");
		
		/**
		 * Operations:
		 * 1. Select measure histories satisfying the criteria for the goal of a person
		 * 2. Obtain the values obtained in array or other stuffs
		 * 3. Make sure if the goal is for increase or decrease
		 * 4. Take the current 3 values and and tell his progress
		 * 5. Make a decision and either send motivations-External data source 1
		 * 6. Make a decision and either send information of some external data source again
		 * 7. Make a summary of his progresses showing the history-Use orchestrated services
		 */
		Response res=null;
	List<Measurehistory>historyMeasures=MeasureHistoryImp.getHistoryMeasureByGoalOnDates(personId, goalId);
	if(historyMeasures.size()==0)
	{
		res=Response.status(Response.Status.NOT_FOUND)
			    .tag("Not found")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	else
	{
		res=Response.status(Response.Status.OK)
			    .entity(historyMeasures)
			    .tag("Goal tracking histories found in the database")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*Response res=null;
Personalgoal pgoal=GoalImpl.getGoalById(goalId);

Currentmeasure cmeasure= CurrentMeasureImpl.getCurrentMeasureByPersonIdMeasure(measureName, personId);

List<Measurehistory> hmeasure= MeasureHistoryImp.getHistoryMeasureByPersonIdMeasureByDate(measureName, personId, pgoal.getCreateddate());
System.out.println("measure history");
if(cmeasure==null)
{
	res=Response.status(Response.Status.NOT_FOUND)
		    .tag("Not found")
		    .header("Location", uriInfo.getPath()).build();
	return res ;
}

else
{
	System.out.println("Your goal was to " + pgoal.getGoaltype() +" " + pgoal.getMeasuredefinition().getMeasurename()+ " by " + pgoal.getGoaltowards()
			+ pgoal.getMeasuredefinition().getUnitofMeasure());
	
	System.out.println("Your Current Status is " + cmeasure.getMeasuredvalue() + cmeasure.getMeasuredefinition().getUnitofMeasure());
	res=Response.status(Response.Status.OK)
		    .entity(hmeasure)
		    .tag("personla goal information found in database")
		    .header("Location", uriInfo.getPath()).build();
	return res ;*/
//}
		
	}
}
