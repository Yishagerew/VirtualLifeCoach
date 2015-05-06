package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the reminders database table.
 * 
 */
@Entity
@Table(name="reminders")
@NamedQueries
({
	@NamedQuery(name="Reminder.findAll", query="SELECT r FROM Reminder r"),
	@NamedQuery(name="Reminder.findById",query="SELECT r FROM Reminder r WHERE r.reminderid=:id"),
	@NamedQuery(name="Reminder.findByPersonId",query="SELECT r FROM Reminder r WHERE r.person.personid=:personId"),
	@NamedQuery(name="Reminder.findByPersonIdNew",query="SELECT r FROM Reminder r WHERE r.person.personid=:personId and r.remindOn > :currentDate")
})
public class Reminder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int reminderid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	@Lob
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REMIND_ON")
	private Date remindOn;

	private String remindertitle;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PersonId")
	private Person person;

	public Reminder() {
	}

	public int getReminderid() {
		return this.reminderid;
	}

	public void setReminderid(int reminderid) {
		this.reminderid = reminderid;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRemindOn() {
		return this.remindOn;
	}

	public void setRemindOn(Date remindOn) {
		this.remindOn = remindOn;
	}

	public String getRemindertitle() {
		return this.remindertitle;
	}

	public void setRemindertitle(String remindertitle) {
		this.remindertitle = remindertitle;
	}
	@XmlTransient
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}