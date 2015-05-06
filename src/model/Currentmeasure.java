package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the currentmeasure database table.
 * 
 */
@Entity
@Table(name="currentmeasure")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name="Currentmeasure.findAll", query="SELECT c FROM Currentmeasure c"),
	@NamedQuery(name="Currentmeasure.findById", query="SELECT p FROM Currentmeasure p where p.currentmeasureid=:cmeasureId"),
	@NamedQuery(name="Currentmeasure.findByPersonIdMeasure",query="SELECT c FROM Currentmeasure c WHERE c.measuredefinition.measurename=:measureName and c.person.personid =:personId"),
	@NamedQuery(name="Currentmeasure.findByPersonId",query="SELECT c FROM Currentmeasure c WHERE c.person.personid =:personId")


})
public class Currentmeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int currentmeasureid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	private float measuredvalue;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeasureDefId")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PersonalId")
	private Person person;

	public Currentmeasure() {
	}

	public int getCurrentmeasureid() {
		return this.currentmeasureid;
	}

	public void setCurrentmeasureid(int currentmeasureid) {
		this.currentmeasureid = currentmeasureid;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public float getMeasuredvalue() {
		return this.measuredvalue;
	}

	public void setMeasuredvalue(float measuredvalue) {
		this.measuredvalue = measuredvalue;
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