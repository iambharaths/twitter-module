package com.github.iambharaths.twitter.datapopulator.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.datapopulator.client.TwitterClient;
import com.github.iambharaths.twitter.datapopulator.model.Tweet;
import com.github.iambharaths.twitter.datapopulator.repository.TwitterCrudRepository;
import com.github.iambharaths.twitter.datapopulator.service.TwitterService;
import com.github.iambharaths.twitter.datapopulator.vo.TweetInfoVo;
import com.github.iambharaths.twitter.datapopulator.vo.TwitterRequest;
import com.github.iambharaths.twitter.datapopulator.vo.TwitterRequestEntity;
import com.github.iambharaths.twitter.datapopulator.vo.mapper.TweetDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TwitterServiceImpl implements TwitterService{

	@Value("${twitter.bearerToken}")
	private String bearerToken;

	@Value("${twitter.url}")
	private String twitterURL;
	
	private static final int THRESHOLD_SIZE = 5;
	private static final int THRESHOLD_TIME_INTERVAL = 30;

	private ObjectMapper jacksonObjectMapper;
	private TwitterCrudRepository repository;
	private TweetDataMapper tweetDataMapper;
	private TwitterClient twitterClient;

	@Autowired
	public TwitterServiceImpl(ObjectMapper jacksonObjectMapper, TweetDataMapper tweetDataMapper,
			TwitterCrudRepository repository, TwitterClient twitterClient) {
		this.repository = repository;
		this.jacksonObjectMapper = jacksonObjectMapper;
		this.tweetDataMapper = tweetDataMapper;
		this.twitterClient = twitterClient;
	}
	
	@Override
	public void addRulesToTwitter(String username, String tag) {
		String request = processRequestJson(username, tag);
		if (StringUtils.isNotEmpty(request)) {
			try {
				twitterClient.addRules(request);
			} catch (URISyntaxException | IOException e) {
				log.error(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public void fetchDataFromTwitter() {
		try {
			List<Tweet> fetchedTweets = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(twitterClient.fetchTweetsFromTwitter()));
			String line = reader.readLine();
			Instant startTime = Instant.now();
			while (null != line) {
				log.info(line);
				Tweet tweet = mapToVo(line);
				if (StringUtils.isNotEmpty(tweet.getText())) {
					fetchedTweets.add(tweet);
				}
				if (!fetchedTweets.isEmpty() && (fetchedTweets.size() > THRESHOLD_SIZE
						|| (Instant.now().getEpochSecond() - startTime.getEpochSecond() > THRESHOLD_TIME_INTERVAL))) {
					repository.saveAll(fetchedTweets);
					fetchedTweets.clear();
					startTime = Instant.now();
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (URISyntaxException | IOException e) {
			log.error(e.getLocalizedMessage());

		}

	}

	private Tweet mapToVo(String line) {
		Tweet tweet = new Tweet();
		TweetInfoVo data = new TweetInfoVo();
		try {
			if (!line.isEmpty()) {
				data = jacksonObjectMapper.readValue(line, TweetInfoVo.class);
			}
		} catch (JsonProcessingException e) {
			log.error(e.getLocalizedMessage());
		}
		if (ObjectUtils.isNotEmpty(data.getData())) {
			tweet = tweetDataMapper.map(data);
			log.info("Fetched Tweet::: {}", tweet);
		}
		return tweet;
	}

	private String processRequestJson(String usernames, String tags) {
		TwitterRequest request = new TwitterRequest();
		List<TwitterRequestEntity> requestEntities = new ArrayList<>();
		String requestJson = "";
		if (null != usernames) {
			List<String> users = Stream.of(usernames.split(",")).collect(Collectors.toList());
			users.forEach(user -> {
				TwitterRequestEntity requestEntityForUser = new TwitterRequestEntity();
				requestEntityForUser.setValue(String.format("from:%s -is:retweet", user));
				requestEntityForUser.setTag(user);
				requestEntities.add(requestEntityForUser);
			});
		}

		if (null != tags) {
			List<String> tagsList = Stream.of(tags.split(",")).collect(Collectors.toList());
			tagsList.forEach(tag -> {
				TwitterRequestEntity requestEntityForTag = new TwitterRequestEntity();
				requestEntityForTag.setValue(prepareTagValue(tag));
				requestEntityForTag.setTag(tag);
				requestEntities.add(requestEntityForTag);
			});
		}

		if (!requestEntities.isEmpty()) {
			request.setAdd(requestEntities);
			try {
				requestJson = jacksonObjectMapper.writeValueAsString(request);
			} catch (JsonProcessingException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		return requestJson;
	}

	private String prepareTagValue(String tag) {
		return String.format("#%s -is:retweet", tag);
	}

}
