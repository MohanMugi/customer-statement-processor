package com.rabo.statement.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.rabo.statement.api.StatementProcessorApi;
import com.rabo.statement.constants.ResponseCodeDescription;
import com.rabo.statement.constants.StatementsProcessorConstants;
import com.rabo.statement.model.StatementResponse;
import com.rabo.statement.model.StatmentServiceResponse;
import com.rabo.statement.model.Transaction;
import com.rabo.statement.service.StatementProcessorService;
import com.rabo.statement.service.Impl.FileStorageService;
import com.rabo.statement.uitil.ResponseUtil;

@RestController
public class StatementProcessorController implements StatementProcessorApi {

	public static final Logger LOG = LoggerFactory.getLogger(StatementProcessorController.class);

	@Autowired
	StatementProcessorService statementService;

	@Autowired
	FileStorageService fileStorageService;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<StatementResponse> process(@RequestParam(StatementsProcessorConstants.FILE) MultipartFile file) {

		StatementResponse response = new StatementResponse();
		Resource resource;
		StatmentServiceResponse serviceResponse;
		try {

			String fileName = fileStorageService.storeFile(file);
			resource = fileStorageService.loadFileAsResource(fileName);
			
			String extension = Files.getFileExtension(fileName);
			
			if ("csv".equals(extension)) {
				LOG.info(StatementsProcessorConstants.PROCESSING_CSV_FILE,fileName);
				serviceResponse = statementService.processCSVStatement(resource.getFile());
			} else if ("xml".equals(extension)) {
				LOG.info(StatementsProcessorConstants.PROCESSING_XML_FILE,fileName);
				serviceResponse = statementService.processXMLStatement(resource.getFile());
			} else{
				LOG.error(StatementsProcessorConstants.INVALID_FORMAT);
				return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForBadRequest(ResponseCodeDescription.VALIDATION_ERROR, response);
			}
			List<Transaction> transactions = new ArrayList<>();
			
			if (serviceResponse != null && serviceResponse.getTransactions() != null) {
				
				serviceResponse.getTransactions().forEach(transaction -> {

					Transaction responseTransaction = new Transaction();
					responseTransaction.setReference(transaction.getReference());
					responseTransaction.setDescription(transaction.getDescription());
					transactions.add(responseTransaction);
					LOG.debug(StatementsProcessorConstants.FAILURE_RESPONSE,responseTransaction);
				});
			
			}
			LOG.info(StatementsProcessorConstants.STATEMENT_RESPONSE);
			response.setTransaction(transactions);

		} catch (Exception exception) {
			LOG.error(StatementsProcessorConstants.EXCEPTION_IN_PROCESSING_FILE, exception);
			return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForException(ResponseCodeDescription.INTERNAL_SERVER_ERROR, response);
		}
		return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForOk(serviceResponse,response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<StatementResponse> processXML() {

		StatementResponse response = new StatementResponse();
		StatmentServiceResponse serviceResponse;
		try {
			Resource resource = new ClassPathResource(StatementsProcessorConstants.XML_FILE);
			LOG.info(StatementsProcessorConstants.PROCESSING_XML_FILE);
			serviceResponse = statementService.processXMLStatement(resource.getFile());
			List<Transaction> transactions = new ArrayList<>();
			if (serviceResponse.getTransactions() != null) {
				serviceResponse.getTransactions().forEach(transaction -> {

					Transaction responseTransaction = new Transaction();
					responseTransaction.setReference(transaction.getReference());
					responseTransaction.setDescription(transaction.getDescription());
 					transactions.add(responseTransaction);
					LOG.debug(StatementsProcessorConstants.FAILURE_RESPONSE,responseTransaction);
				});
			}
			LOG.info(StatementsProcessorConstants.STATEMENT_RESPONSE);
			response.setTransaction(transactions);

		} catch (Exception exception) {
			LOG.error(StatementsProcessorConstants.EXCEPTION_IN_PROCESSING_FILE, exception);
			return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForException(ResponseCodeDescription.INTERNAL_SERVER_ERROR, response);
		}
		return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForOk(serviceResponse,response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<StatementResponse> processCSV() {
		
		StatementResponse response = new StatementResponse();
		StatmentServiceResponse serviceResponse;
		try {
			Resource resource = new ClassPathResource(StatementsProcessorConstants.CSV_FILE);
			serviceResponse = statementService.processCSVStatement(resource.getFile());
			List<Transaction> transactions = new ArrayList<>();
			if (serviceResponse.getTransactions() != null) {
				serviceResponse.getTransactions().forEach(transaction -> {
					LOG.info(StatementsProcessorConstants.PROCESSING_CSV_FILE);
					Transaction responseTransaction = new Transaction();
					responseTransaction.setReference(transaction.getReference());
					responseTransaction.setDescription(transaction.getDescription());
					transactions.add(responseTransaction);
					LOG.debug(StatementsProcessorConstants.FAILURE_RESPONSE,responseTransaction);
				});
			}
			response.setTransaction(transactions);

		} catch (Exception exception) {
			LOG.error(StatementsProcessorConstants.EXCEPTION_IN_PROCESSING_FILE, exception);
			return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForException(ResponseCodeDescription.INTERNAL_SERVER_ERROR, response);
		}
		return (ResponseEntity<StatementResponse>) ResponseUtil.preperEntityForOk(serviceResponse,response);
	}

}
