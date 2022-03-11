package com.java.bank.validation.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
/*
 * Model class to hold Source2 information
 * 
 * */
public class Source2 {

	private String sourceName;
	private List<AccountInfo> accInfoList;

	public Source2(String sourceName, List<AccountInfo> accInfoList) {
		super();
		this.sourceName = sourceName;
		this.accInfoList = accInfoList;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public List<AccountInfo> getAccountDetailList() {
		if (CollectionUtils.isEmpty(accInfoList)) {
			return new ArrayList<AccountInfo>();
		}
		return accInfoList;
	}

	public void setAccountDetailList(List<AccountInfo> accInfoList) {
		this.accInfoList = accInfoList;
	}
	
	public boolean contains(Integer accountNumber) {
		return accInfoList.stream().anyMatch(i -> i.getAccountNumber().equals(accountNumber));
	}

}
