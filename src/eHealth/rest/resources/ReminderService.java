package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Person;
import model.Reminder;

import eHealth.rest.business.PersonImpl;
import eHealth.rest.business.ReminderImpl;


@Path("/reminder")
public class ReminderService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	int status;
	
	public ReminderService(UriInfo uriInfo, Request request, int personId,int status) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId=personId;
		this.status=status;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)		
	public Response addNewReminder(Reminder reminder)
	{
		Response res=null;
		String responseEntity;
		//get person
				Person person= PersonImpl.findPersonById(personId);
			
				if(person!= null )
				{
					
					reminder.setPerson(person);
				}
		int status=ReminderImpl.addNewReminder(reminder);
		if(status==1)
		{
			responseEntity="The new reminder with id of" + reminder.getReminderid()+ "added successfully";
		}
		else
		{
			responseEntity="Problem adding care giver information";
		}	
		Response.status(Response.Status.OK)
	    .entity(responseEntity)
	    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(reminder.getReminderid())).build();
	return  res;	
	}
	
	//get all reminders for that person
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getAllReminders()
	{
		Response res=null;
		List<Reminder> reminder = null;
		if(status==1)
		{
		reminder=ReminderImpl.getAllRemindersByPerson(personId);
		}
		else
		{
	    reminder=ReminderImpl.getNewReminders(personId);

		}
		if(reminder==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(reminder)
				    .tag("Reminder information found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		
	}
	@Path("{reminderId}")
	public SpecificReminderService getReminderResource(@PathParam("reminderId") int reminderId, @PathParam("personId") int personId)
	{
		return new SpecificReminderService(uriInfo,request,personId, reminderId);
	}
	

}
