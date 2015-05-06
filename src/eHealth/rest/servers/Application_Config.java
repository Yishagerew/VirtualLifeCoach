package eHealth.rest.servers;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("lifecoach")
public class Application_Config extends ResourceConfig{

	public Application_Config()
	{
		/**
		 * A package containing all available resources
		 */
		packages("eHealth");
		
		
	}
}
