package eHealth.rest.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import model.Person;
import eHealth.rest.business.PersonImpl;

public class PersonResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	int personId;
	public PersonResource(UriInfo uriInfo, Request request, int personId) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId=personId;
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updatePerson(Person person) {
		
		/**
		 * calls a method which returns the person with the given id to check if that person exist
		 */
		
		Person olddata = PersonImpl.findPersonById(personId);
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
			
			person.setPersonid(personId);
			int status=PersonImpl.updatePersonInfo(person);
			if(status==1)
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(person).build();
			}
			else
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating care giver information").build();	
			}
			}
		return res;

		
	}
	
	/**
	 * Getting individual information
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonalDetails()
	{
		Response res=null;
		Person person=PersonImpl.findPersonById(personId);
		
		if(person==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(person)
				    .tag("Person found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
	}
	
	
			/**
			 * Deleting peronal information
			 */
		@DELETE
		public Response removePerson()
		{
			Response res=null;
			int result=PersonImpl.deletePersonInfo(personId);
			String responseEntity;
			if(result==1)
			{
			responseEntity="Person deleted successfully";	
			}
			else
			{
				responseEntity="Person not found on database or error in deleting";
			}
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag(responseEntity)
				    .header("Location", uriInfo.getPath()).build();
			return res ;
			
		}
		
		
		@Path("goal/{measureName}")  
		public GoalService getPersonResourceGoal(@PathParam("personId") int personId, @PathParam("goal") String goals,@PathParam("measureName") String measureName)
		{
			return new GoalService(uriInfo,request,personId,measureName,0);
		}
		@Path("goals")  
		public GoalService getAllPersonResourceGoal(@PathParam("personId") int personId, @PathParam("goal") String goals)
		{
			return new GoalService(uriInfo,request,personId,1);
		}
		
		/*@Path("goal")  // to see all the goals made by each person
		public GoalService getPersonResourceGoal(@javax.ws.rs.PathParam("personId") int personId)
		{
			return new GoalService(uriInfo,request,personId);
		}*/
		
		@Path("/currentmeasure/{measureName}") //the name of the measure we are to measure now
		public CurrentStatusService getPersonResource(@PathParam("personId") int personId,@PathParam("measureName") String measureName)
		{
			return new CurrentStatusService(uriInfo,request,personId,measureName,0);
		}
		@Path("currentmeasure") //the name of the measure we are to measure now
		public CurrentStatusService getPersonResourceAll(@PathParam("personId") int personId)
		{
			return new CurrentStatusService(uriInfo,request,personId,1);
		}
		
		
		@Path("/measurehistory/{measureName}") 
		public MeasureHistoryService getPersonHistoryResource(@javax.ws.rs.PathParam("personId") int personId,@javax.ws.rs.PathParam("measureName") String measureName)
		{
			return new MeasureHistoryService(uriInfo,request,personId,measureName,0);
		}
		
		@Path("measurehistory") 
		public MeasureHistoryService getPersonHistoryResourceAll(@javax.ws.rs.PathParam("personId") int personId,@javax.ws.rs.PathParam("measureName") String measureName)
		{
			return new MeasureHistoryService(uriInfo,request,personId,measureName,1);
		}
		
		
		
		@Path("reminder") 
		public ReminderService  getReminderResource(@PathParam("personId") int personId)
		{
			return new ReminderService(uriInfo,request,personId,0);
		}
		
		/**
		 * A method for calling the resource returning the history of reminders for a person 
		 * with person Id given.
		 * @param personId
		 * @return
		 */
		@Path("reminder/history") 
		public ReminderService  getReminderHistoryResource(@PathParam("personId") int personId)
		{
			return new ReminderService(uriInfo,request,personId,1);
		}
		@Path("suggestedactivities") //the name of the measure we are to measure now
		public SuggestedActivityService  getSuggestedActivity()
		{
			return new SuggestedActivityService(uriInfo,request,personId);
		}
		

}
