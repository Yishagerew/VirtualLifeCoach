package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the caregivers database table.
 * 
 */
@Entity
@Table(name="caregivers")
@NamedQueries
({
	@NamedQuery(name="CareGiver.findAll", query="SELECT c FROM Caregiver c"),
	@NamedQuery(name="CareGiver.findById",query="SELECT c FROM Caregiver c WHERE c.caregiverid=:careGiverId")
})
public class Caregiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int caregiverid;

	private String firstname;

	private String lastname;

	private String middlename;

	//bi-directional many-to-one association to Careperson
	@OneToMany(mappedBy="caregiver")
	private List<Careperson> carepersons;

	public Caregiver() {
	}

	public int getCaregiverid() {
		return this.caregiverid;
	}

	public void setCaregiverid(int caregiverid) {
		this.caregiverid = caregiverid;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	public List<Careperson> getCarepersons() {
		return this.carepersons;
	}

	public void setCarepersons(List<Careperson> carepersons) {
		this.carepersons = carepersons;
	}

	public Careperson addCareperson(Careperson careperson) {
		getCarepersons().add(careperson);
		careperson.setCaregiver(this);

		return careperson;
	}

	public Careperson removeCareperson(Careperson careperson) {
		getCarepersons().remove(careperson);
		careperson.setCaregiver(null);

		return careperson;
	}

}