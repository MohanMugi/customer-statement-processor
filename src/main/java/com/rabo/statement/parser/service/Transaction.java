package com.rabo.statement.parser.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
public class Transaction {
	private Long reference;
	private String accountNumber;
	private String description;
	private Double startBalance;
	private Double mutation;
	private Double endBalance;

	public Transaction() {
	}

	public Transaction(Long reference, String accountNumber, String description, Double startBalance, Double mutation,
			Double endBalance) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}

	@XmlAttribute(name = "reference")
	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	@XmlElement(name = "accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "startBalance")
	public Double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}

	@XmlElement(name = "mutation")
	public Double getMutation() {
		return mutation;
	}

	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}

	@XmlElement(name = "endBalance")
	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public String toString() {
		return "Statement [reference=" + reference + ", accountNumber=" + accountNumber + ", description=" + description
				+ ", startBalance=" + startBalance + ", mutation=" + mutation + ", endBalance=" + endBalance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

}
