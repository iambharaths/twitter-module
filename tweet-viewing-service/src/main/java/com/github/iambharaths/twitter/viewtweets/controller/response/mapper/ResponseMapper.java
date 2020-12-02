package com.github.iambharaths.twitter.viewtweets.controller.response.mapper;

import org.mapstruct.Mapper;

import com.github.iambharaths.twitter.viewtweets.controller.response.RulesResponseDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.TweetDTO;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.Tweet;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

	TweetDTO mapTweet(Tweet tweet);
	RulesResponseDTO mapResponse(RulesResponse respone);
}
