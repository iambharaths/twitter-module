package com.github.iambharaths.twitter.datapopulator.vo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.iambharaths.twitter.datapopulator.model.Tweet;
import com.github.iambharaths.twitter.datapopulator.vo.IncludeInfo;
import com.github.iambharaths.twitter.datapopulator.vo.PublicMetric;
import com.github.iambharaths.twitter.datapopulator.vo.TweetData;
import com.github.iambharaths.twitter.datapopulator.vo.TweetInfoVo;
import com.github.iambharaths.twitter.datapopulator.vo.TweetMatchingRules;
import com.github.iambharaths.twitter.datapopulator.vo.UserInfo;

@ExtendWith(MockitoExtension.class)
 class TweetVoMapperTest {

	@InjectMocks
	private TweetDataMapper mapper;
	
	@Test
	void testMapper() {
		Tweet tweet = mapper.map(generateTweetVO());
		assertEquals(2L, tweet.getId());
		assertEquals(3L, tweet.getMatchingRuleIds().get(0));
		assertEquals(1, tweet.getLikeCount());
		assertEquals(2, tweet.getQuoteCount());
		assertEquals(3, tweet.getRetweetCount());
		assertEquals(4, tweet.getReplyCount());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), tweet.getCreatedAt());
		assertEquals("test", tweet.getText());
		assertEquals("testUser", tweet.getUsername());
		assertEquals("test user", tweet.getName());
	}

	private TweetInfoVo generateTweetVO() {
		TweetInfoVo vo = new TweetInfoVo();
		TweetData data = new TweetData();
		PublicMetric metric = new PublicMetric();
		metric.setLikeCount(1);
		metric.setQuoteCount(2);
		metric.setRetweetCount(3);
		metric.setReplyCount(4);
		data.setPublicMetrics(metric);
		data.setAuthorId(1L);
		data.setId(2L);
		data.setText("test");
		data.setCreatedAt(Instant.parse("2020-11-30T00:00:00Z"));
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
		TweetMatchingRules rule = new TweetMatchingRules();
		rule.setId(3L);
		List<TweetMatchingRules> rules = new ArrayList<>();
		rules.add(rule);
		vo.setMatchingRules(rules);
		return vo;
	}
}
