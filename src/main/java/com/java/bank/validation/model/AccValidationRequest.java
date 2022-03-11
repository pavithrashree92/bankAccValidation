package com.java.bank.validation.model;

import java.util.List;
/*
 * Model class to hold AccValidationRequest information
 * 
 * */
public class AccValidationRequest {

	private Integer accountNumber;
	private List<String> sources;
	
	public Integer getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public List<String> getSources() {
		return sources;
	}
	
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	
	
}
