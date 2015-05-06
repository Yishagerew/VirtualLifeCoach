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

public class MeasureHistoryService {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int status;
	
	public MeasureHistoryService(UriInfo uriInfo, Request request,
			int personId, String measureName,int status) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId=personId;
		this.measureName=measureName;
		this.status=status;
	}
	/*
	 * this is to get history measure for specific person and specific measure
	*/
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getAllHistoryMeasure()
	{
		
		Response res=null;
		List<Measurehistory> hmeasure=null;
		if(status==0)
		{
             hmeasure= MeasureHistoryImp.getHistoryMeasureByPersonIdMeasure(measureName, personId);
		}
		else
		{
			 hmeasure= MeasureHistoryImp.getHistoryMeasureByPersonIdMeasure(personId);	
		}
		
		if(hmeasure==null )
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("No history measure record in the database")
				    .header("Location", uriInfo.getPath()).build();
			
		}
		else
		{
			
			res=Response.status(Response.Status.OK)
				    .entity(hmeasure)
				    .tag("history measure information found in database")
				    .header("Location", uriInfo.getPath()).build();
			
		}
		return res ;
		
	}
}
