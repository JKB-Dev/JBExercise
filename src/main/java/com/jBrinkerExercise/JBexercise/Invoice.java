package com.jBrinkerExercise.JBexercise;

import java.math.BigDecimal;

import org.json.JSONObject;

public class Invoice extends JSONObject {
	
	private String ref;
	private String date;
	private String currency;
	private BigDecimal amount;
	
	public Invoice() {
	}

	public Invoice(String ref, String date, String currency, BigDecimal amount) {
		super();
		this.ref = ref;
		this.date = date;
		this.currency = currency;
		this.amount = amount;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
