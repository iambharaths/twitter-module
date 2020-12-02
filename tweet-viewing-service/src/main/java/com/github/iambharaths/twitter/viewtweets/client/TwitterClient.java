package com.github.iambharaths.twitter.viewtweets.client;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesResponseVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.mapper.ClientVoMapper;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TwitterClient {

	private ClientVoMapper clientVoMapper;

	private ObjectMapper jacksonObjectMapper;
	
	private HttpClient httpClient;

	@Value("${twitter.url}")
	private String twitterUrl;

	@Value("${twitter.accessToken}")
	private String bearerToken;

	@Autowired
	public TwitterClient(ClientVoMapper rulesResponseMapper, ObjectMapper jacksonObjectMapper, HttpClient httpClient) {
		this.clientVoMapper = rulesResponseMapper;
		this.jacksonObjectMapper = jacksonObjectMapper;
		this.httpClient = httpClient;
	}

	public RulesResponse fetchMatchingRules() {
		RulesResponse rulesResponse = new RulesResponse();
		try {

			URIBuilder uriBuilder = new URIBuilder(twitterUrl);

			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
			httpGet.setHeader("content-type", "application/json");
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String responseBody = EntityUtils.toString(entity, "UTF-8");
				log.info(responseBody);
				RulesResponseVO responseVO = jacksonObjectMapper.readValue(responseBody, RulesResponseVO.class);
				rulesResponse = prepareRulesResponse(responseVO);
			}

		} catch (URISyntaxException | IOException e) {
			log.error(e.getLocalizedMessage());
		}
		return rulesResponse;
	}

	private RulesResponse prepareRulesResponse(RulesResponseVO responseVO) {
		RulesResponse response = new RulesResponse();
		if (null != responseVO) {
			 response = clientVoMapper.map(responseVO);
			log.info("Mapped response :::: {}",response);
		}
		return response;
	}

	public RulesResponse deleteOrPostRules(String request) {
		RulesResponse rulesSummary = new RulesResponse();
		try {
			URIBuilder uriBuilder = new URIBuilder(twitterUrl);

			HttpPost httpPost = new HttpPost(uriBuilder.build());
			httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
			httpPost.setHeader("content-type", "application/json");
			StringEntity body = new StringEntity(request);
			httpPost.setEntity(body);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String responseBody = EntityUtils.toString(entity, "UTF-8");
				log.info(responseBody);
				RulesResponseVO responseVO = jacksonObjectMapper.readValue(responseBody, RulesResponseVO.class);
				rulesSummary = prepareRulesResponse(responseVO);
			}
		} catch (URISyntaxException | IOException e) {
			log.error(e.getLocalizedMessage());
		}
		return rulesSummary;

	}



}