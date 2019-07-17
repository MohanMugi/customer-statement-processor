package com.rabo.statement.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("Customer Statement Response")
public class StatementResponse implements Response {

	@JsonProperty(value = "status")
	private StatementProcessStatus status = new StatementProcessStatus();

	@JsonProperty(value = "failure_records")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<Transaction> transaction = new ArrayList<>();

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	@Override
	public StatementProcessStatus getStatus() {
		return status;
	}

	public void setStatus(StatementProcessStatus status) {
		this.status = status;

	}

}
