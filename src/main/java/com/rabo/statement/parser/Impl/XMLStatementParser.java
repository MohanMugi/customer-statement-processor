package com.rabo.statement.parser.Impl;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.parser.service.Transaction;
import com.rabo.statement.parser.service.Transactions;
import com.rabo.statement.parser.service.XMLTransactions;
import com.rabo.statement.service.StatementParser;

@Component(value = "xmlStatementParser")
public class XMLStatementParser implements StatementParser {

	@Override
	public List<Transaction> parse(File file) throws StatementProcessorException {
		Transactions transactions;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(XMLTransactions.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			transactions = (XMLTransactions) unmarshaller.unmarshal(file);
		} catch (Exception exception) {
			throw new StatementProcessorException(exception);
		}
		return transactions.getStatements();
	}

}
