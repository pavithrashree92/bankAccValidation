package com.java.bank.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

import com.java.bank.validation.controller.AccValidationController;
import com.java.bank.validation.model.AccountInfo;
import com.java.bank.validation.model.Source1;
import com.java.bank.validation.model.Source2;
import com.java.bank.validation.service.AccountValidationService;
import com.java.bank.validation.service.SourceOneImpl;
import com.java.bank.validation.service.SourceTwoImpl;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class BankAccValidationApplicationTests {	

	@Autowired
	private MockMvc mock;
	
	@Autowired
	AccountValidationService validationService;
	
	@InjectMocks
	private AccValidationController controller;
	
	private Source1 source1 = createTestDataSource1();
	
	private Source2 source2 = createTestDataSource2();
	
	@Before
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(controller).build();
	}

	private Source1 createTestDataSource1() {
		AccountInfo accountInfo1 = new AccountInfo(12345678); 
		AccountInfo accountInfo2 = new AccountInfo(12343333); 
		AccountInfo accountInfo3 = new AccountInfo(12341111); 
		List<AccountInfo> accountInfoList = new ArrayList<>();
		accountInfoList.add(accountInfo1);
		accountInfoList.add(accountInfo2);
		accountInfoList.add(accountInfo3); 
		return new Source1("source1",accountInfoList);
	}
	
	private Source2 createTestDataSource2() {
		AccountInfo accountInfo4 = new AccountInfo(12345638); 
		AccountInfo accountInfo5 = new AccountInfo(12343333); 
		AccountInfo accountInfo6 = new AccountInfo(12342222); 
		List<AccountInfo> accountInfoList2 = new ArrayList<>();
		accountInfoList2.add(accountInfo4);
		accountInfoList2.add(accountInfo5);
		accountInfoList2.add(accountInfo6); 
		return new Source2("source2",accountInfoList2);	
	}
	
	@Test
	void testValidation() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File requestFile = new File(classLoader.getResource("SampleRequest.json").getFile());
		String request = new String(Files.readAllBytes(requestFile.toPath()));
		SourceOneImpl mockDataSourceService1Impl = mock(SourceOneImpl.class);
		SourceTwoImpl mockDataSourceService2Impl = mock(SourceTwoImpl.class);
		FieldUtils.writeField(validationService, "dataSourceService1", mockDataSourceService1Impl, true);
		FieldUtils.writeField(validationService, "dataSourceService2", mockDataSourceService2Impl, true);
		doReturn(source1).when(mockDataSourceService1Impl).getData(anyInt());
		doReturn(true).when(mockDataSourceService1Impl).isValidSourceData(anyInt());
		doReturn(source2).when(mockDataSourceService2Impl).getData(anyInt());
		MvcResult mvcResult = mock
		.perform(post("/account/validate")
		.contentType(MediaType.APPLICATION_JSON)
		.content(request))
		.andExpect(status().isOk())
		.andReturn();
		
		File responseFile = new File(classLoader.getResource("SampleResponse.json").getFile());
		String response = new String(Files.readAllBytes(responseFile.toPath()));
		
		assertEquals(StringUtils.trimAllWhitespace(response), StringUtils.trimAllWhitespace(mvcResult.getResponse().getContentAsString()));
	}
	
	@Test
	void testValidationWithDataSource2() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File requestFile = new File(classLoader.getResource("Source2Request.json").getFile());
		String request = new String(Files.readAllBytes(requestFile.toPath()));
		SourceOneImpl mockDataSourceService1Impl = mock(SourceOneImpl.class);
		SourceTwoImpl mockDataSourceService2Impl = mock(SourceTwoImpl.class);
		FieldUtils.writeField(validationService, "dataSourceService1", mockDataSourceService1Impl, true);
		FieldUtils.writeField(validationService, "dataSourceService2", mockDataSourceService2Impl, true);
		doReturn(source1).when(mockDataSourceService1Impl).getData(anyInt());
		doReturn(source2).when(mockDataSourceService2Impl).getData(anyInt());
		MvcResult mvcResult = mock
		.perform(post("/account/validate")
		.contentType(MediaType.APPLICATION_JSON)
		.content(request))
		.andExpect(status().isOk())
		.andReturn();
		
		File responseFile = new File(classLoader.getResource("Source2Response.json").getFile());
		String response = new String(Files.readAllBytes(responseFile.toPath()));
		
		assertEquals(StringUtils.trimAllWhitespace(response), StringUtils.trimAllWhitespace(mvcResult.getResponse().getContentAsString()));
	}

	@Test
	void testValidationWithNoSource() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File requestFile = new File(classLoader.getResource("NoSourceRequest.json").getFile());
		String request = new String(Files.readAllBytes(requestFile.toPath()));
		SourceOneImpl mockDataSourceService1Impl = mock(SourceOneImpl.class);
		SourceTwoImpl mockDataSourceService2Impl = mock(SourceTwoImpl.class);
		FieldUtils.writeField(validationService, "dataSourceService1", mockDataSourceService1Impl, true);
		FieldUtils.writeField(validationService, "dataSourceService2", mockDataSourceService2Impl, true);
		doReturn(source1).when(mockDataSourceService1Impl).getData(anyInt());
		doReturn(true).when(mockDataSourceService1Impl).isValidSourceData(anyInt());
		doReturn(source2).when(mockDataSourceService2Impl).getData(anyInt());
		doReturn(true).when(mockDataSourceService2Impl).isValidSourceData(anyInt());
		MvcResult mvcResult = mock
		.perform(post("/account/validate")
		.contentType(MediaType.APPLICATION_JSON)
		.content(request))
		.andExpect(status().isOk())
		.andReturn();
		
		File responseFile = new File(classLoader.getResource("NoSourceResponse.json").getFile());
		String response = new String(Files.readAllBytes(responseFile.toPath()));
		
		assertEquals(StringUtils.trimAllWhitespace(response), StringUtils.trimAllWhitespace(mvcResult.getResponse().getContentAsString()));
	}
	
	
	@Test
	void testValidationWithInvalidSource() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File requestFile = new File(classLoader.getResource("InvalidSourceRequest.json").getFile());
		String request = new String(Files.readAllBytes(requestFile.toPath()));
		SourceOneImpl mockDataSourceService1Impl = mock(SourceOneImpl.class);
		SourceTwoImpl mockDataSourceService2Impl = mock(SourceTwoImpl.class);
		FieldUtils.writeField(validationService, "dataSourceService1", mockDataSourceService1Impl, true);
		FieldUtils.writeField(validationService, "dataSourceService2", mockDataSourceService2Impl, true);
		doReturn(source1).when(mockDataSourceService1Impl).getData(anyInt());
		doReturn(source2).when(mockDataSourceService2Impl).getData(anyInt());
		MvcResult mvcResult = mock
		.perform(post("/account/validate")
		.contentType(MediaType.APPLICATION_JSON)
		.content(request))
		.andExpect(status().isOk())
		.andReturn();
		
		File responseFile = new File(classLoader.getResource("InvalidSourceResponse.json").getFile());
		String response = new String(Files.readAllBytes(responseFile.toPath()));
		
		assertEquals(StringUtils.trimAllWhitespace(response), StringUtils.trimAllWhitespace(mvcResult.getResponse().getContentAsString()));
	}
}
