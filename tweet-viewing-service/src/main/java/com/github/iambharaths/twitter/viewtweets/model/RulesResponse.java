package com.github.iambharaths.twitter.viewtweets.model;

import java.util.List;

import lombok.Data;

@Data
public class RulesResponse {

	private List<RulesData> data;
	private RulesMeta meta;
	private List<RulesError> errors;
}
