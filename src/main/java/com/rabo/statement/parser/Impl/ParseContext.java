package com.rabo.statement.parser.Impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.parser.service.Transaction;
import com.rabo.statement.service.StatementParser;


@Component
public class ParseContext {

	private StatementParser parser;

	public void setParser(StatementParser parser) {
		this.parser = parser;
	}

	public List<Transaction> parseFile(File file) throws StatementProcessorException {
		return parser.parse(file);
	}

}