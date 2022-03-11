package com.java.bank.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.java.bank.validation.model.Source2;


@Component
public class SourceTwoImpl implements SourceInterface {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	Environment env;//to set the dev/prod env

	public Source2 getData(Integer accountNumber) {
		String lifecycle = env.getProperty("lifeCycle");
		if(lifecycle==null){
			lifecycle ="";
		}
		String source2Url = env.getProperty(lifecycle.concat(".").concat("source2.url"));
		source2Url = source2Url + "/" + accountNumber;
		return restTemplate.getForObject(source2Url, Source2.class);
	}

	@Override
	public boolean isValidSourceData(Integer accountNumber) {
		Source2 source = getData(accountNumber);
		return source.contains(accountNumber);
	}
}
