package com.github.iambharaths.twitter.viewtweets.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.iambharaths.twitter.viewtweets.exception.RecordNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
	
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception){
		log.error("Record Not Found", exception);
		List<String> descriptions = new ArrayList<>();
		descriptions.add(exception.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse(ErrorMessage.RECORD_NOT_FOUND, descriptions);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception){
		log.error("Server Exception", exception);
		List<String> descriptions = new ArrayList<>();
		descriptions.add(exception.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse(ErrorMessage.SERVER_ERROR, descriptions);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
