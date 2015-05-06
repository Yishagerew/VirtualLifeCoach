package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the careperson database table.
 * 
 */
@Entity
@Table(name="careperson")
@NamedQuery(name="Careperson.findAll", query="SELECT c FROM Careperson c")
public class Careperson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	@Lob
	private String remark;

	//bi-directional many-to-one association to Caregiver
	@ManyToOne
	@JoinColumn(name="CareGiverId")
	private Caregiver caregiver;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PersonId")
	private Person person;

	public Careperson() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    @XmlTransient
	public Caregiver getCaregiver() {
		return this.caregiver;
	}

	public void setCaregiver(Caregiver caregiver) {
		this.caregiver = caregiver;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}