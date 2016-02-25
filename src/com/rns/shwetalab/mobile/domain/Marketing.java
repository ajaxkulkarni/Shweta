package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Marketing 
{
	private Integer id;
	private String marketing_name;
	
	private Date date;
	private String contact;
	private String email;
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarketing_name() {
		return marketing_name;
	}
	public void setMarketing_name(String marketing_name) {
		this.marketing_name = marketing_name;
	}
}
