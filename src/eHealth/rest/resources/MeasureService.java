package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import eHealth.rest.business.MeasureDefImpl;
import eHealth.rest.business.MeasureRangeImpl;
import model.Measuredefinition;
import model.Measuredefinitionrange;
public class MeasureService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	public MeasureService(Request request2,UriInfo uriInfo2, int personId2,
			String measureName) {
		this.uriInfo=uriInfo2;
		this.request=request2;
		this.personId=personId2;
		this.measureName=measureName;
	}
public MeasureService(UriInfo uriInfo2, Request request2,
			String measureName2) {
		this.uriInfo=uriInfo2;
		this.request=request2;
		this.measureName=measureName2;
	}

/**
 * A method for adding new range information for a specific measure name
 * @param cMeasure
 * @return
 */
@SuppressWarnings("static-access")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response addMeasurRange(Measuredefinitionrange mRange)
{
	Response res=null;
	String responseEntity;
	/**
	 * Get the measure object before adding the range information to the database
	 */
	Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
	mRange.setMeasuredefinition(mDef);
	int status=MeasureRangeImpl.addNewRangeInfo(mRange);
	if(status==1)
	{
		responseEntity="The new measure definition with id of" + mRange.getRangeid()+ "added successfully";
	}
	else
	{
		responseEntity="Problem adding measure definition information";
	}
	res.status(Response.Status.OK)
    .entity(responseEntity)
    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mRange.getRangeid())).build();
return  res;	
}

/**
 * A method for retrieving all measure ranges-ranges of a specific measure like weight
 */

@GET
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
public Response getAllMeasureRanges()
{
	Response res=null;
	List<Measuredefinitionrange> measureRanges=MeasureRangeImpl.getMeasureRanges(measureName);
	if(measureRanges==null)
	{
		res=Response.status(Response.Status.NOT_FOUND)
			    .tag("Not found")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	else
	{
		res=Response.status(Response.Status.OK)
			    .entity(measureRanges)
			    .tag("measure range information found in database")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	
}

/**
 * A method for deleting measure information-like a whole weight measure
 * @param rangeName
 * @return
 */
@DELETE
public Response deleteMeasureinformation()
{
	Response res=null;
	int status=-1;
	
	Measuredefinition mDef=MeasureDefImpl.getMeasureByName(measureName);
	if(mDef==null){	
		status=1;
	}
	else{
		 status=MeasureDefImpl.deleteMeasureInformation(mDef);
		 
	}
	if(status==1)
	{
		res=Response.status(Response.Status.NOT_FOUND).build();
	}
	else
	{
		res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting resource").build();
	}
	return res;
}

/**
 * A method for changing the content of the measure information
 * @param rangeName
 * @return
 */


@SuppressWarnings("static-access")
@PUT
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Response updateMeasure(Measuredefinition mDef)
{
/**
 * calls a method which returns the person with the given id
 */
Measuredefinition oldDefinition=MeasureDefImpl.getMeasureByName(measureName);
Response res = null;
if(oldDefinition==null)
{
	/**
	 * Add the range information as new record 
	 */
	MeasureDefImpl.addNewMeasureDef(mDef);
	res.status(Response.Status.CREATED)
    .entity(mDef)
    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mDef.getMeasurename())).build();
}
else
{
	/**
	 * update the field
	 */
	mDef.setMeasuredefid(oldDefinition.getMeasuredefid());
	int status=MeasureDefImpl.updateMeasureInformation(mDef);
	if(status==1)
	{
	res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(mDef).build();
	}
	else
	{
		res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating measure information").build();	
	}
	}
return res;
}

@Path("{RangeName}")
public RangeService getPersonResource(@PathParam("RangeName") String rangeName )
{
	return new RangeService(uriInfo,request,rangeName,measureName);
}	
@Path("activity")
public ActivityService getActivityResource()
{
	return new ActivityService(uriInfo,request,measureName);
}
}
