package eHealth.rest.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class onlineQuotes {
public static void main(String[]args)
{
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget service = client.target(UriBuilder.fromUri(
			"http://www.iheartquotes.com/api/v1/random").build());
	Invocation.Builder invocationBuilder = service
			.request(MediaType.APPLICATION_JSON);
	
	/**
	 * A random quote for fortunes
	 */
	
	System.out.println(invocationBuilder.accept(MediaType.APPLICATION_JSON).get().readEntity(String.class));
	
	
	/**
	 * Retrieving food descriptions from food information
	 */
	
	try {
		HttpResponse<JsonNode> response = Unirest.get("https://glennbech-foodfacts.p.mashape.com/fooditem/21252?nutrients=true&weight=true")
				.header("X-Mashape-Key", "4VlAof3IitmshZvmvC5TSnIRGrr7p1wxhlijsnMZDeiScPLLSf")
				.header("Accept", "application/json")
				.asJson();
		System.out.println(response.getBody());
	} catch (UnirestException e) {
		e.printStackTrace();
	}
}
}
