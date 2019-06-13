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
public class XMLStatementParserTest {

	@InjectMocks
	private XMLStatementParser classUnderTest;
	
	private List<Transaction> expected =new ArrayList<>();
	
	@Before
	public void setUp() throws Exception{
		Transaction transaction=new Transaction();
		transaction.setReference(Long.valueOf("130498"));
		transaction.setAccountNumber("NL69ABNA0433647324");
		transaction.setDescription("Tickets for Peter Theuß");
		transaction.setEndBalance(8.12);
		transaction.setStartBalance(26.9);
		transaction.setMutation(-18.78);
		Transaction statement2=new Transaction();
		statement2.setReference(Long.valueOf("167875"));
		statement2.setAccountNumber("NL93ABNA0585619023");
		statement2.setDescription("Tickets from Erik de Vries");
		statement2.setEndBalance(6368.00);
		statement2.setStartBalance(5429.00);
		statement2.setMutation(939.00);

		
		expected.add(transaction);
		expected.add(statement2);
	}
	@Test
	public void testParseWithReocrds() throws Exception {
		List<Transaction> actual=classUnderTest.parse(new ClassPathResource("records.xml").getFile());
		assertEquals(expected,actual);
	
	}

	@Test(expected=StatementProcessorException.class)
	public void testParseForException() throws Exception {
		classUnderTest.parse(new ClassPathResource("invalidRecords.xml").getFile());
	
	}
	


}
