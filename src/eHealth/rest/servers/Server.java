package eHealth.rest.servers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {

private static final URI urlParam = URI.create("http://localhost:6902/lifecoach");    

	
	
	/**The following main method allows to take parameters from the command line
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws URISyntaxException
	 */

    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
    	/**
    	 * If the user does not provide the URI ,it default to http://localhost:8089/
    	 */
//    	if(args.length==0)
//    	{
//    		urlParam="http://localhost:6902/";
//    		
//    	}
    	/**
    	 * The user enters a URI and taken as base URI
    	 */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

        System.out.println("Starting standalone HTTP server...");
        JdkHttpServerFactory.createHttpServer(urlParam, createApp());
        System.out.println("Server started on " + urlParam + "\n[kill the process to exit]");
//        String[]urls=new String[]{urlParam.toString()};
//        
//        try {
//			MyPostman.main(urls);
//		} catch (ParseException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
    }
    public static ResourceConfig createApp() {
        System.out.println("Starting sdelab REST services...");
        return new Application_Config();
    }
}
