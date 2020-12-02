package com.github.iambharaths.twitter.datapopulator.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.github.iambharaths.twitter.datapopulator.model.Tweet;

public interface TwitterCrudRepository extends SolrCrudRepository<Tweet, String>{

}
