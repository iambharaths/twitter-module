package com.github.iambharaths.twitter.viewtweets.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.viewtweets.client.TwitterClient;
import com.github.iambharaths.twitter.viewtweets.model.AddRulesData;
import com.github.iambharaths.twitter.viewtweets.model.AddRulesInfo;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.Tweet;
import com.github.iambharaths.twitter.viewtweets.model.TweetsData;
import com.github.iambharaths.twitter.viewtweets.repository.TweetRepository;
import com.github.iambharaths.twitter.viewtweets.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetServiceImpl implements TweetService {

	private TwitterClient client;
	private TweetRepository repository;
	private ObjectMapper jacksonObjectMapper;
	private SolrTemplate template;

	@Autowired
	public TweetServiceImpl(TwitterClient client, ObjectMapper jacksonObjectMapper, SolrTemplate template,
			TweetRepository repository) {
		this.client = client;
		this.jacksonObjectMapper = jacksonObjectMapper;
		this.repository = repository;
		this.template = template;
	}

	@Override
	public RulesResponse addRules(RulesRequest request) {
		RulesResponse response = new RulesResponse();
		try {
			AddRulesData rulesData = generateRulesRequest(request);
			String addRequest = jacksonObjectMapper.writeValueAsString(rulesData);
			
			response = client.deleteOrPostRules(addRequest);
		} catch (JsonProcessingException e) {
			log.error("Exception while parsing JSON: {}",e);
		}
		return response;
	}

	
	@Override
	public TweetsData fetchTweetsByPage(int pageNo, int records) {
		TweetsData data = new TweetsData();
		List<Long> ids = new ArrayList<>();
		List<String> tags = new ArrayList<>();
		RulesResponse rulesResponse = client.fetchMatchingRules();

		if (null != rulesResponse && !CollectionUtils.isEmpty(rulesResponse.getData())) {
			rulesResponse.getData().forEach(rule -> {
				ids.add(rule.getId());
				tags.add(rule.getTag());
			});

		}
		if (!ids.isEmpty() && !tags.isEmpty()) {
			Pageable pageRequest = PageRequest.of(pageNo, records, Sort.by("createdAt").descending());
			List<Tweet> tweets = repository.fetchTweetsByIds(ids, tags, pageRequest);
			Long count = template.count("tweets", new SimpleQuery("matchingRuleIds:(" + StringUtils.join(ids, ' ')
					+ ") OR matchingRuleTags: (" + StringUtils.join(tags, ' ') + ")"));
			data.setTweets(tweets);
			data.setNoOfRecords(count);
		}
		return data;
	}

	@Override
	public RulesResponse deleteRules(List<String> ruleIds) {
		String deleteQuery = String.format("{\"delete\":{\"ids\":%s}}", ruleIds);
		return client.deleteOrPostRules(deleteQuery);
	}

	@Override
	public RulesResponse fetchRules() {
		return client.fetchMatchingRules();
	}
	
	private AddRulesData generateRulesRequest(RulesRequest request) {
		AddRulesData rulesData = new AddRulesData();
		List<AddRulesInfo> finalInfoList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(request.getAccounts())){
			List<AddRulesInfo> infoListForAccounts = request.getAccounts().stream()
															   .map(this::prepareValueForNewAccount)
															   .collect(Collectors.toList());
			finalInfoList.addAll(infoListForAccounts);
		}
		if(!CollectionUtils.isEmpty(request.getHashTags())) {
			 List<AddRulesInfo> infoListForHashTags = request.getHashTags().stream()
										    							   .map(this::prepareValueForNewHashTag)
										    							   .collect(Collectors.toList());
			 finalInfoList.addAll(infoListForHashTags);
		}
		rulesData.setAdd(finalInfoList);
		return rulesData;
	}
	
	private AddRulesInfo prepareValueForNewHashTag(String hashTag) {
		AddRulesInfo info = new AddRulesInfo();
		info.setTag(hashTag);
		info.setValue(String.format("#%s -is:retweet", hashTag));
		return info;
	}
	
	private AddRulesInfo prepareValueForNewAccount(String username) {
		AddRulesInfo info = new AddRulesInfo();
		info.setTag(username);
		info.setValue(String.format("from:%s -is:retweet", username));
		return info;
	}

}
