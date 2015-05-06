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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import model.Person;
import eHealth.rest.business.PersonImpl;


@Path("/people")
public class PeopleService {
	
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		Status stat;
		String responseEntity;
       
		/**
		 * add new person information
		 */
		@SuppressWarnings("static-access")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addNewPerson(Person person)
		{
			Response res=null;
			int status=PersonImpl.addNewPersonInfo(person);
			if(status==1)
			{
				responseEntity="The new person with id of" + person.getPersonid() + "added successfully";
				stat= Response.Status.CREATED;
			}
			else
			{
				responseEntity="Problem adding person information";
				stat= Response.Status.BAD_REQUEST;
			}
			res.status(stat)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(person.getPersonid())).build();
		return  res;	 					 
		}
		
		/**
		 * Getting all person information
		 */
		
		@GET
		@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
		public Response getAllPeople()
		{
			Response res=null;
			List<Person> person=PersonImpl.getAllPerson();
			if(person.size()==0)
			{
				res=Response.status(Response.Status.NOT_FOUND)
					    .tag("No person record in the database")
					    .header("Location", uriInfo.getPath()).build();
				
			}
			else
			{
				
				res=Response.status(Response.Status.OK)
					    .entity(person)
					    .tag("person information found in database")
					    .header("Location", uriInfo.getPath()).build();
				
			}
			return res ;
			
		}
		
		@Path("{personId}")
		public PersonResource getPersonResource(@PathParam("personId") int personId)
		{
			return new PersonResource(uriInfo,request,personId);
		}
								 
}
