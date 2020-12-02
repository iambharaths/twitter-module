package com.github.iambharaths.twitter.datapopulator.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicMetric {

	private int likeCount;
	private int replyCount;
	private int retweetCount;
	private int quoteCount;
}
