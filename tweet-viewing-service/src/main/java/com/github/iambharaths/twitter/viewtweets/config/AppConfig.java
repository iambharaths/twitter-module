package com.github.iambharaths.twitter.viewtweets.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {

	

	@Bean
	public ObjectMapper jacksonObjectMapper() {
		return new ObjectMapper().registerModule(new JavaTimeModule())
								 .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
								 .setSerializationInclusion(Include.NON_NULL)
								 .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
								 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Bean
	public HttpClient httpClient() {
		return HttpClients.custom()
						  .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
						  .build();

	}
}
