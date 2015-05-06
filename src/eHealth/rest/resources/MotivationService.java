package eHealth.rest.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Path("/motivation")
public class MotivationService {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public HttpResponse<String> getMotivation()
	{
		HttpResponse<String> response=null;
		try {
			response = Unirest.get("https://glennbech-foodfacts.p.mashape.com/fooditem/21252?nutrients=true&weight=true")
					.header("X-Mashape-Key", "4VlAof3IitmshZvmvC5TSnIRGrr7p1wxhlijsnMZDeiScPLLSf")
					.header("Accept", "application/json")
					.asString();
		} catch (UnirestException e) {
    System.out.println(e.getMessage());		
		}
		return response ;
		
	}

}
