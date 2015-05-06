package eHealth.rest.resources;


import java.util.Date;
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

import model.Currentmeasure;
import model.Measuredefinition;
import model.Measurehistory;
import model.Person;
import eHealth.rest.business.CurrentMeasureImpl;
import eHealth.rest.business.MeasureDefImpl;
import eHealth.rest.business.MeasureHistoryImp;
import eHealth.rest.business.PersonImpl;

public class CurrentStatusService {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int personId;
	String measureName;
	int status;
	
	public CurrentStatusService(UriInfo uriInfo, Request request, int personId, String measurename,int status) {
		this.uriInfo=uriInfo;
		this.request=request;
		this.personId= personId;
		this.measureName= measurename;
		this.status=status;
	}	
	public CurrentStatusService(UriInfo uriInfo2, Request request2,
			int personId2, int status) {
			this.uriInfo=uriInfo2;
			this.request=request2;
			this.status=status;
			this.personId=personId2;
	}

	@SuppressWarnings({ "static-access" })
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)		
	public Response addNewCurrentMeasure(Currentmeasure cmeasure)
	{
		Response res=null;
		String responseEntity;
		int currentMeasureStatus=0;
		int historyMeasureStatus=0;
		 
		
		//check if the measure exist
		Measuredefinition measure= MeasureDefImpl.getMeasureByName(measureName);
		
		//check if the person exist
		Person person= PersonImpl.findPersonById(personId);
		
		//if both exist assign to the current measure
		
		if(person!= null & measure!=null )
		{
		  cmeasure.setMeasuredefinition(measure);
		  cmeasure.setPerson(person);
		}
		
			//if the measure already exist for that person just update it and save the old data in history measure
			//if not just save the data in the current measure table
			
			//get the old measure
			
		   Currentmeasure olddata= CurrentMeasureImpl.getCurrentMeasureByPersonIdMeasure(measureName, personId);
		   System.out.println("here in the method");
			if(olddata == null){ //the first measure
				currentMeasureStatus= CurrentMeasureImpl.addNewCurrentMeasure(cmeasure);
			}
			else{
				 
				Measurehistory mhistory= new Measurehistory();
				
				// set values for measure history
			   Date cr=olddata.getCreateddate();
			   System.out.println("here in the updateCurrentMeasure"+ cr);
				mhistory.setCreateddate(cr); 
				mhistory.setMeasuredefinition(measure);
				float measuredValue= olddata.getMeasuredvalue();
				mhistory.setMeasuredvalue(measuredValue);
				mhistory.setPerson(person);
				//mhistory. (olddata.getCreateddate());//date time today or just remove this field
				
				
				// update current measure
				
				cmeasure.setCurrentmeasureid(olddata.getCurrentmeasureid());
				currentMeasureStatus = CurrentMeasureImpl.updateCurrentMeasure(cmeasure);
				
				// save measure history
				historyMeasureStatus= MeasureHistoryImp.addNewMeasureHistory(mhistory);
				System.out.println("here in the addNewMeasureHistory");
			}
				
		
		
		if(currentMeasureStatus==1 & historyMeasureStatus==1)
		{
			responseEntity="The new current measure with id of" + cmeasure.getCurrentmeasureid()+ "added successfully";
			res.status(Response.Status.OK)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(cmeasure.getCurrentmeasureid())).build();
		}
		else
		{
			responseEntity="Problem adding current measure for specific person measure";
			res.status(Response.Status.NOT_FOUND)
		    .entity(responseEntity)
		    .header("Location", uriInfo.getAbsolutePath().getPath()+"/"+String.valueOf(cmeasure.getCurrentmeasureid())).build();
		}	
		
	return  res;	
	}
	/*
	 * this is to get current measure for specific person and specific measure
	*/
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public Response getAllCurrentMeasure()
	{
		
		Response res=null;
		Currentmeasure cmeasure=null;
		List<Currentmeasure>currentMeasures=null;
		
		if(status==0)
		{
		cmeasure= CurrentMeasureImpl.getCurrentMeasureByPersonIdMeasure(measureName, personId);
		if(cmeasure!=null)
		{
			res=Response.status(Response.Status.OK)
				    .entity(cmeasure)
				    .tag("current measure information found in database")
				    .header("Location", uriInfo.getPath()).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("No current measured record in the database")
				    .header("Location", uriInfo.getPath()).build();	
		}
		}
		else
		{
		currentMeasures=CurrentMeasureImpl.getCurrentMeasureByPersonIdMeasureAll(personId);
		if(currentMeasures!=null)
		{
			res=Response.status(Response.Status.OK)
				    .entity(currentMeasures)
				    .tag("current measure information found in database")
				    .header("Location", uriInfo.getPath()).build();
		}
		else
		{
			res=Response.status(Response.Status.NOT_FOUND)
				    .tag("No current measured record in the database")
				    .header("Location", uriInfo.getPath()).build();
		}	
		}
		return res ;
	}
	
	@Path("{cmeasureId}")
	public SpecificCurrentStatusService getCurrentStatusResource(@PathParam("personId") int personId, @PathParam("measureName") String measureName, @PathParam("cmeasureId") int cMeasureId)
	{
		return new SpecificCurrentStatusService(uriInfo,request,personId,measureName,cMeasureId);
	}

}
