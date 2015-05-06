package eHealth.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

public class SpecificReminderService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	int reminderId;

	public SpecificReminderService(UriInfo uriInfo, Request request,int personId,
			int reminderId) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId= personId;
		this.reminderId= reminderId;
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updateReminder(Reminder reminder) {
		
		Reminder olddata = ReminderImpl.getReminderById(reminderId);
		Response res;
		int status=0;
		if(olddata==null)
		{
			
			res=Response.status(Response.Status.NO_CONTENT).header("Location", uriInfo.getAbsolutePath().getPath()).tag("The resource you are looking for not found in the system").build();
		}
		else
		{
			/**
			 * update the field
			 */
			
			
			//check if the person exist
			
			Person person= PersonImpl.findPersonById(personId);
			
			//if both exist save the data
			
			if(person!= null)
			{
			   reminder.setPerson(person);
			   reminder.setReminderid(olddata.getReminderid());
			   status=ReminderImpl.updateReminder(reminder);
			}
			if(status==1)
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(reminder).build();
			}
			else
			{
				res=Response.status(Response.Status.NOT_FOUND).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating reminder information").build();	
			}
			}
		return res;

		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getReminder()
	{
		Response res=null;
		Reminder reminder=ReminderImpl.getReminderById(reminderId);
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
	
	@DELETE
	public Response removeReminder()
	{
		Response res=null;
		int status=-1;
		Reminder reminder=ReminderImpl.getReminderById(reminderId);
		if(reminder==null){	
			status=1;
		}
		else{
			 status= ReminderImpl.deleteReminder(reminder);
			 
		}
		if(status==1)
		{
			res=Response.status(Response.Status.NOT_FOUND).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting reminder").build();
		}
		return res;
		
	}

}
