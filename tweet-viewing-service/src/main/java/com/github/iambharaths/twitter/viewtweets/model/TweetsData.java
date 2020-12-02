package com.github.iambharaths.twitter.viewtweets.model;

import java.util.List;

import lombok.Data;

@Data
public class TweetsData {

	private List<Tweet> tweets;
	private Long noOfRecords;
}
