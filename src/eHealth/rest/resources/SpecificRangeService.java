package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Measuredefinitionrange;
import eHealth.rest.business.MeasureRangeImpl;

public class SpecificRangeService {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String rangeName;
	
	public SpecificRangeService(UriInfo uriInfo, Request request,
			String measureRangeName) {
			this.uriInfo=uriInfo;
			this.request=request;
			this.rangeName=measureRangeName;
	}
	/**
	 * A method for retrieving a specific range information 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeasureRanges()
	{
		Response res=null;
		List<Measuredefinitionrange>measureRanges=MeasureRangeImpl.getMeasureranges();
		if(measureRanges==null)
		{
			res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("No range information found").build();
		}
		else
		{
		res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(measureRanges).build();
		}
		return res;	
	}
	
	/**
	 * Deleting personal information
	 */
@DELETE
public Response removeMeasureRange()
{
	Response res=null;
	int result=-1;
	String responseEntity; 
	Measuredefinitionrange mRange=MeasureRangeImpl.getMeasureRange(rangeName, "weight");
	
	if(mRange!=null)
	{
		result=MeasureRangeImpl.deleteRangeInfo(mRange);
	}
	if(result==1)
	{
	responseEntity="Measure range information deleted successfully";	
	}
	else
	{
		responseEntity="MeasureRange information not found on database or error in deleting";
	}
	res=Response.status(Response.Status.NOT_FOUND)
		    .tag(responseEntity)
		    .header("Location", uriInfo.getPath()).build();
	return res ;
}
/**
 * A method for adding new measure range information
 */

@SuppressWarnings("static-access")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response addNewMeasureRange(Measuredefinitionrange mRange)
{
	Response res=null;
	String responseEntity;
	int status=MeasureRangeImpl.addNewRangeInfo(mRange);
	if(status==1)
	{
		responseEntity="The new measure definition range with id of" + mRange.getRangeid()+ "added successfully";
	}
	else
	{
		responseEntity="Problem adding measure definition range information";
	}
	res.status(Response.Status.CREATED)
    .entity(responseEntity)
    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mRange.getRangeid())).build();
return  res;	
}
/**
 * A method for updating measure range information
 */
@SuppressWarnings("static-access")
@PUT
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Response updateMeasure(Measuredefinitionrange mRange)
{
/**
 * Check of the measure information found in the database
 */
Measuredefinitionrange oldmRange=MeasureRangeImpl.getMeasureRange(rangeName, "weight");
Response res = null;
if(oldmRange==null)
{
	/**
	 * Create new range resource if its not found in the database
	 */
	MeasureRangeImpl.addNewRangeInfo(mRange);
	res.status(Response.Status.CREATED)
    .entity(mRange)
    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mRange.getRangename())).build();
}
else
{
	/**
	 * update the field
	 */
	mRange.setRangeid(oldmRange.getRangeid());
	int status=MeasureRangeImpl.updateRangeInfo(mRange);
	if(status==1)
	{
	res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(mRange).build();
	}
	else
	{
		res=Response.status(Response.Status.NOT_MODIFIED).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating measure information").build();	
	}
	}
return res;
}

}
