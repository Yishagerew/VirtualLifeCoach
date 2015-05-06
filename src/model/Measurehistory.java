package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the measurehistory database table.
 * 
 */
@Table(name="measurehistory")
@Entity
@XmlRootElement

@NamedQueries({
	@NamedQuery(name="Measurehistory.findAll", query="SELECT m FROM Measurehistory m"),
	@NamedQuery(name="Measurehistory.findByPersonIdMeasure",query="SELECT m FROM Measurehistory m WHERE m.measuredefinition.measurename=:measureName and m.person.personid =:personId ORDER BY m.createddate DESC"),
	@NamedQuery(name="Measurehistory.findByPersonIdMeasureDate",query="SELECT m FROM Measurehistory m WHERE m.measuredefinition.measurename=:measureName and m.person.personid =:personId and m.createddate >= :measureDate ORDER BY m.createddate DESC"),
	@NamedQuery(name="Measurehistory.findByPersonId",query="SELECT m FROM Measurehistory m WHERE m.person.personid =:personId ORDER BY m.createddate DESC")	,
	@NamedQuery(name="Measurehistory.findByPersonIdMeasureGoal",query="SELECT m FROM Measurehistory m WHERE m.person.personid =:personId and m.measuredefinition=:measureDefinition and m.createddate BETWEEN :startDate and :endDate ORDER BY m.createddate DESC")
})
public class Measurehistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int historyid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;


	private float measuredvalue;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeasureDefId")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PersonGoaId")
	private Person person;

	public Measurehistory() {
	}

	public int getHistoryid() {
		return this.historyid;
	}

	public void setHistoryid(int historyid) {
		this.historyid = historyid;
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