/**
 * This is a process centric service which calls three separate serices sequentially
 */

package eHealth.rest.resources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import model.Personalgoal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.glassfish.jersey.client.ClientConfig;

import eHealth.rest.business.GoalImpl;
@Path("dailystatus")
public class DailyStatusService {
	@Context
	UriInfo uriInfo;
	@Context 
	Request request;
	String measurename;
	int goalId;
	int personId;
	
	/****************************************************************/
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget service = client.target(UriBuilder.fromUri(
			"http://localhost:6902/lifecoach/").build());
	WebTarget peopleResource = service.path("people");
	/*****************************************************************/
	public DailyStatusService(UriInfo uriInfo2, Request request2, int personId,
			String measureName, int goalId) {
		this.uriInfo=uriInfo2;
		this.request=request2;
		this.goalId=goalId;
		this.measurename=measureName;	
		this.personId=personId;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToPersonReminder()
	{
		/**
		 * Get all measure histories of a goal
		 */
		Response historyResponse=peopleResource.path(String.valueOf(personId))
				                               .path("goals")
				                               .path(String.valueOf(goalId))
				                               .path("goaltracking")
				                               .request(MediaType.APPLICATION_JSON)
				                               .accept(MediaType.APPLICATION_JSON)
				                               .get();
		String historyResponseString = historyResponse.readEntity(String.class);
		JSONParser parser = new JSONParser();
		Object req9JsonObject = null;
		try {
			req9JsonObject = parser.parse(historyResponseString);
		} catch (ParseException e) {
				System.out.println(e.getMessage());		
		}
		JSONArray historyJson = (JSONArray) req9JsonObject;
		int total_Measures = historyJson.size();
		
		double goalMeasuredValue = 0;
		double summedValues=0;
		double averagedValues=0;
		Personalgoal personGoal=null;
		if(total_Measures>0)
		{
		    personGoal=GoalImpl.getGoalById(goalId);
		for (int i = 0; i < total_Measures; i++) {
			goalMeasuredValue=personGoal.getGoaltowards(); //** The value set in the goal *//*
			JSONObject historyJsonObject = (JSONObject) historyJson.get(i);
			summedValues +=Double.parseDouble((String)historyJsonObject.get("measuredvalue"));
		}
		averagedValues=summedValues/total_Measures;
		/**
		 * If the aim of the person is to increase his current measure to some high level
		 */
		int generalStatus=0;
		 String goalAim=personGoal.getGoaltype();
		if(goalAim=="increase")
		{
			if(averagedValues>=goalMeasuredValue)
				
			{
				generalStatus=1;
			}
			else 
			{
				generalStatus=0;
			}
		}
		/**
		 * If the goal of the person is to decrease his measured value
		 */
		if(goalAim=="decrease")
		{
			if(averagedValues<goalMeasuredValue)
				
			{
				generalStatus=1;
			}
			else 
			{
				generalStatus=0;
			}
		}
		/**
		 * If the general status is 1,if the the person achieve his goal,reward with a motivational phrase
		 */
		if(generalStatus==1)
		{
			String responseMotivation=(String)service.path("motivation").request(MediaType.APPLICATION_JSON)
			                          .accept(MediaType.APPLICATION_JSON)
			                          .get().toString();
			/**
			 * The add to the person reminder by calling the suitable method
			 */
			String measurehistoryrecord="{" + "\"description\"" + ": " + "\"" + responseMotivation +"\" "+","
                    + "\"remindertitle\"" + ": " + "\"" + "Motivation Message" +"\" " + "}";
			
			
			/**
			 * Convert into json objects
			 */
			JSONObject jsonMotivation = new JSONObject();
			try {
				jsonMotivation = (JSONObject) parser.parse(measurehistoryrecord);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
			
			Response reminderesponse=peopleResource.path(String.valueOf(personId))
					                               .path("reminder")
					                               .request(MediaType.APPLICATION_JSON)
						               		        .accept(MediaType.APPLICATION_JSON)
						               		        .header("Content-Type", "application/json").post(Entity.entity(jsonMotivation, MediaType.APPLICATION_JSON));
			System.out.println("Result sent to your remindreminderesponseer");
			System.out.println(reminderesponse.toString());
		 }
		else
		{
			/**
			 * Suggest other health information updates
			 */
			WebTarget healthUpdateService = client.target(UriBuilder.fromUri(
					"http://acsyshealthguide.appspot.com/api/healthupdates/format/JSON").build());
			Response healthResponse=healthUpdateService.request().get();
			System.out.println("Here are your updates");
			System.out.println(healthResponse.toString());
		}			                              
		}
		return historyResponse;
		}}
	
	

