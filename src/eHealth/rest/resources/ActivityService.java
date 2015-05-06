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

import model.Activity;
import model.Measuredefinition;
import eHealth.rest.business.ActivityImpl;
import eHealth.rest.business.MeasureDefImpl;
@Path("/activity")

public class ActivityService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String measureName;

	public ActivityService(UriInfo uriInfo, Request request, String measureName) {
		this.request=request;
		this.uriInfo=uriInfo;
		this.measureName=measureName;
	}
	
	/**
	 * A method for getting all activities
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivities()
	{
		Response res=null;
		List<Activity>activities=ActivityImpl.getActivity(measureName);
		if(activities==null)
		{
			res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("No range information found").build();
		}
		else
		{
		res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(activities).build();
		}
		return res;	
	}
	/**
	 * A method for adding new activity information for the measure
	 * @return
	 */
	@SuppressWarnings("static-access")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewActivity(Activity activity)
	{
		Response res=null;
		String responseEntity;
		Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
		activity.setMeasuredefinition(mDef);
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
	}
	@Path("{activityId}")
	public SpecificActivityService getPersonResource(@PathParam("activityId") int activityId)
	{
		return new SpecificActivityService(uriInfo,request,activityId);
	}
}
