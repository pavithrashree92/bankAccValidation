package com.java.bank.validation.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.java.bank.validation.model.AccValidationResponse;
import com.java.bank.validation.model.Source1;
import com.java.bank.validation.model.Source2;
import com.java.bank.validation.model.ValidationResult;

@Component
public class AccountValidationService {
	@Autowired
	private SourceOneImpl dataSourceService1;

	@Autowired
	private SourceTwoImpl dataSourceService2;

	@Autowired
	Environment env;

	public static final String SOURCE_1 = "source1";
	public static final String SOURCE_2 = "source2";

	public ValidationResult validateAccount(Integer accountNumber, List<String> sourceList) {

		String allowedSources = env.getProperty("source.name");
		ValidationResult result = new ValidationResult();
		List<String> allowedSourceList = Arrays.asList(StringUtils.split(allowedSources, ","));
		if (CollectionUtils.isEmpty(sourceList)) {
			result.addResult(new AccValidationResponse(SOURCE_1, dataSourceService1.isValidSourceData(accountNumber)));
			result.addResult(new AccValidationResponse(SOURCE_2, dataSourceService2.isValidSourceData(accountNumber)));
		} else {
			if (CollectionUtils.containsAny(sourceList, allowedSourceList)) {
				if (sourceList.contains(SOURCE_1)) {
					result.addResult(new AccValidationResponse(SOURCE_1, dataSourceService1.isValidSourceData(accountNumber)));
				}
				if (sourceList.contains(SOURCE_2)) {
					result.addResult(new AccValidationResponse(SOURCE_2, dataSourceService2.isValidSourceData(accountNumber)));
				}
			}else {
				result.addResult(new AccValidationResponse("Invalid Data Source", false));
			}
		}
		return result;
	}

}
