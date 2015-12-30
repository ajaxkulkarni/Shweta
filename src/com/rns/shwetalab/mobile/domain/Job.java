package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Job {
	
	private Integer id;
	private String patientName;
	private Integer shade;
	private Date date;
	private Person doctor;
	private WorkType workType;
	private WorkPersonMap workPersonMap;
	
	public WorkPersonMap getWorkPersonMap() {
		return workPersonMap;
	}
	public void setWorkPersonMap(WorkPersonMap workPersonMap) {
		this.workPersonMap = workPersonMap;
	}
	private BigDecimal price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Integer getShade() {
		return shade;
	}
	public void setShade(Integer shade) {
		this.shade = shade;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Person getDoctor() {
		return doctor;
	}
	public void setDoctor(Person doctor) {
		this.doctor = doctor;
	}
	public WorkType getWorkType() {
		return workType;
	}
	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
}
