package com.rabo.statement.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.rabo.statement.constants.ResponseCodeDescription;
import com.rabo.statement.exception.StatementProcessorException;
import com.rabo.statement.model.StatmentServiceResponse;
import com.rabo.statement.parser.Impl.CSVStatementParser;
import com.rabo.statement.parser.Impl.ParseContext;
import com.rabo.statement.parser.Impl.XMLStatementParser;
import com.rabo.statement.parser.service.Transaction;
import com.rabo.statement.service.StatementProcessorService;


@RunWith(MockitoJUnitRunner.class)
public class StatementProcessServiceImplTest {

	@InjectMocks
	private StatementProcessorService classUnderTest;

	@Mock
	private ParseContext mockParseContext;
	
	@Mock
	CSVStatementParser mockCSVStatementParser;
	
	@Mock
	XMLStatementParser mockXMLStatementParser;
	
	List<Transaction> transactions =new ArrayList<>();
	List<Transaction> uniqueStatements =new ArrayList<>();
	
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
		
		Transaction statement3=new Transaction();
		statement3.setReference(Long.valueOf("194261"));
		statement3.setAccountNumber("NL91RABO0315273637");
		statement3.setDescription("description");
		statement3.setEndBalance(-20.23);
		statement3.setStartBalance(21.6);
		statement3.setMutation(-41.83);
		Transaction statement4=new Transaction();
		statement4.setReference(Long.valueOf("194263"));
		statement4.setAccountNumber("NL91RABO0315273637");
		statement4.setDescription("description");
		statement4.setEndBalance(-20.23);
		statement4.setStartBalance(21.6);
		statement4.setMutation(-41.83);
		uniqueStatements.add(statement3);
		uniqueStatements.add(statement4);
		
	}
	
	@Test
	public void testProcessCSVStatementForSuccess() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setTransactions(transactions);
		expected.setServiceResponse(ResponseCodeDescription.SUCCESS);
		
		when(this.mockParseContext.parseFile(any())).thenReturn(transactions);
		
		StatmentServiceResponse actual = classUnderTest.processCSVStatement(new ClassPathResource("records.csv").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	@Test
	public void testProcessCSVStatementForNoData() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setTransactions(new ArrayList<Transaction>());
		expected.setServiceResponse(ResponseCodeDescription.NO_DATE_FOUND);
		
		when(this.mockParseContext.parseFile(any())).thenReturn(uniqueStatements);
		
		StatmentServiceResponse actual = classUnderTest.processCSVStatement(new ClassPathResource("records.csv").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void testProcessCSVStatementForError() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setServiceResponse(ResponseCodeDescription.ERROR);
		
		when(this.mockParseContext.parseFile(any())).thenThrow(StatementProcessorException.class);
		
		StatmentServiceResponse actual = classUnderTest.processCSVStatement(new ClassPathResource("records.csv").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void testProcessXMLStatementForSuccess() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setTransactions(transactions);
		expected.setServiceResponse(ResponseCodeDescription.SUCCESS);
		
		when(this.mockParseContext.parseFile(any())).thenReturn(transactions);
		
		StatmentServiceResponse actual = classUnderTest.processXMLStatement(new ClassPathResource("records.xml").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void testProcessXMLStatementForSuccessNoDataFound() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setTransactions(new ArrayList<Transaction>());
		expected.setServiceResponse(ResponseCodeDescription.NO_DATE_FOUND);
		
		when(this.mockParseContext.parseFile(any())).thenReturn(uniqueStatements);
		
		StatmentServiceResponse actual = classUnderTest.processXMLStatement(new ClassPathResource("records.xml").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void testProcessXMLStatementForError() throws Exception {
		
		StatmentServiceResponse expected = new StatmentServiceResponse();
		expected.setServiceResponse(ResponseCodeDescription.ERROR);
		
		when(this.mockParseContext.parseFile(any())).thenThrow(StatementProcessorException.class);
		
		StatmentServiceResponse actual = classUnderTest.processXMLStatement(new ClassPathResource("records.xml").getFile());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}

}
