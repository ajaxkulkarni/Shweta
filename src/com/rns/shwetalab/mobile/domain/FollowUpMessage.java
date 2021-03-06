package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;
import java.util.Date;

public class FollowUpMessage 
{
	private Integer id;
	private String name;
	private String description;
	private Date date;
	public Integer getId() {
		return id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
