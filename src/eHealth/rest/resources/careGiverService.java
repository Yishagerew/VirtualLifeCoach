package eHealth.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import eHealth.rest.business.CareGiverImp;
import model.Caregiver;


@Path("/caregiver")
public class careGiverService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;	
		@SuppressWarnings("static-access")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)		
		public Response addNewCareGiver(Caregiver cgiver)
		{
			Response res=null;
			String responseEntity;
			int status=CareGiverImp.addNewCareGiver(cgiver);
			if(status==1)
			{
				responseEntity="The new care giver with id of" + cgiver.getCaregiverid()+ "added successfully";
			}
			else
			{
				responseEntity="Problem adding care giver information";
			}	
			res.status(Response.Status.OK)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(cgiver.getCaregiverid())).build();
		return  res;	
		}

		@GET
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
		public Response getAllCareGivers()
		{
			Response res=null;
			List<Caregiver> cgiver=CareGiverImp.getAllCareGivers();
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
		
		
		@Path("{careGiverId}")
		public SpecificCareGiverService getCareGiverResource(@PathParam("careGiverId") int careGiverId)
		{
			return new SpecificCareGiverService(uriInfo,request,careGiverId);
		}

}
