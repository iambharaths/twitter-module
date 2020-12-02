package com.github.iambharaths.twitter.datapopulator.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TwitterClient {

	@Value("${twitter.bearerToken}")
	private String bearerToken;

	@Value("${twitter.url}")
	private String twitterURL;

	private HttpClient httpClient;
	
	
	@Autowired
	public TwitterClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private static final String AUTHORIZATION = "Authorization";

	public void addRules(String request) throws URISyntaxException, IOException {
		try {
			
			URIBuilder uriBuilder = new URIBuilder(twitterURL + "/rules");

			HttpPost httpPost = new HttpPost(uriBuilder.build());
			httpPost.setHeader(AUTHORIZATION, String.format("Bearer %s", bearerToken));
			httpPost.setHeader("content-type", "application/json");
			StringEntity body = new StringEntity(request);
			httpPost.setEntity(body);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				log.info(EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (URISyntaxException | IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public InputStream fetchTweetsFromTwitter() throws URISyntaxException, IOException {

		URIBuilder uriBuilder = new URIBuilder(
				twitterURL + "?tweet.fields=text,created_at,public_metrics&expansions=author_id&user.fields=username");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader(AUTHORIZATION, String.format("Bearer %s", bearerToken));

		HttpResponse response = httpClient.execute(httpGet);

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			return entity.getContent();
		} else {
			throw new IOException();
		}

	}
}
