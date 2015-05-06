package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the person database table.
 * 
 */
@Table(name="person")
@Entity
@NamedQueries
({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p"),
	@NamedQuery(name="Person.findById",query="SELECT p FROM Person p WHERE p.personid=:personId")
})
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int personid;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String firstname;

	private String gender;

	private String lastname;

	private String middlename;

	//bi-directional many-to-one association to Careperson
	@OneToMany(mappedBy="person")
	private List<Careperson> carepersons;

	//bi-directional many-to-one association to Currentmeasure
	@OneToMany(mappedBy="person")
	private List<Currentmeasure> currentmeasures;

	//bi-directional many-to-one association to Measurehistory
	@OneToMany(mappedBy="person")
	private List<Measurehistory> measurehistories;

	//bi-directional many-to-one association to Personalgoal
	@OneToMany(mappedBy="person")
	private List<Personalgoal> personalgoals;

	//bi-directional many-to-one association to Reminder
	@OneToMany(mappedBy="person")
	private List<Reminder> reminders;

	public Person() {
	}

	public int getPersonid() {
		return this.personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
   @XmlTransient
	public List<Careperson> getCarepersons() {
		return this.carepersons;
	}

	public void setCarepersons(List<Careperson> carepersons) {
		this.carepersons = carepersons;
	}

	public Careperson addCareperson(Careperson careperson) {
		getCarepersons().add(careperson);
		careperson.setPerson(this);

		return careperson;
	}

	public Careperson removeCareperson(Careperson careperson) {
		getCarepersons().remove(careperson);
		careperson.setPerson(null);

		return careperson;
	}

	public List<Currentmeasure> getCurrentmeasures() {
		return this.currentmeasures;
	}

	public void setCurrentmeasures(List<Currentmeasure> currentmeasures) {
		this.currentmeasures = currentmeasures;
	}

	public Currentmeasure addCurrentmeasure(Currentmeasure currentmeasure) {
		getCurrentmeasures().add(currentmeasure);
		currentmeasure.setPerson(this);

		return currentmeasure;
	}

	public Currentmeasure removeCurrentmeasure(Currentmeasure currentmeasure) {
		getCurrentmeasures().remove(currentmeasure);
		currentmeasure.setPerson(null);

		return currentmeasure;
	}

	public List<Measurehistory> getMeasurehistories() {
		return this.measurehistories;
	}

	public void setMeasurehistories(List<Measurehistory> measurehistories) {
		this.measurehistories = measurehistories;
	}

	public Measurehistory addMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().add(measurehistory);
		measurehistory.setPerson(this);

		return measurehistory;
	}

	public Measurehistory removeMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().remove(measurehistory);
		measurehistory.setPerson(null);

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
		personalgoal.setPerson(this);

		return personalgoal;
	}

	public Personalgoal removePersonalgoal(Personalgoal personalgoal) {
		getPersonalgoals().remove(personalgoal);
		personalgoal.setPerson(null);

		return personalgoal;
	}

	public List<Reminder> getReminders() {
		return this.reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	public Reminder addReminder(Reminder reminder) {
		getReminders().add(reminder);
		reminder.setPerson(this);

		return reminder;
	}

	public Reminder removeReminder(Reminder reminder) {
		getReminders().remove(reminder);
		reminder.setPerson(null);

		return reminder;
	}

}