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

import model.Currentmeasure;
import model.Measuredefinition;
import model.Person;
import eHealth.rest.business.CurrentMeasureImpl;
import eHealth.rest.business.MeasureDefImpl;
import eHealth.rest.business.PersonImpl;

public class SpecificCurrentStatusService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int cMeasureId;
	
	public SpecificCurrentStatusService(UriInfo uriInfo, Request request,
			int personId, String measureName, int cMeasureId) {
		this.uriInfo= uriInfo;
		this.request= request;
		this.personId=personId;
		this.cMeasureId=cMeasureId;
		this.measureName=measureName;
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updateCurrentStatus(Currentmeasure cmeasure) {
		
		Currentmeasure olddata =CurrentMeasureImpl.getCurrentMeasureById(cMeasureId);
		Response res;
		if(olddata==null)
		{
			
			res=Response.status(Response.Status.NO_CONTENT).header("Location", uriInfo.getAbsolutePath().getPath()).tag("The resource you are looking for not found in the system").build();
		}
		else
		{
			
			cmeasure.setCurrentmeasureid(cMeasureId);
			
			//get person
			Person person= PersonImpl.findPersonById(personId);
			
			// get measure
			Measuredefinition measure= MeasureDefImpl.getMeasureByName(measureName);
			if(measure!= null & person!= null )
			{
				cmeasure.setMeasuredefinition(measure);
				cmeasure.setPerson(person);
			}
			int status=CurrentMeasureImpl.updateCurrentMeasure(cmeasure);
			if(status==1)
			{
			res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(cmeasure).build();
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
	public Response getCurrentMeasure()
	{
		Response res=null;
		Currentmeasure cmeasure= CurrentMeasureImpl.getCurrentMeasureById(cMeasureId);
		if(cmeasure==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(cmeasure)
				    .tag("Current measure information found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		
	}

	@DELETE
	public Response removeCurrentMeasure()
	{
		Response res=null;
		int status=-1;
		Currentmeasure cmeasure=CurrentMeasureImpl.getCurrentMeasureById(cMeasureId);
		if(cmeasure==null){	
			status=1;
		}
		else{
			 status=CurrentMeasureImpl.deleteCurrentMeasure(cmeasure);
			 
		}
		if(status==1)
		{
			res=Response.status(Response.Status.OK).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting Current Measure").build();
		}
		return res;
		
	}
}

