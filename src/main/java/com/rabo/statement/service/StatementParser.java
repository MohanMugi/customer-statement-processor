package com.rabo.statement.service;

import java.io.File;
import java.util.List;

import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.parser.service.Transaction;



/**
 * Statement parser interface which need to be implemented by file parser
 * 
 *
 */
public interface StatementParser {

	public List<Transaction> parse(File file) throws StatementProcessorException;

}
