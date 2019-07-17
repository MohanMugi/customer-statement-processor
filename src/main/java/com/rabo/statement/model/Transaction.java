package com.rabo.statement.model;

public class Transaction {

	private Long reference;
	private String description;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [reference=" + reference + ", description=" + description + "]";
	}

	

}