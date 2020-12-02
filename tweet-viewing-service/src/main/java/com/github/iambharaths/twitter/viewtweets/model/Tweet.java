package com.github.iambharaths.twitter.viewtweets.model;

import java.time.Instant;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;

@Data
@SolrDocument(collection = "tweets")
public class Tweet {
	@Id
	private Long id;
	@Field
	private String username;
	@Field
	private String text;
	@Field
	private Instant createdAt;
	@Field
	private List<Long> matchingRuleIds;
	@Field
	private List<String> matchingRuleTags;
	@Field
	private String name;
	@Field
	private int likeCount;
	@Field
	private int replyCount;
	@Field
	private int retweetCount;
	@Field
	private int quoteCount;
}
