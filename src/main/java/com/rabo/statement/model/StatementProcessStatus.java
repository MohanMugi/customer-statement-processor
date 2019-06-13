package com.rabo.statement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Statement Process Status")
public class StatementProcessStatus implements Status {

	@ApiModelProperty(allowableValues = "1001,1002,1003")
	@JsonProperty(value = "status_code")
	private String statusCode;

	@ApiModelProperty(allowableValues = "customer records retrieved, no failure customer record found,error in reteriving customer record")
	@JsonProperty(value = "status_desc")
	private String statusDescription;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}
