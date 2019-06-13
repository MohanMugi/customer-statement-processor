package com.rabo.statement.model;

import java.util.List;

import com.rabo.statement.constants.ResponseCode;
import com.rabo.statement.parser.service.Transaction;



public class StatmentServiceResponse implements ServiceResponse {

	private ResponseCode serviceResponse;
	private List<Transaction> transactions;

	@Override
	public ResponseCode getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(ResponseCode serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
