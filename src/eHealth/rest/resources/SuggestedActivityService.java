package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Activity;
import model.Currentmeasure;
import model.Person;
import model.Personalgoal;
import model.Reminder;
import eHealth.rest.business.CurrentMeasureImpl;
import eHealth.rest.business.GoalImpl;
import eHealth.rest.business.PersonImpl;
import eHealth.rest.business.ReminderImpl;

public class SuggestedActivityService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;

	public SuggestedActivityService(UriInfo uriInfo, Request request, int personId) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId=personId;
	}
	/**
	 * Getting individual information
	 */
	@SuppressWarnings({ "unused", "null" })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Response> getSuggestedActivities()
	{
		List<Response>res=null;
		Response resReminder=null;
		/**
		 * Getting a list of reminders for the person
		 */
		List<Reminder>reminders=ReminderImpl.getAllRemindersByPersonLimited(personId);
		Person person=PersonImpl.findPersonById(personId);
		
		if(reminders==null)
		{
			resReminder=Response.status(Response.Status.NOT_FOUND)
				    .tag("You have no reminders,please focus on other suggested activities")
				    .header("Location", uriInfo.getPath()).build();
		}
		else
		{
			resReminder=Response.status(Response.Status.OK)
				    .entity(reminders)
				    .tag("You have the following reminders")
				    .header("Location", uriInfo.getPath()).build();
		}
		
		
		/**
		 * Getting list of activities based on current measure and measure range
		 */
		
		Response resCurrentMeasure=null;
		
		List<Currentmeasure> currentMeasures=CurrentMeasureImpl.getCurrentMeasuresByPersonId(personId);
		
		/**
		 * Store indexes
		 */
		List<Activity>returnedActivities=CurrentMeasureImpl.getListOfRelevantActivities(currentMeasures);
		if(returnedActivities==null)
		{
			resReminder=Response.status(Response.Status.NOT_FOUND)
				    .tag("You have a better current status and no activity is suggested based on this.")
				    .header("Location", uriInfo.getPath()).build();
		}
		else
		{
			resReminder=Response.status(Response.Status.OK)
				    .entity(returnedActivities)
				    .tag("You have suggested the following activities based on your current status")
				    .header("Location", uriInfo.getPath()).build();
		}
		/**
		 * Getting activities based on their goal
		 * Steps:
		 * Getting goals whose end date are not over
		 * Suggest possibilities based on those goals
		 */
		
		Response resPersonalGoal=null;
		List<Personalgoal>personalGoals=GoalImpl.getGoals(personId);
		
		res.add(resCurrentMeasure);
		res.add(resReminder);
		return res;
		
	}
	

}
