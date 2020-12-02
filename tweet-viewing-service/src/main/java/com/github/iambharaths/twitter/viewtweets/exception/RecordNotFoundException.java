package com.github.iambharaths.twitter.viewtweets.exception;

public class RecordNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 555037505249693948L;
	
	public RecordNotFoundException(String exception) {
		super(exception);
	}
}
