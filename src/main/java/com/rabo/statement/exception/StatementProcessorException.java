package com.rabo.statement.exception;
public class StatementProcessorException extends Exception {


	private static final long serialVersionUID = 5756238830272171170L;

	public StatementProcessorException(Throwable excption) {
		super(excption);
	}
	
	public StatementProcessorException(String message) {
		super(message);
	}

	public StatementProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

}