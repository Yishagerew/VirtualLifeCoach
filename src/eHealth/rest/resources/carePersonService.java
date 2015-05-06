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

import eHealth.rest.business.CareGiverImp;
import eHealth.rest.business.CarePersonImp;
import eHealth.rest.business.PersonImpl;
import model.Caregiver;
import model.Careperson;
import model.Person;

@Path("/carePerson")
public class carePersonService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
		
	@SuppressWarnings("static-access")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)		
	public Response addNewPersonCareGiver(Careperson cperson)
	{
		Response res=null;
		String responseEntity;
		int status=0;
		
		//check if the care giver exist
		int careGiverId= cperson.getCaregiver().getCaregiverid();
		Caregiver cgiver= CareGiverImp.getCareGiverById(careGiverId);
		
		//check if the person exist
		int personId= cperson.getPerson().getPersonid();
		Person person= PersonImpl.findPersonById(personId);
		
		//if both exist save the data
		
		if(person!= null & cgiver!=null )
		{
		 status=CarePersonImp.addNewPersonCareGiver(cperson);
		}
		
		
		if(status==1)
		{
			responseEntity="The new care giver with id of" + cperson.getCaregiver().getCaregiverid()+ "added successfully";
			res.status(Response.Status.OK)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(cperson.getCaregiver().getCaregiverid())).build();
		}
		else
		{
			responseEntity="Problem adding care giver for specific person information";
			res.status(Response.Status.NOT_FOUND)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(cperson)).build();
		}	
		
	return  res;	
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getAllCareGiverforPerson()
	{
		Response res=null;
		List<Careperson> cperson= CarePersonImp.getAllCareGiverforperson();
		if(cperson==null)
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("Not found")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		else
		{
			res=Response.status(Response.Status.OK)
				    .entity(cperson)
				    .tag("care giver information found in database")
				    .header("Location", uriInfo.getPath()).build();
			return res ;
		}
		
	}
	@Path("{carePersonId}")
	public SpecificCarePersonService getCarePersonResource(@PathParam("carePersonId") int carePersonId)
	{
		return new SpecificCarePersonService(uriInfo,request,carePersonId);
	}

}
