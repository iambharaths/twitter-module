package com.github.iambharaths.twitter.viewtweets.exceptionhandler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	private ErrorMessage message;
	private List<String> descriptions;
}
