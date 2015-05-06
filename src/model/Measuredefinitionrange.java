package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the measuredefinitionrange database table.
 * 
 */
@Entity

@Table(name="measuredefinitionrange")
@NamedQueries
({
	@NamedQuery(name="Measuredefinitionrange.findAll", query="SELECT m FROM Measuredefinitionrange m"),
	@NamedQuery(name="Measuredefinitionrange.findAllByMeasureName", query="SELECT m FROM Measuredefinitionrange m WHERE m.measuredefinition=:measureDefinition"),
	@NamedQuery(name="Measuredefinitionrange.findAllByMeasureNameRangeName", query="SELECT m FROM Measuredefinitionrange m WHERE m.measuredefinition=:measureDefinition and m.rangename=:rangeName")
})
public class Measuredefinitionrange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int rangeid;

	private float endvalue;

	private String rangename;

	private float startvalue;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeasureDefId")
	private Measuredefinition measuredefinition;

	public Measuredefinitionrange() {
	}

	public int getRangeid() {
		return this.rangeid;
	}

	public void setRangeid(int rangeid) {
		this.rangeid = rangeid;
	}

	public float getEndvalue() {
		return this.endvalue;
	}

	public void setEndvalue(float endvalue) {
		this.endvalue = endvalue;
	}

	public String getRangename() {
		return this.rangename;
	}

	public void setRangename(String rangename) {
		this.rangename = rangename;
	}

	public float getStartvalue() {
		return this.startvalue;
	}

	public void setStartvalue(float startvalue) {
		this.startvalue = startvalue;
	}
    @XmlTransient
	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

}