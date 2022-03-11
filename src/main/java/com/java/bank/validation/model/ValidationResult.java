package com.java.bank.validation.model;

import java.util.ArrayList;
import java.util.List;
/*
 * Model class to hold ValidationResult information
 * 
 * */
public class ValidationResult {
	private List<AccValidationResponse> result;

	public List<AccValidationResponse> getResult() {
		return result;
	}

	public void addResult(AccValidationResponse response) {
		if (this.result == null) {
			result = new ArrayList<>();
		}
		result.add(response);
	}
}
