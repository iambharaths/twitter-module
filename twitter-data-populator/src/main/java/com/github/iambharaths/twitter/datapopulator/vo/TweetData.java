package com.github.iambharaths.twitter.datapopulator.vo;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetData {
	private Long id;
	private Long authorId;
	private Instant createdAt;
	private String text;
	private PublicMetric publicMetrics;

}
