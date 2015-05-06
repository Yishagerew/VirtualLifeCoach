package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import eHealth.rest.business.MeasureRangeImpl;
import model.Measuredefinitionrange;

public class RangeService {
	@Context
    UriInfo uriInfo;
	@Context
	Request request;
	String measureName;
	String rangeName;
	
	public RangeService(UriInfo uriInfo2, Request request2, String rangeName,
			String measureName2) {
		this.uriInfo=uriInfo2;
		this.request=request2;
		this.measureName=measureName2;
		this.rangeName=rangeName;
	}
/*	*//**
	 * @param measureName
	 *  A method for updating measure record
	 *//*
	@SuppressWarnings("static-access")
	@PUT
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response updateMeasure(Measuredefinition mDef)
	{
	*//**
	 * calls a method which returns the person with the given id
	 *//*
	Measuredefinition oldDefinition=MeasureDefImpl.getMeasureByName(measureName);
	Response res = null;
	if(oldDefinition==null)
	{
		*//**
		 * Add the range information as new record 
		 *//*
		MeasureDefImpl.addNewMeasureDef(mDef);
		res.status(Response.Status.CREATED)
	    .entity(mDef)
	    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mDef.getMeasurename())).build();
	}
	else
	{
		*//**
		 * update the field
		 *//*
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
	}*/
	
	/**
	 * A method for changing the contents a measure range information
	 * @param mRange
	 * @return
	 */
	
	@SuppressWarnings("static-access")
	@PUT
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response updateMeasure(Measuredefinitionrange mRange)
	{
	/**
	 * Check of the measure information found in the database
	 */
	Measuredefinitionrange oldmRange=MeasureRangeImpl.getMeasureRange(rangeName, measureName);
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


	/**
	 * A method of getting the detail of a specific range information like min.
	 * @return
	 */
	@SuppressWarnings("unused")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeasureRanges()
	{
		Response res=null;
		Measuredefinitionrange mRange=MeasureRangeImpl.getMeasureRange(rangeName,measureName);
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
}
