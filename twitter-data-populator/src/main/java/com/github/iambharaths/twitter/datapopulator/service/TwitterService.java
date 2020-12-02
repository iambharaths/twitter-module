package com.github.iambharaths.twitter.datapopulator.service;

public interface TwitterService {
	
	void addRulesToTwitter(String username, String tag);
	
	void fetchDataFromTwitter();
}
