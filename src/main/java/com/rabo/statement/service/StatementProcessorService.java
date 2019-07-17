package com.rabo.statement.service;
import java.io.File;

import com.rabo.statement.model.StatmentServiceResponse;


public interface StatementProcessorService {

	public StatmentServiceResponse processCSVStatement(File file);

	public StatmentServiceResponse processXMLStatement(File file);

}
