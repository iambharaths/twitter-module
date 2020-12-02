package com.github.iambharaths.twitter.viewtweets.model;

import java.util.List;

import lombok.Data;
@Data
public class RulesRequest {

	private List<String> accounts;
	private List<String> hashTags;
}
