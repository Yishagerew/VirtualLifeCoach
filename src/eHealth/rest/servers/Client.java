package eHealth.rest.servers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class Client {
	public static void main(String[]args)
	{
	 ClientConfig config = new ClientConfig();
	    javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
	    WebTarget service = client.target(UriBuilder.fromUri("https://localhost:6902/lifecoach").build());

	    System.out.println(service.path("measure").request().accept(MediaType.APPLICATION_JSON).get().readEntity(String.class));
}
}
