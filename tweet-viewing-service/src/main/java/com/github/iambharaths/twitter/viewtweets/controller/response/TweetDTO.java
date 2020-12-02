package com.github.iambharaths.twitter.viewtweets.controller.response;

import java.time.Instant;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "TweetDTO", description = "All Details About One Tweet")
public class TweetDTO {
	@ApiModelProperty(notes = "Unique Id of Tweet")
	private Long id;
	@ApiModelProperty(notes = "Username of the user who tweeted")
	private String username;
	@ApiModelProperty(notes = "Tweet text")
	private String text;
	@ApiModelProperty(notes = "Time of creation of tweet in UTC")
	private Instant createdAt;
	@ApiModelProperty(notes = "List of rule Ids matched")
	private List<Long> matchingRuleIds;
	@ApiModelProperty(notes = "List of rule Tags matched")
	private List<String> matchingRuleTags;
	@ApiModelProperty(notes = "Name of user who tweeted")
	private String name;
	@ApiModelProperty(notes = "Number of Likes for tweet")
	private int likeCount;
	@ApiModelProperty(notes = "Number of replies for tweet")
	private int replyCount;
	@ApiModelProperty(notes = "Number of retweets for tweet")
	private int retweetCount;
	@ApiModelProperty(notes = "Number of qoutes for tweet")
	private int quoteCount;
}
