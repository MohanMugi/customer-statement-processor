package com.rabo.statement.parser.service;

import java.util.List;

public interface Transactions {

	public List<Transaction> getStatements();

	public void setStatements(List<Transaction> transactions);
}
