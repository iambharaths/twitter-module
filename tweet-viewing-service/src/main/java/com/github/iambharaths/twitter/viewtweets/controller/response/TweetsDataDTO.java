package com.github.iambharaths.twitter.viewtweets.controller.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "TweetsDataDTO", description = "All Details About Rules Summary When Adding/Deleting rules")
public class TweetsDataDTO {
	@ApiModelProperty(notes = "List of TweetDTOs")
	private List<TweetDTO> tweets;
	@ApiModelProperty(notes = "Number of Records saved in collection with matching rule Ids")
	private Long noOfRecords;
}
