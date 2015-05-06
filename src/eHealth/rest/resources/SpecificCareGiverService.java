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

import model.Caregiver;
import eHealth.rest.business.CareGiverImp;

public class SpecificCareGiverService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int careGiverId;
	
	
	public SpecificCareGiverService(UriInfo uriInfo, Request request, int careGiverId) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.careGiverId= careGiverId;		
		
	}
	
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updateCareGiver(Caregiver cgiver) {
		
		/**
		 * calls a method which returns the care giver with the given id
		 */
		
		Caregiver olddata = CareGiverImp.getCareGiverById(careGiverId);
		Response res;
		if(olddata==null)
		{
			/**
			 * Add the new person 
			 */
			res=Response.status(Response.Status.NO_CONTENT).header("Location", uriInfo.getAbsolutePath().getPath()).tag("The resource you are looking for not found in the system").build();
		}
		else
		{
			/**
			 * update the field
			 */
			cgiver.setCaregiverid(careGiverId);
			int status=CareGiverImp.updateCareGivers(cgiver);
			if(status==1)
			{
			res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(cgiver).build();
			}
			else
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating care giver information").build();	
			}
			}
		return res;

		
	}
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getCareGiver()
	{
		Response res=null;
		Caregiver cgiver=CareGiverImp.getCareGiverById(careGiverId);
		if(cgiver==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(cgiver)
				    .tag("care giver information found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		
	}
	
	@DELETE
	public Response removeCareGiver()
	{
		Response res=null;
		int status=-1;
		System.out.println(careGiverId);
		Caregiver cGiver=CareGiverImp.getCareGiverById(careGiverId);
		if(cGiver==null){	
			status=1;
		}
		else{
			 status=CareGiverImp.deleteCareGiver(cGiver);
			 
		}
		if(status==1)
		{
			res=Response.status(Response.Status.OK).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting care Giver").build();
		}
		return res;
		
	}

}
