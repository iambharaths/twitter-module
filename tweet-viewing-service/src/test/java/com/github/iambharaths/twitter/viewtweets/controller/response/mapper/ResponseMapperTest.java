package com.github.iambharaths.twitter.viewtweets.controller.response.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.iambharaths.twitter.viewtweets.controller.response.RulesResponseDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.TweetDTO;
import com.github.iambharaths.twitter.viewtweets.model.RulesData;
import com.github.iambharaths.twitter.viewtweets.model.RulesError;
import com.github.iambharaths.twitter.viewtweets.model.RulesMeta;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.RulesSummary;
import com.github.iambharaths.twitter.viewtweets.model.Tweet;

@ExtendWith(MockitoExtension.class)
 class ResponseMapperTest {

	private ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);
	
	@Test
	void testTweetMap() {
		TweetDTO tweetDTO = responseMapper.mapTweet(generateTweet());
		assertEquals(1L, tweetDTO.getId());
		assertEquals(1, tweetDTO.getLikeCount());
		assertEquals(2, tweetDTO.getRetweetCount());
		assertEquals(3, tweetDTO.getReplyCount());
		assertEquals(4, tweetDTO.getQuoteCount());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), tweetDTO.getCreatedAt());
		assertEquals("test text", tweetDTO.getText());
	}
	
	@Test
	void testTweetMapWhenNull() {
		TweetDTO tweetDTO = responseMapper.mapTweet(null);
		assertEquals(null, tweetDTO);
	}
	
	private Tweet generateTweet() {
		Tweet tweet = new Tweet();
		tweet.setId(1L);
		tweet.setLikeCount(1);
		tweet.setRetweetCount(2);
		tweet.setReplyCount(3);
		tweet.setQuoteCount(4);
		tweet.setCreatedAt(Instant.parse("2020-11-30T00:00:00Z"));
		tweet.setText("test text");
		tweet.setUsername("user");
		return tweet;
	}

	@Test
	void testRulesResponseMap() {
		RulesResponseDTO responseDTO = responseMapper.mapResponse(generateRulesResponse());
		assertEquals(1L,responseDTO.getData().get(0).getId());
		assertEquals("Test tag",responseDTO.getData().get(0).getTag());
		assertEquals("Test value",responseDTO.getData().get(0).getValue());
		assertEquals("Test",responseDTO.getErrors().get(0).getDetail());
		assertEquals("Test",responseDTO.getErrors().get(0).getTitle());
		assertEquals("Test",responseDTO.getErrors().get(0).getDetail());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), responseDTO.getMeta().getSent());
		assertEquals(1, responseDTO.getMeta().getSummary().getCreated());
		assertEquals(1, responseDTO.getMeta().getSummary().getNotDeleted());
	}
	
	@Test
	void testRulesResponseMapNull() {
		RulesResponseDTO responseDTO = responseMapper.mapResponse(null);
		assertEquals(null, responseDTO);
	}
	
	@Test
	void testRulesResponseMapWhenNull() {
		RulesResponseDTO responseDTO = responseMapper.mapResponse(new RulesResponse());
		assertEquals(null, responseDTO.getData());
		assertEquals(null, responseDTO.getErrors());
		assertEquals(null, responseDTO.getMeta());
	}

	private RulesResponse generateRulesResponse() {
		List<RulesData> datas = new ArrayList<>();
		List<RulesError> errors = new ArrayList<>();
		RulesMeta meta = new RulesMeta();
		RulesData data =  new RulesData();
		data.setId(1L);
		data.setTag("Test tag");
		data.setValue("Test value");
		datas.add(data);
		RulesError error = new RulesError();
		error.setId(1L);
		error.setDetail("Test");
		error.setTitle("Test");
		error.setValue("Test");
		errors.add(error);
		RulesSummary summary = new RulesSummary();
		summary.setCreated(1);
		summary.setNotDeleted(1);
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		meta.setSummary(summary);
		RulesResponse response = new RulesResponse();
		response.setData(datas);
		response.setErrors(errors);
		response.setMeta(meta);
		return response;
	}
}
