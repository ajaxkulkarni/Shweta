package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Job {
	
	private Integer id;
	private Integer quadrent,position;
	private String patientName;
	private Integer shade;
	private Date date;
	private Person doctor;
	private List<WorkType> workTypes;
	private WorkPersonMap workPersonMap;
	private BigDecimal price;
	
	
	
	public Integer getQuadrent() {
		return quadrent;
	}
	public void setQuadrent(Integer quadrent) {
		this.quadrent = quadrent;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public WorkPersonMap getWorkPersonMap() {
		return workPersonMap;
	}
	public void setWorkPersonMap(WorkPersonMap workPersonMap) {
		this.workPersonMap = workPersonMap;
	}
	
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
	public List<WorkType> getWorkTypes() {
		return workTypes;
	}
	public void setWorkTypes(List<WorkType> workTypes) {
		this.workTypes = workTypes;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
}
