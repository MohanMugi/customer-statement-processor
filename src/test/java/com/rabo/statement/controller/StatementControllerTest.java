package com.rabo.statement.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.statement.constants.ResponseCodeDescription;
import com.rabo.statement.model.StatmentServiceResponse;
import com.rabo.statement.parser.service.Transaction;
import com.rabo.statement.service.StatementProcessorService;
import com.rabo.statement.service.Impl.FileStorageService;
import com.rabo.statement.model.StatementResponse;


@RunWith(SpringRunner.class)
@WebMvcTest(value = StatementProcessorController.class, secure = false)
public class StatementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StatementProcessorService statementService;
	
	@MockBean
	FileStorageService fileStorageService;

	List<Transaction> transactions = new ArrayList<>();

	
	@Before
	public void setUp() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setReference(Long.valueOf("165102"));
		transaction.setAccountNumber("NL91RABO0315273637");
		transaction.setDescription("Tickets from Erik de Vries");
		transaction.setEndBalance(-20.23);
		transaction.setStartBalance(21.6);
		transaction.setMutation(-41.83);
		Transaction transaction2 = new Transaction();
		transaction2.setReference(Long.valueOf("165102"));
		transaction2.setAccountNumber("NL91RABO0315273637");
		transaction2.setDescription("Tickets for Rik Theu");
		transaction2.setEndBalance(-20.23);
		transaction2.setStartBalance(21.6);
		transaction2.setMutation(-41.83);
		transactions.add(transaction);
		transactions.add(transaction2);
	}
	
	@Test
	public void testProcessForCSVFileSuccess() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setTransactions(transactions);
		response.setServiceResponse(ResponseCodeDescription.SUCCESS);
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "records.csv",
                "multipart/form-data", "Spring Framework".getBytes());
		Resource resource= new ClassPathResource("records.csv");
		when(this.fileStorageService.storeFile(any())).thenReturn("records.csv");
		
		when(this.fileStorageService.loadFileAsResource(any())).thenReturn(resource);
		
		when(this.statementService.processCSVStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/SuccessResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(multipart("/api/rest/v1/statement/process").file(multipartFile)).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}
	
	@Test
	public void testProcessForXMLFileSuccess() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setTransactions(transactions);
		response.setServiceResponse(ResponseCodeDescription.SUCCESS);
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "records.xml",
                "multipart/form-data", "Statements process".getBytes());
		Resource resource= new ClassPathResource("records.csv");
		when(this.fileStorageService.storeFile(any())).thenReturn("records.xml");
		
		when(this.fileStorageService.loadFileAsResource(any())).thenReturn(resource);
		
		when(this.statementService.processXMLStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/SuccessResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(multipart("/api/rest/v1/statement/process").file(multipartFile)).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}
	@Test
	public void testProcessForInValidFile() throws Exception {

		MockMultipartFile multipartFile  = new MockMultipartFile("file", "records.test",
                "multipart/form-data", "Spring Framework".getBytes());
		Resource resource= new ClassPathResource("records.csv");
		when(this.fileStorageService.storeFile(any())).thenReturn("records.test");
		
		when(this.fileStorageService.loadFileAsResource(any())).thenReturn(resource);
		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/InvalidFileResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(multipart("/api/rest/v1/statement/process").file(multipartFile)).andExpect(status().is4xxClientError())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessForServerError() throws Exception {
		
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "records.test",
                "multipart/form-data", "Spring Framework".getBytes());
		
		when(this.fileStorageService.storeFile(any())).thenThrow(RuntimeException.class);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/ServerErrorResponse.json").getFile(),
				StatementResponse.class);
	
		MvcResult result = this.mockMvc.perform(multipart("/api/rest/v1/statement/process").file(multipartFile)).andExpect(status().is5xxServerError())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}
	@Test
	public void testProcessCSVForSuccess() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setTransactions(transactions);
		response.setServiceResponse(ResponseCodeDescription.SUCCESS);

		when(this.statementService.processCSVStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/SuccessResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/csv")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessCSVForError() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setServiceResponse(ResponseCodeDescription.ERROR);

		when(this.statementService.processCSVStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/ErrorResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/csv")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessCSVForNoDataFound() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setServiceResponse(ResponseCodeDescription.NO_DATE_FOUND);
		response.setTransactions(new ArrayList<>());
		when(this.statementService.processCSVStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/NoDataFoundResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/csv")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessCSVForServerError() throws Exception {

		when(this.statementService.processCSVStatement(any())).thenThrow(RuntimeException.class);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/ServerErrorResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/csv"))
				.andExpect(status().is5xxServerError()).andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	

	@Test
	public void testProcessXMLForSuccess() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setTransactions(transactions);
		response.setServiceResponse(ResponseCodeDescription.SUCCESS);

		when(this.statementService.processXMLStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/SuccessResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/xml")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessXMLError() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setServiceResponse(ResponseCodeDescription.ERROR);

		when(this.statementService.processXMLStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/ErrorResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/xml")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessXMLForNoDataFound() throws Exception {

		StatmentServiceResponse response = new StatmentServiceResponse();
		response.setServiceResponse(ResponseCodeDescription.NO_DATE_FOUND);
		response.setTransactions(new ArrayList<>());
		when(this.statementService.processXMLStatement(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/NoDataFoundResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/xml")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void testProcessXMLForServerError() throws Exception {

		when(this.statementService.processXMLStatement(any())).thenThrow(RuntimeException.class);

		ObjectMapper mapper = new ObjectMapper();

		StatementResponse expected = mapper.readValue(
				new ClassPathResource("com/rabo/statement/api/controllers/v1/ServerErrorResponse.json").getFile(),
				StatementResponse.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/v1/statement/process/xml"))
				.andExpect(status().is5xxServerError()).andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}
}
