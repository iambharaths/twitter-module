package com.github.iambharaths.twitter.viewtweets.service;

import java.util.List;

import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.TweetsData;

public interface TweetService {

	RulesResponse addRules(RulesRequest rulesData);
	
	TweetsData fetchTweetsByPage(int pageNo,int records);
	
	RulesResponse deleteRules(List<String> ruleIds);
	
	RulesResponse fetchRules();
}
