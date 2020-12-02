package com.github.iambharaths.twitter.viewtweets.client.vo;

import lombok.Data;

@Data
public class RulesSummaryVO {

	private int deleted;
	private int notDeleted;
	private int created;
	private int notCreated;
	private int valid;
	private int invalid;
}
