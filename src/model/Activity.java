package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the activities database table.
 * 
 */
@Entity
@Table(name="activities")
@NamedQueries
({
	@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a"),
	@NamedQuery(name="Activity.findByMeasureName",query="SELECT a FROM Activity a WHERE a.measuredefinition =:measureDefinition"),
	@NamedQuery(name="Activity.findByMeasureNameActivityFor",query="SELECT a FROM Activity a WHERE a.measuredefinition =:measureDefinition and a.activityfor=:activityFor" ),
	@NamedQuery(name="Activity.findById", query="SELECT a FROM Activity a WHERE a.activityid=:activityId")
	
})

public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityid;

	private String activitydescription;

	private String activityfor;

	private String activitytitle;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeasureDefId")
	private Measuredefinition measuredefinition;

	public Activity() {
	}

	public int getActivityid() {
		return this.activityid;
	}

	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}

	public String getActivitydescription() {
		return this.activitydescription;
	}

	public void setActivitydescription(String activitydescription) {
		this.activitydescription = activitydescription;
	}

	public String getActivityfor() {
		return this.activityfor;
	}

	public void setActivityfor(String activityfor) {
		this.activityfor = activityfor;
	}

	public String getActivitytitle() {
		return this.activitytitle;
	}

	public void setActivitytitle(String activitytitle) {
		this.activitytitle = activitytitle;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
    @XmlTransient
	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

}