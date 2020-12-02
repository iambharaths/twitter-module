package com.github.iambharaths.twitter.viewtweets.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.viewtweets.client.TwitterClient;
import com.github.iambharaths.twitter.viewtweets.model.AddRulesData;
import com.github.iambharaths.twitter.viewtweets.model.RulesData;
import com.github.iambharaths.twitter.viewtweets.model.RulesMeta;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.RulesSummary;
import com.github.iambharaths.twitter.viewtweets.model.Tweet;
import com.github.iambharaths.twitter.viewtweets.model.TweetsData;
import com.github.iambharaths.twitter.viewtweets.repository.TweetRepository;
import com.github.iambharaths.twitter.viewtweets.service.impl.TweetServiceImpl;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

	@InjectMocks
	private TweetServiceImpl service;

	@Mock
	private TwitterClient client;
	@Mock
	private TweetRepository repository;
	@Mock
	private ObjectMapper jacksonObjectMapper;
	@Mock
	private SolrTemplate template;

	@Test
	void testFetchRules() {
		when(client.fetchMatchingRules()).thenReturn(generateMockRulesRespone());
		RulesResponse response = service.fetchRules();
		assertEquals(1L, response.getData().get(0).getId());
		assertEquals("Test Tag", response.getData().get(0).getTag());
		assertEquals("Test value", response.getData().get(0).getValue());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getMeta().getSent());

	}
	
	@Test
	void testDeleteRules() {
		when(client.deleteOrPostRules(generateMockDeleteRequest())).thenReturn(generateMockDeleteRulesResponse());
		RulesResponse response = service.deleteRules(Arrays.asList("1","2"));
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getMeta().getSent());
		assertEquals(2, response.getMeta().getSummary().getDeleted());

	}
	
	@Test
	void testAddRules() throws JsonProcessingException {
		when(jacksonObjectMapper.writeValueAsString(any(AddRulesData.class))).thenReturn(addRequest());
		when(client.deleteOrPostRules(addRequest())).thenReturn(generateMockAddRulesResponse());
		RulesResponse response = service.addRules(mockAddRules());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getMeta().getSent());
		assertEquals(1, response.getMeta().getSummary().getCreated());

	}
	
	@Test
	void testFetchTweets() {
		when(repository.fetchTweetsByIds(Arrays.asList(1L),Arrays.asList("Test Tag"), generatePageRequest())).thenReturn(generateMockTweets());
		when(client.fetchMatchingRules()).thenReturn(generateMockRulesRespone());
		TweetsData tweets = service.fetchTweetsByPage(0, 20);
		assertEquals(1L, tweets.getTweets().get(0).getId());
		assertEquals("test 1", tweets.getTweets().get(0).getName());
		
	}
	
	@Test
	void testFetchTweetsWhenIdsEmpty() {
		TweetsData tweets = service.fetchTweetsByPage(0, 20);
		assertEquals(null, tweets.getTweets());
	}
	
	private List<Tweet> generateMockTweets() {
		Tweet tweet1 = new Tweet();
		tweet1.setId(1L);
		tweet1.setName("test 1");
		tweet1.setText("text 1");
		
		Tweet tweet2 = new Tweet();
		tweet2.setId(2L);
		tweet2.setName("test 2");
		tweet2.setText("text 2");
		
		return Arrays.asList(tweet1,tweet2);
	}

	private Pageable generatePageRequest() {
		return PageRequest.of(0, 20, Sort.by("createdAt").descending());
	}

	private RulesResponse generateMockAddRulesResponse() {
		RulesResponse deleteRulesResponse = new RulesResponse();
		RulesMeta meta = new RulesMeta();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		RulesSummary summary = new RulesSummary();
		summary.setCreated(1);
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

	private String addRequest() {
		return "{\"add\":[{\"value\":\"Test value\",\"tag\":\"Test tag\"}]}";
	}
	
	private RulesResponse generateMockDeleteRulesResponse() {
		RulesResponse deleteRulesResponse = new RulesResponse();
		RulesMeta meta = new RulesMeta();
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		RulesSummary summary = new RulesSummary();
		summary.setDeleted(2);
		meta.setSummary(summary);
		deleteRulesResponse.setMeta(meta);
		return deleteRulesResponse;
	}

	private String generateMockDeleteRequest() {
		return "{\"delete\":{\"ids\":[1, 2]}}";
	}

	private RulesResponse generateMockRulesRespone() {
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
