package com.github.iambharaths.twitter.viewtweets.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.iambharaths.twitter.viewtweets.controller.request.RulesRequestDTO;
import com.github.iambharaths.twitter.viewtweets.controller.request.mapper.RequestMapper;
import com.github.iambharaths.twitter.viewtweets.controller.response.RulesDataDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.RulesMetaDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.RulesResponseDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.RulesSummaryDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.TweetDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.TweetsDataDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.mapper.ResponseMapper;
import com.github.iambharaths.twitter.viewtweets.exception.RecordNotFoundException;
import com.github.iambharaths.twitter.viewtweets.model.RulesData;
import com.github.iambharaths.twitter.viewtweets.model.RulesMeta;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.RulesSummary;
import com.github.iambharaths.twitter.viewtweets.model.Tweet;
import com.github.iambharaths.twitter.viewtweets.model.TweetsData;
import com.github.iambharaths.twitter.viewtweets.service.TweetService;

@ExtendWith(MockitoExtension.class)
class TweetControllerTest {

	@InjectMocks
	private TweetController controller;

	@Mock
	private TweetService service;

	@Mock
	private RequestMapper requestMapper;

	@Mock
	private ResponseMapper responseMapper;

	@Test
	void testFetchRules() {
		when(service.fetchRules()).thenReturn(generateMockRules());
		when(responseMapper.mapResponse(generateMockRules())).thenReturn(generateMockRulesPO());
		ResponseEntity<RulesResponseDTO> response = controller.fetchRules();
		assertEquals(1L, response.getBody().getData().get(0).getId());
		assertEquals("Test Tag", response.getBody().getData().get(0).getTag());
		assertEquals("Test value", response.getBody().getData().get(0).getValue());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getBody().getMeta().getSent());
	}

	@Test
	void testPostRules() {
		when(requestMapper.map(mockAddRulesPO())).thenReturn(mockAddRules());
		ResponseEntity<RulesResponseDTO> addRulesResponse = controller.addRules(mockAddRulesPO());
		verify(service).addRules(mockAddRules());
		assertEquals(HttpStatus.CREATED, addRulesResponse.getStatusCode());
	}

	@Test
	void testDeleteRules() {
		when(service.deleteRules(Arrays.asList("1", "2"))).thenReturn(generateMockDeleteRules());
		when(responseMapper.mapResponse(generateMockDeleteRules())).thenReturn(generateMockDeleteRulesPO());
		ResponseEntity<RulesResponseDTO> addRulesResponse = controller.deleteRules(Arrays.asList("1", "2"));
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), addRulesResponse.getBody().getMeta().getSent());
		assertEquals(2, addRulesResponse.getBody().getMeta().getSummary().getDeleted());

	}

	@Test
	void testFetchTweets() {
		when(service.fetchTweetsByPage(0, 20)).thenReturn(generateMockTweets());
		when(responseMapper.mapTweet(any(Tweet.class))).thenReturn(MockTweetsPO());
		ResponseEntity<TweetsDataDTO> response = controller.fetchTweets(0, 20);
		assertEquals(1, response.getBody().getTweets().size());
		assertEquals(1L, response.getBody().getNoOfRecords());
		assertEquals(1L, response.getBody().getTweets().get(0).getId());
	}
	
	@Test
	void testFetchTweetsWhenEmpty() {
		when(service.fetchTweetsByPage(0, 20)).thenReturn(new TweetsData());
		assertThrows(RecordNotFoundException.class, 
				() -> controller.fetchTweets(0, 20));
	}

	private TweetDTO MockTweetsPO() {
		TweetDTO tweet = new TweetDTO();
		tweet.setId(1L);
		tweet.setName("Test Name");
		tweet.setText("Test Text");
		return tweet;
	}

	private TweetsData generateMockTweets() {
		Tweet tweet = new Tweet();
		tweet.setId(1L);
		tweet.setName("Test Name");
		tweet.setText("Test Text");
		TweetsData data = new TweetsData();
		data.setTweets(Arrays.asList(tweet));
		data.setNoOfRecords(1L);
		return data;
	}

	private RulesResponseDTO generateMockDeleteRulesPO() {
		RulesResponseDTO deleteRulesResponse = new RulesResponseDTO();
		RulesMetaDTO meta = new RulesMetaDTO();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		RulesSummaryDTO summary = new RulesSummaryDTO();
		summary.setDeleted(2);
		meta.setSummary(summary);
		deleteRulesResponse.setMeta(meta);
		return deleteRulesResponse;
	}

	private RulesResponse generateMockDeleteRules() {
		RulesResponse deleteRulesResponse = new RulesResponse();
		RulesMeta meta = new RulesMeta();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		RulesSummary summary = new RulesSummary();
		summary.setDeleted(2);
		meta.setSummary(summary);
		deleteRulesResponse.setMeta(meta);
		return deleteRulesResponse;
	}

	private RulesRequest mockAddRules() {
		RulesRequest addRules = new RulesRequest();
		addRules.setAccounts(Arrays.asList("account1","account2"));
		addRules.setHashTags(Arrays.asList("hashTag1","hashTag2"));
		return addRules;
		}

	private RulesRequestDTO mockAddRulesPO() {
		RulesRequestDTO addRules = new RulesRequestDTO();
		addRules.setAccounts(Arrays.asList("account1","account2"));
		addRules.setHashTags(Arrays.asList("hashTag1","hashTag2"));
		return addRules;
	}

	private RulesResponseDTO generateMockRulesPO() {
		RulesResponseDTO fetchRulesResponse = new RulesResponseDTO();
		RulesDataDTO rule = new RulesDataDTO();
		rule.setId(1L);
		rule.setTag("Test Tag");
		rule.setValue("Test value");
		RulesMetaDTO meta = new RulesMetaDTO();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		List<RulesDataDTO> data = Arrays.asList(rule);
		fetchRulesResponse.setData(data);
		fetchRulesResponse.setMeta(meta);
		return fetchRulesResponse;
	}

	private RulesResponse generateMockRules() {
		RulesResponse fetchRulesResponse = new RulesResponse();
		RulesData rule = new RulesData();
		rule.setId(1L);
		rule.setTag("Test Tag");
		rule.setValue("Test value");
		RulesMeta meta = new RulesMeta();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		List<RulesData> data = Arrays.asList(rule);
		fetchRulesResponse.setData(data);
		fetchRulesResponse.setMeta(meta);
		return fetchRulesResponse;
	}

}
