package com.github.iambharaths.twitter.viewtweets.model;

import lombok.Data;

@Data
public class RulesSummary {

	private int deleted;
	private int notDeleted;
	private int created;
	private int notCreated;
	private int valid;
	private int invalid;
}
