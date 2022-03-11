package com.java.bank.validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.bank.validation.model.AccValidationRequest;
import com.java.bank.validation.model.AccountInfo;
import com.java.bank.validation.model.ValidationResult;
import com.java.bank.validation.service.AccountValidationService;
/*
 * AccValidationController controller class
 * 
 * */
@RestController
@RequestMapping("/account")
public class AccValidationController {

	@Autowired
	private AccountValidationService validationService;

	@PostMapping("/validate")
	public ValidationResult validate(@RequestBody AccValidationRequest accountRequest) {
		return validationService.validateAccount(accountRequest.getAccountNumber(), accountRequest.getSources());

	}

}
