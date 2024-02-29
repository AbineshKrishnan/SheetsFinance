package com.bursa.sheetIntegration.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean status;

	private String message;

	private Integer statusCode;
	
	private transient Object retrievedResult;
	

}
