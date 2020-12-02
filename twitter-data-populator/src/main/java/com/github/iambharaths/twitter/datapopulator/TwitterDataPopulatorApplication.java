package com.github.iambharaths.twitter.datapopulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.iambharaths.twitter.datapopulator.service.TwitterService;
import com.github.iambharaths.twitter.datapopulator.service.impl.TwitterServiceImpl;

@SpringBootApplication
public class TwitterDataPopulatorApplication implements CommandLineRunner {

	private TwitterService twitterService;
	
	@Autowired
	public TwitterDataPopulatorApplication(TwitterServiceImpl twitterClient) {
		this.twitterService = twitterClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterDataPopulatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String account = System.getProperty("accounts");
		String tag = System.getProperty("tags");
		twitterService.addRulesToTwitter(account, tag);
		twitterService.fetchDataFromTwitter();
		
	}

}
