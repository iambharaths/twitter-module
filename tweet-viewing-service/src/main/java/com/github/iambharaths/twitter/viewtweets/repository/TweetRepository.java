package com.github.iambharaths.twitter.viewtweets.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.github.iambharaths.twitter.viewtweets.model.Tweet;

public interface TweetRepository extends SolrCrudRepository<Tweet, String>{
	@Query("matchingRuleIds:(?0) OR matchingRuleTags:(?1)")
	List<Tweet> fetchTweetsByIds(List<Long> ids,List<String> tags, Pageable pagerequest);
}
