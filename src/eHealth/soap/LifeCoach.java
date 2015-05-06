package eHealth.soap;


import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import model.Measurehistory;


@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.ENCODED)
public interface LifeCoach {
	/**
	 * A method which returns the history recorded for achieving a goal of a person.
	 * @return
	 */
	@WebMethod(operationName="readpersonalGoalhistory")
	@WebResult(name="AchievementHistory")
	public List<Measurehistory> readpersonalGoalhistory();
	
	
	
}
