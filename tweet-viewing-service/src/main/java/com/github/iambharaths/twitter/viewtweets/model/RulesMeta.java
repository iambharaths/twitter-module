package com.github.iambharaths.twitter.viewtweets.model;

import java.time.Instant;

import lombok.Data;

@Data
public class RulesMeta {
	private Instant sent;
	private RulesSummary summary;
}
