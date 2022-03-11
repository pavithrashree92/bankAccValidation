package com.java.bank.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.java.bank.validation.model.Source1;

@Component
public class SourceOneImpl implements SourceInterface {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	Environment env;

	public Source1 getData(Integer accountNumber) {
		String lifecycle = env.getProperty("env.lifeCycle");
		if(lifecycle==null){
			lifecycle ="";
		}
		String source1Url = env.getProperty(lifecycle.concat(".").concat("source1.url"));
		source1Url = source1Url + "/" + accountNumber;
		return restTemplate.getForObject(source1Url, Source1.class);
	}

	@Override
	public boolean isValidSourceData(Integer accountNumber) {
		Source1 source = getData(accountNumber);
		return source.contains(accountNumber);
	}
}
