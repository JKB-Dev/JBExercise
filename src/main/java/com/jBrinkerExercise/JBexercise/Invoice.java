package com.jBrinkerExercise.JBexercise;

import org.json.JSONObject;

public class Invoice extends JSONObject {
	
	private String ref;
	private String date;
	private String currency;
	private double amount;
	
	public Invoice() {
	}

	public Invoice(String ref, String date, String currency, double amount) {
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
