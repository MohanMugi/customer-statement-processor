package com.rabo.statement.parser.Impl;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.parser.service.Transaction;


@RunWith(MockitoJUnitRunner.class)
public class CSVStatementParserTest {

	@InjectMocks
	private CSVStatementParser classUnderTest;
	
	private List<Transaction> expected =new ArrayList<>();
	
	@Before
	public void setUp() throws Exception{
		Transaction transaction=new Transaction();
		transaction.setReference(Long.valueOf("194261"));
		transaction.setAccountNumber("NL91RABO0315273637");
		transaction.setDescription("Clothes from Jan Bakker");
		transaction.setEndBalance(21.6);
		transaction.setStartBalance(-20.23);
		transaction.setMutation(-41.83);
		Transaction statement2=new Transaction();
		statement2.setReference(Long.valueOf("112806"));
		statement2.setAccountNumber("NL27SNSB0917829871");
		statement2.setDescription("Clothes for Willem Dekker");
		statement2.setEndBalance(106.8);
		statement2.setStartBalance(91.23);
		statement2.setMutation(+15.57);

		
		expected.add(transaction);
		expected.add(statement2);
	}
	@Test
	public void testParseWithReocrds() throws Exception {
		List<Transaction> actual=classUnderTest.parse(new ClassPathResource("records.csv").getFile());
		assertEquals(expected,actual);
	
	}

	@Test(expected=StatementProcessorException.class)
	public void testParseForException() throws Exception {
		classUnderTest.parse(new ClassPathResource("invaildRecords.csv").getFile());
	
	}
	@Test(expected=Exception.class)
	public void testParseForIOException() throws Exception {
		classUnderTest.parse(new ClassPathResource(null).getFile());
	
	}


}
