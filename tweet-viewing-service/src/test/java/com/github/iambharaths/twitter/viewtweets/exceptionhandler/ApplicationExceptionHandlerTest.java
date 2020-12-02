package com.github.iambharaths.twitter.viewtweets.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.iambharaths.twitter.viewtweets.exception.RecordNotFoundException;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

	@InjectMocks
	private ApplicationExceptionHandler exceptionHandler;
	
	@Test
	void testServerException() {
		ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(new Exception());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(ErrorMessage.SERVER_ERROR, response.getBody().getMessage());
	}
	
	@Test
	void testRecordNotFoundException() {
		ResponseEntity<ErrorResponse> response = exceptionHandler.handleRecordNotFoundException(new RecordNotFoundException("No tweets Found"));
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ErrorMessage.RECORD_NOT_FOUND, response.getBody().getMessage());
	}
	
	
}
