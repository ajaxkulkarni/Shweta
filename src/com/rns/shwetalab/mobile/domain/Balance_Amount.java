package com.rns.shwetalab.mobile.domain;

public class Balance_Amount 
{
	private Integer person_id;
	private Integer id; 
	
	private Integer amount_paid;
	private Integer month;
	private Integer year;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}
	public int getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(Integer total) {
		this.amount_paid = total;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	

}
