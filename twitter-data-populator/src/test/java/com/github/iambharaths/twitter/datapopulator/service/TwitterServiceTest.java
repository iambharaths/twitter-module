package com.github.iambharaths.twitter.datapopulator.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.datapopulator.client.TwitterClient;
import com.github.iambharaths.twitter.datapopulator.model.Tweet;
import com.github.iambharaths.twitter.datapopulator.repository.TwitterCrudRepository;
import com.github.iambharaths.twitter.datapopulator.service.impl.TwitterServiceImpl;
import com.github.iambharaths.twitter.datapopulator.vo.IncludeInfo;
import com.github.iambharaths.twitter.datapopulator.vo.TweetData;
import com.github.iambharaths.twitter.datapopulator.vo.TweetInfoVo;
import com.github.iambharaths.twitter.datapopulator.vo.TwitterRequest;
import com.github.iambharaths.twitter.datapopulator.vo.UserInfo;
import com.github.iambharaths.twitter.datapopulator.vo.mapper.TweetDataMapper;

@ExtendWith(MockitoExtension.class)
class TwitterServiceTest {

	@InjectMocks
	private TwitterServiceImpl service;

	@Mock
	private ObjectMapper jacksonObjectMapper;
	@Mock
	private TwitterCrudRepository repository;
	@Mock
	private TweetDataMapper tweetDataMapper;
	@Mock
	private TwitterClient twitterClient;

	@Test
	void testaddRulesToTwitter() throws URISyntaxException, IOException {
		when(jacksonObjectMapper.writeValueAsString(any(TwitterRequest.class))).thenReturn(prepareRequest());
		service.addRulesToTwitter("sampleAccount,sampleAccount2", "sampleTag,sampleTag2");
		verify(twitterClient, times(1)).addRules(prepareRequest());

	}

	@Test
	void testaddRulesToTwitterWhenNull() throws URISyntaxException, IOException {
		service.addRulesToTwitter(null, null);
		verify(twitterClient, times(0)).addRules(prepareRequest());

	}

	@Test
	void testFetchTweets() throws URISyntaxException, IOException {
		when(twitterClient.fetchTweetsFromTwitter()).thenReturn(generateMockTweetData());
		when(jacksonObjectMapper.readValue(anyString(), eq(TweetInfoVo.class))).thenReturn(generateTweetInfoVo());
		when(tweetDataMapper.map(any(TweetInfoVo.class))).thenReturn(generateTweetData());
		service.fetchDataFromTwitter();
		verify(twitterClient, times(1)).fetchTweetsFromTwitter();
	}

	private Tweet generateTweetData() {
		Tweet tweet = new Tweet();
		tweet.setId(1L);
		tweet.setName("test user");
		tweet.setText("test");
		tweet.setUsername("testUser");
		return tweet;
	}

	private TweetInfoVo generateTweetInfoVo() {
		TweetInfoVo vo = new TweetInfoVo();
		TweetData data = new TweetData();
		data.setAuthorId(1L);
		data.setId(1L);
		data.setText("test");
		vo.setData(data);
		IncludeInfo info = new IncludeInfo();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(1L);
		userInfo.setName("test user");
		userInfo.setUsername("testUser");
		List<UserInfo> user = new ArrayList<>();
		user.add(userInfo);
		info.setUsers(user);
		vo.setIncludes(info);
		vo.setIncludes(info);
		return vo;
	}

	private InputStream generateMockTweetData() throws FileNotFoundException {
		return new FileInputStream("src/test/resources/testFile.txt");
	}

	private String prepareRequest() {
		return "{\"add\":[{\"value\":\"from:sampleAccount1\"}, {\"value\":\"from:sampleAccount2\"}, {\"value\":\"sample Tag1\", \"tag\":\"sampleTag1\"}, {\"value\":\"sample Tag2\", \"tag\":\"sampleTag2\"}]}";
	}
}
