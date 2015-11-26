package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;

public class WorkPersonMap {
	
	private Integer id;
	private WorkType workType;
	private Person person;
	private BigDecimal price;
	
	public WorkType getWorkType() {
		return workType;
	}
	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
