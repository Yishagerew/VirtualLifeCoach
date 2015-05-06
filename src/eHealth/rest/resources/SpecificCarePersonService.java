package eHealth.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Caregiver;
import model.Careperson;
import model.Person;
import eHealth.rest.business.CareGiverImp;
import eHealth.rest.business.CarePersonImp;
import eHealth.rest.business.PersonImpl;

public class SpecificCarePersonService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int carePersonId;

	public SpecificCarePersonService(UriInfo uriInfo, Request request,
			int carePersonId) {
		
		this.uriInfo=uriInfo;
		this.request=request;
		this.carePersonId= carePersonId;	
		
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON })
	public Response updatePersonCareGiver(Careperson cperson) {
		
		/**
		 * calls a method which returns the person with the given id
		 */
		
		Careperson olddata = CarePersonImp.getPersonCareGiverById(carePersonId);
		Response res;
		int status=0;
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
			//cperson.setCareGiverId(carePersonId);
			
			//check if the care giver exist
			Caregiver careGiverId= cperson.getCaregiver();
			Caregiver cgiver= CareGiverImp.getCareGiverById(careGiverId.getCaregiverid());
			
			//check if the person exist
			Person p= cperson.getPerson();
			Person person= PersonImpl.findPersonById(p.getPersonid());
			
			//if both exist save the data
			
			if(person!= null & cgiver!=null )
			{
			   status=CarePersonImp.updateCarePerson(cperson);
			}
			if(status==1)
			{
				res=Response.status(Response.Status.OK).header("Location", uriInfo.getAbsolutePath().getPath()).entity(cperson).build();
			}
			else
			{
				res=Response.status(Response.Status.NOT_FOUND).header("Location", uriInfo.getAbsolutePath().getPath()).tag("Problem updating care giver information").build();	
			}
			}
		return res;

		
	}
	@DELETE
	public Response removeCarePerson()
	{
		Response res=null;
		int status=-1;
		Careperson cPerson=CarePersonImp.getPersonCareGiverById(carePersonId);
		if(cPerson==null){	
			status=1;
		}
		else{
			 status= CarePersonImp.deleteCarePerson(cPerson);
			 
		}
		if(status==1)
		{
			res=Response.status(Response.Status.NOT_FOUND).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_MODIFIED).tag("Problem deleting care Giver").build();
		}
		return res;
		
	}

}
