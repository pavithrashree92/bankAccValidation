package com.java.bank.validation.model;
/*
 * Model class to hold AccValidationResponse information
 * 
 * */
public class AccValidationResponse {
	private String source;
	private boolean isValid;

	public AccValidationResponse(String source, boolean isValid) {
		this.source = source;
		this.isValid = isValid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
}
