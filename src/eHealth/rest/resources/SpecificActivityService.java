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

import model.Activity;
import eHealth.rest.business.ActivityImpl;

public class SpecificActivityService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int activityId;
	public SpecificActivityService(UriInfo uriInfo, Request request,
			int activityId) {
		this.request=request;
		this.uriInfo=uriInfo;
		this.activityId=activityId;
}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getactivityDetails()
	{
		Response res=null;
		System.out.println("We were here");
		Activity activity=ActivityImpl.getActivity(activityId);
		if(activity==null)
		{
			res=Response.status(Response.Status.NOT_FOUND).header("Location", uriInfo.getAbsolutePath().getPath()).tag("No range information found").build();
		}
		else
		{
		res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(activity).build();
		}
		return res;	
	}
	
	/**
	 * A method for deleting activity information
	 * @return
	 */
	@DELETE
	public Response activityInfo()
	{
		Response res=null;
		int result=-1;
		String responseEntity; 
		Activity activity=ActivityImpl.getActivity(activityId);
		
		if(activity!=null)
		{
			result=ActivityImpl.deleteactivityInfo(activity);
		}
		if(result==1)
		{
		responseEntity="Activity information deleted successfully";	
		}
		else
		{
			responseEntity="Activity information not found on database or error in deleting";
		}
		res=Response.status(Response.Status.NOT_FOUND)
			    .tag(responseEntity)
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	/**
	 * A method for adding activity information
	 * @param activity
	 * @return
	 *//*
	@SuppressWarnings("static-access")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewActivity(Activity activity)
	{
		Response res=null;
		String responseEntity;
		int status=ActivityImpl.addNewActivityInfo(activity);
		if(status==1)
		{
			responseEntity="The new activity information with id of" + activity.getActivityid()+"added successfully";
		}
		else
		{
			responseEntity="Problem adding activity information";
		}
		res.status(Response.Status.CREATED)
	    .entity(responseEntity)
	    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(activity.getActivityid())).build();
	return  res;	
	}*/
	
	
	@SuppressWarnings("static-access")
	@PUT
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response updateActivity(Activity activity)
	{
	/**
	 * Check of the activity information found in the database
	 */
	Activity oldActivity=ActivityImpl.getActivity(activityId);
	Response res = null;
	if(oldActivity==null)
	{
		/**
		 * Create new activity resource if its not found in the database
		 */
		ActivityImpl.addNewActivityInfo(activity);
		res.status(Response.Status.CREATED)
	    .entity(activity)
	    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(activity.getActivityid())).build();
	}
	else
	{
		/**
		 * update id of the old resource to the old resource
		 */
		activity.setActivityid(activityId);
		int status=ActivityImpl.updateactivityInfo(activity);
		if(status==1)
		{
		res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(activity).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating activity information").build();	
		}
		}
	return res;
	}
	
	
}
