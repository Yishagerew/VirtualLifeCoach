package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;
/**
 * The persistent class for the personalgoals database table.
 * 
 */
@Entity
@Table(name="personalgoals")
@XmlRootElement
@NamedQueries
({
	@NamedQuery(name="Personalgoal.findAll", query="SELECT p FROM Personalgoal p"),
	@NamedQuery(name="Personalgoal.findById", query="SELECT p FROM Personalgoal p where p.persongoalid=:goalId"),
	@NamedQuery(name="Personalgoal.findByPersonIdMeasure",query="SELECT c FROM Personalgoal c WHERE c.measuredefinition=:measureName and c.person.personid =:personId"),
	@NamedQuery(name="Personalgoal.findByMeasureNamePerson", query="SELECT p FROM Personalgoal p WHERE p.person.personid=:personId ORDER BY p.goalenddate DESC")

})
public class Personalgoal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int persongoalid;

	@Column(name="ACTIVITY_FREQUENCY")
	private String activityFrequency;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date goalenddate;

	private float goaltowards;

	private String goaltype;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeasureDefId")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PersonId")
	private Person person;

	public Personalgoal() {
	}

	public int getPersongoalid() {
		return this.persongoalid;
	}

	public void setPersongoalid(int persongoalid) {
		this.persongoalid = persongoalid;
	}

	public String getActivityFrequency() {
		return this.activityFrequency;
	}

	public void setActivityFrequency(String activityFrequency) {
		this.activityFrequency = activityFrequency;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getGoalenddate() {
		return this.goalenddate;
	}

	public void setGoalenddate(Date goalenddate) {
		this.goalenddate = goalenddate;
	}

	public float getGoaltowards() {
		return this.goaltowards;
	}

	public void setGoaltowards(float goaltowards) {
		this.goaltowards = goaltowards;
	}

	public String getGoaltype() {
		return this.goaltype;
	}

	public void setGoaltype(String goaltype) {
		this.goaltype = goaltype;
	}
	@XmlTransient
	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}
	@XmlTransient
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}