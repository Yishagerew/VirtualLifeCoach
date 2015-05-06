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

import eHealth.rest.business.MeasureDefImpl;
import model.Measuredefinition;


@Path("/measure")

public class MeasureDefinitionService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
@SuppressWarnings("static-access")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response addNewMeasure(Measuredefinition mdef)
{
	Response res=null;
	String responseEntity;
	int status=MeasureDefImpl.addNewMeasureDef(mdef);
	if(status==1)
	{
		responseEntity="The new measure definition with id of" + mdef.getMeasuredefid()+ "added successfully";
	}
	else
	{
		responseEntity="Problem adding measure definition information";
	}
	res.status(Response.Status.OK)
    .entity(responseEntity)
    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(mdef.getMeasuredefid())).build();
return  res;	
}
@GET
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
public Response getAllMeasures()
{
	Response res=null;
	List<Measuredefinition> def=MeasureDefImpl.getAllMeasures();
	System.out.println("def returned is"+def);
	if(def==null)
	{
		res=Response.status(Response.Status.NOT_FOUND)
			    .tag("Not found")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	else
	{
		res=Response.status(Response.Status.OK)
			    .entity(def)
			    .tag("measure information found in database")
			    .header("Location", uriInfo.getPath()).build();
		return res ;
	}
	
}
@Path("{measureName}")
public MeasureService getPersonResource(@PathParam("measureName") String measureName )
{
	return new MeasureService(uriInfo,request,measureName);
}




}
