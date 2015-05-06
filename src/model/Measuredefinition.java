package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the measuredefinition database table.
 * 
 */
@Table(name="measuredefinition")
@Entity
@NamedQueries({
	@NamedQuery(name="Measuredefinition.findAll", query="SELECT m FROM Measuredefinition m"),
	@NamedQuery(name="Measuredefinition.findByName",query="SELECT m FROM Measuredefinition m WHERE m.measurename=:measureName")
}
	)
public class Measuredefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int measuredefid;

	@Lob
	private String description;

	private String measurename;

	@Column(name="UNITOF_MEASURE")
	private String unitofMeasure;

	//bi-directional many-to-one association to Activity
	@OneToMany(mappedBy="measuredefinition")
	private List<Activity> activities;

	//bi-directional many-to-one association to Currentmeasure
	@OneToMany(mappedBy="measuredefinition")
	private List<Currentmeasure> currentmeasures;

	//bi-directional many-to-one association to Measuredefinitionrange
	@OneToMany(mappedBy="measuredefinition")
	private List<Measuredefinitionrange> measuredefinitionranges;

	//bi-directional many-to-one association to Measurehistory
	@OneToMany(mappedBy="measuredefinition")
	private List<Measurehistory> measurehistories;

	//bi-directional many-to-one association to Personalgoal
	@OneToMany(mappedBy="measuredefinition")
	private List<Personalgoal> personalgoals;

	public Measuredefinition() {
	}

	public int getMeasuredefid() {
		return this.measuredefid;
	}

	public void setMeasuredefid(int measuredefid) {
		this.measuredefid = measuredefid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeasurename() {
		return this.measurename;
	}

	public void setMeasurename(String measurename) {
		this.measurename = measurename;
	}

	public String getUnitofMeasure() {
		return this.unitofMeasure;
	}

	public void setUnitofMeasure(String unitofMeasure) {
		this.unitofMeasure = unitofMeasure;
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public Activity addActivity(Activity activity) {
		getActivities().add(activity);
		activity.setMeasuredefinition(this);

		return activity;
	}

	public Activity removeActivity(Activity activity) {
		getActivities().remove(activity);
		activity.setMeasuredefinition(null);

		return activity;
	}

	public List<Currentmeasure> getCurrentmeasures() {
		return this.currentmeasures;
	}

	public void setCurrentmeasures(List<Currentmeasure> currentmeasures) {
		this.currentmeasures = currentmeasures;
	}

	public Currentmeasure addCurrentmeasure(Currentmeasure currentmeasure) {
		getCurrentmeasures().add(currentmeasure);
		currentmeasure.setMeasuredefinition(this);

		return currentmeasure;
	}

	public Currentmeasure removeCurrentmeasure(Currentmeasure currentmeasure) {
		getCurrentmeasures().remove(currentmeasure);
		currentmeasure.setMeasuredefinition(null);

		return currentmeasure;
	}

	public List<Measuredefinitionrange> getMeasuredefinitionranges() {
		return this.measuredefinitionranges;
	}

	public void setMeasuredefinitionranges(List<Measuredefinitionrange> measuredefinitionranges) {
		this.measuredefinitionranges = measuredefinitionranges;
	}

	public Measuredefinitionrange addMeasuredefinitionrange(Measuredefinitionrange measuredefinitionrange) {
		getMeasuredefinitionranges().add(measuredefinitionrange);
		measuredefinitionrange.setMeasuredefinition(this);

		return measuredefinitionrange;
	}

	public Measuredefinitionrange removeMeasuredefinitionrange(Measuredefinitionrange measuredefinitionrange) {
		getMeasuredefinitionranges().remove(measuredefinitionrange);
		measuredefinitionrange.setMeasuredefinition(null);

		return measuredefinitionrange;
	}

	public List<Measurehistory> getMeasurehistories() {
		return this.measurehistories;
	}

	public void setMeasurehistories(List<Measurehistory> measurehistories) {
		this.measurehistories = measurehistories;
	}

	public Measurehistory addMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().add(measurehistory);
		measurehistory.setMeasuredefinition(this);

		return measurehistory;
	}

	public Measurehistory removeMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().remove(measurehistory);
		measurehistory.setMeasuredefinition(null);

		return measurehistory;
	}

	public List<Personalgoal> getPersonalgoals() {
		return this.personalgoals;
	}

	public void setPersonalgoals(List<Personalgoal> personalgoals) {
		this.personalgoals = personalgoals;
	}

	public Personalgoal addPersonalgoal(Personalgoal personalgoal) {
		getPersonalgoals().add(personalgoal);
		personalgoal.setMeasuredefinition(this);

		return personalgoal;
	}

	public Personalgoal removePersonalgoal(Personalgoal personalgoal) {
		getPersonalgoals().remove(personalgoal);
		personalgoal.setMeasuredefinition(null);

		return personalgoal;
	}

}