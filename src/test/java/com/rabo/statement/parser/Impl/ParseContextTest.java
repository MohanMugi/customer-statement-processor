package com.rabo.statement.parser.Impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.parser.service.Transaction;
import com.rabo.statement.service.StatementParser;



@RunWith(MockitoJUnitRunner.class)
public class ParseContextTest {

	@InjectMocks
	private ParseContext classUnderTest;
	
	@Mock
	private StatementParser parser;

	List<Transaction> transactions =new ArrayList<>();
	@Before
	public void setUp() throws Exception{
		Transaction transaction=new Transaction();
		transaction.setReference(Long.valueOf("194261"));
		transaction.setAccountNumber("NL91RABO0315273637");
		transaction.setDescription("description");
		transaction.setEndBalance(-20.23);
		transaction.setStartBalance(21.6);
		transaction.setMutation(-41.83);
		Transaction statement2=new Transaction();
		statement2.setReference(Long.valueOf("194261"));
		statement2.setAccountNumber("NL91RABO0315273637");
		statement2.setDescription("description");
		statement2.setEndBalance(-20.23);
		statement2.setStartBalance(21.6);
		statement2.setMutation(-41.83);

		
		transactions.add(transaction);
		transactions.add(statement2);
	}
	@Test
	public void testForSuccess() throws Exception {
		
		when(this.parser.parse(any())).thenReturn(transactions);
		List<Transaction> actual=classUnderTest.parseFile(new ClassPathResource("records.csv").getFile());
		assertEquals(transactions,actual);
	}
	@Test(expected=StatementProcessorException.class)
	public void testForException() throws Exception {
		
		when(this.parser.parse(any())).thenThrow(StatementProcessorException.class);
		
		List<Transaction> actual=classUnderTest.parseFile(new ClassPathResource("records.csv").getFile());
		assertEquals(transactions,actual);
	}

}
