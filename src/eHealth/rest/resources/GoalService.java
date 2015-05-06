package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import model.Measuredefinition;
import model.Person;
import model.Personalgoal;
import eHealth.rest.business.GoalImpl;
import eHealth.rest.business.MeasureDefImpl;
import eHealth.rest.business.PersonImpl;
public class GoalService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int status;

	public GoalService(UriInfo uriInfo, Request request, int personId, String measureName,int status) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId=personId;
		this.measureName= measureName;
		this.status=status;
	}
	public GoalService(UriInfo uriInfo2, Request request2, int personId2,int status) {
		this.personId=personId2;
		this.request=request2;
		this.uriInfo=uriInfo2;
		this.status=status;
	}
/**
 * Add new goal information for the person
 */
	@SuppressWarnings("static-access")
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewGoal(Personalgoal personGoal)
	{
		
		Response res=null;
		String responseEntity;
		//get person
		Person person= PersonImpl.findPersonById(personId);
		
		// get measure
		Measuredefinition measure= MeasureDefImpl.getMeasureByName(measureName);
		if(measure!= null & person!= null )
		{
			personGoal.setMeasuredefinition(measure);
		    personGoal.setPerson(person);
		}
		int status=GoalImpl.addNewGoal(personGoal);
		if(status==1)
		{
			responseEntity="Personal goal addded successfully";
		}
		else
		{
			responseEntity="Problem adding goal information";
		}
		res.status(Response.Status.OK)
	    .entity(responseEntity)
	    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(personId)).build();
		return res;	
	}
	
	/*
	 * this is to get goals for specific person and specific measure
	*/
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getAllGoal()
	{
		Response res=null;
		
		List<Personalgoal> personGoal=null;
		if(status==0)
		{
		personGoal=GoalImpl.getGoalsbyPersonMeasure(personId, measureName);
		}
		else
		{
			personGoal=GoalImpl.getGoals(personId);
		}
		
		if(personGoal.size()==0)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("No personal goal record in the database")
				    .header("Location", uriInfo.getPath()).build();
			
		}
		else
		{
			
			res=Response.status(Response.Status.OK)
				    .entity(personGoal)
				    .tag("personal goal information found in database")
				    .header("Location", uriInfo.getPath()).build();
			
		}
		return res ;
		
	}
	/**
	 * Note: Here the pathparam of goal is used for placeholder purpose and nothing more than that.
	 * Could be an easy way out for this purpose.
	 * @param goal
	 * @param goalId
	 * @return
	 */
	@Path("{goalId}")
	public SpecificGoal getPersonalGoalResource(@PathParam("personId") int personId, @PathParam("goal") String goal, @PathParam("goalId") int goalId)
	{

		return new SpecificGoal(uriInfo,request,personId,goalId);
	}
}
