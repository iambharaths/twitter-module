package com.github.iambharaths.twitter.viewtweets.exceptionhandler;

public enum ErrorMessage {
	SERVER_ERROR("Server Error"),RECORD_NOT_FOUND("No Records Found");
	
	private String message;

	private ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
