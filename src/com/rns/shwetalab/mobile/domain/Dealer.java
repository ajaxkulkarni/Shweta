package com.rns.shwetalab.mobile.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Dealer 
{
	private Integer id;
	
	private String material;
	private BigDecimal price;
	private BigDecimal amount_paid;
	private Date date;
	private Person dealer;
	private String Name;

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Person getDealer() {
		return dealer;
	}
	public void setDealer(Person dealer) {
		this.dealer = dealer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(BigDecimal amount_paid) {
		this.amount_paid = amount_paid;
	}

}
