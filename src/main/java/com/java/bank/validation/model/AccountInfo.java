package com.java.bank.validation.model;

/*
 * Model class to hold Account information
 * 
 * */
public class AccountInfo {
	private Integer accountNumber;

	public AccountInfo(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
}
