package com.github.iambharaths.twitter.viewtweets.client.vo;

import java.time.Instant;

import lombok.Data;

@Data
public class RulesMetaVO {
	private Instant sent;
	private RulesSummaryVO summary;
}
