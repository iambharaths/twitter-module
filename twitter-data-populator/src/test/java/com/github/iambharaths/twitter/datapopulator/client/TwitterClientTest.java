package com.github.iambharaths.twitter.datapopulator.client;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TwitterClientTest {

	@InjectMocks
	private TwitterClient client;

	@Mock
	private HttpClient httpClient;

	@Test
	void testAddRules() throws ClientProtocolException, IOException, URISyntaxException {
		HttpResponse response = mock(HttpResponse.class);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
		when(response.getEntity()).thenReturn(generateResponse());
		client.addRules(generateStringRequest());
		verify(httpClient).execute(any(HttpUriRequest.class));
	}

	private HttpEntity generateResponse() throws UnsupportedEncodingException {
		String responseEntity = "{\"data\": [{\"value\": \"test Tag\",\"tag\": \"testTag\",\"id\": \"1333684444314574857\"}],\"meta\": {\"sent\": \"2020-12-01T08:08:44.878Z\", \\\"summary\\\": {\\\"created\\\": 1,\\\"not_created\\\": 0,\\\"valid\\\": 1,\\\"invalid\\\": 0}}}";
		return new StringEntity(responseEntity);
	}

	@Test
	void testFetchTweets() throws URISyntaxException, IOException {
		File file = new File("src/test/resources/testFile.txt");
		HttpResponse response = mock(HttpResponse.class);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
		when(response.getEntity()).thenReturn(new FileEntity(file));
		InputStream input = client.fetchTweetsFromTwitter();
		assertNotEquals(null, input);
	}

	private String generateStringRequest() {
		return "{\"add\":[{\"value\":\"Test value\",\"tag\":\"Test tag\"}]}";
	}

	

}
