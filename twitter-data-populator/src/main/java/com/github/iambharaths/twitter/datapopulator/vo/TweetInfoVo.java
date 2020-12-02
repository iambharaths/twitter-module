package com.github.iambharaths.twitter.datapopulator.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetInfoVo {

	private TweetData data;
	private IncludeInfo includes;
	private List<TweetMatchingRules> matchingRules;

}
