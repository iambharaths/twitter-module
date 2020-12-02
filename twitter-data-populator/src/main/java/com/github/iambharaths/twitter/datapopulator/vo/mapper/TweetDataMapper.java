package com.github.iambharaths.twitter.datapopulator.vo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.iambharaths.twitter.datapopulator.model.Tweet;
import com.github.iambharaths.twitter.datapopulator.vo.TweetInfoVo;

@Component
public class TweetDataMapper {

	public Tweet map(TweetInfoVo tweetInfoVo) {
		Tweet tweetInfo = new Tweet();
		List<Long> ruleIds = new ArrayList<>();
		List<String> ruleTags = new ArrayList<>();
		if (null != tweetInfoVo.getData()) {
			tweetInfo.setId(tweetInfoVo.getData().getId());
			tweetInfo.setCreatedAt(tweetInfoVo.getData().getCreatedAt());
			tweetInfo.setText(tweetInfoVo.getData().getText());
			if(null!=tweetInfoVo.getData().getPublicMetrics()) {
				tweetInfo.setReplyCount(tweetInfoVo.getData().getPublicMetrics().getReplyCount());
				tweetInfo.setLikeCount(tweetInfoVo.getData().getPublicMetrics().getLikeCount());
				tweetInfo.setRetweetCount(tweetInfoVo.getData().getPublicMetrics().getRetweetCount());
				tweetInfo.setQuoteCount(tweetInfoVo.getData().getPublicMetrics().getQuoteCount());
			}
		}

		if (null != tweetInfoVo.getIncludes() && !CollectionUtils.isEmpty(tweetInfoVo.getIncludes().getUsers())
				&& null != tweetInfoVo.getIncludes().getUsers().get(0)) {
			tweetInfo.setName(tweetInfoVo.getIncludes().getUsers().get(0).getName());
			tweetInfo.setUsername(tweetInfoVo.getIncludes().getUsers().get(0).getUsername());
		}
		
		if(!CollectionUtils.isEmpty(tweetInfoVo.getMatchingRules())) {
			tweetInfoVo.getMatchingRules().forEach(rule -> {
								ruleIds.add(rule.getId());
								ruleTags.add(rule.getTag());
												});
			tweetInfo.setMatchingRuleIds(ruleIds);
			tweetInfo.setMatchingRuleTags(ruleTags);
			
		}
		
		return tweetInfo;
	}
}
