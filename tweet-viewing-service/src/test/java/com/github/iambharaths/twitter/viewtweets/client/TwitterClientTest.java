package com.github.iambharaths.twitter.viewtweets.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesDataVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesErrorVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesMetaVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesResponseVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesSummaryVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.mapper.ClientVoMapper;
import com.github.iambharaths.twitter.viewtweets.model.RulesData;
import com.github.iambharaths.twitter.viewtweets.model.RulesError;
import com.github.iambharaths.twitter.viewtweets.model.RulesMeta;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.RulesSummary;

@ExtendWith(MockitoExtension.class)
class TwitterClientTest {

	@InjectMocks
	private TwitterClient client;
	@Mock
	private ClientVoMapper clientVoMapper;
	@Mock
	private ObjectMapper jacksonObjectMapper;
	@Mock
	private HttpClient httpClient;
	
	@BeforeEach
	public void setup() throws IllegalAccessException {
		FieldUtils.writeField(client, "twitterUrl", "https://api.twitter.com/2/tweets/search/stream/rules",true);
	}
	
	@Test
	void testAddRules() throws ClientProtocolException, IOException, URISyntaxException {
		HttpResponse httpResponse = mock(HttpResponse.class);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		when(httpResponse.getEntity()).thenReturn(generateResponse());
		when(clientVoMapper.map(generateRulesResponseVO())).thenReturn(generateRulesResponse());
		when(jacksonObjectMapper.readValue(anyString(), eq(RulesResponseVO.class))).thenReturn(generateRulesResponseVO());
		RulesResponse response = client.deleteOrPostRules(addRequest());
		assertEquals(1L,response.getData().get(0).getId());
		assertEquals("Test tag",response.getData().get(0).getTag());
		assertEquals("Test value",response.getData().get(0).getValue());
		assertEquals("Test",response.getErrors().get(0).getDetail());
		assertEquals("Test",response.getErrors().get(0).getTitle());
		assertEquals("Test",response.getErrors().get(0).getDetail());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getMeta().getSent());
		assertEquals(1, response.getMeta().getSummary().getCreated());
		assertEquals(1, response.getMeta().getSummary().getNotDeleted());
	}
	
	@Test
	void testFetchRules() throws ClientProtocolException, IOException {
		HttpResponse httpResponse = mock(HttpResponse.class);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		when(httpResponse.getEntity()).thenReturn(generateResponse());
		when(clientVoMapper.map(generateRulesResponseVO())).thenReturn(generateRulesResponse());
		when(jacksonObjectMapper.readValue(anyString(), eq(RulesResponseVO.class))).thenReturn(generateRulesResponseVO());
		RulesResponse response = client.fetchMatchingRules();
		assertEquals(1L,response.getData().get(0).getId());
		assertEquals("Test tag",response.getData().get(0).getTag());
		assertEquals("Test value",response.getData().get(0).getValue());
		assertEquals("Test",response.getErrors().get(0).getDetail());
		assertEquals("Test",response.getErrors().get(0).getTitle());
		assertEquals("Test",response.getErrors().get(0).getDetail());
		assertEquals(Instant.parse("2020-11-30T00:00:00Z"), response.getMeta().getSent());
		assertEquals(1, response.getMeta().getSummary().getCreated());
		assertEquals(1, response.getMeta().getSummary().getNotDeleted());
	
	}
	
	private RulesResponse generateRulesResponse() {
		List<RulesData> datas = new ArrayList<>();
		List<RulesError> errors = new ArrayList<>();
		RulesMeta meta = new RulesMeta();
		RulesData data =  new RulesData();
		data.setId(1L);
		data.setTag("Test tag");
		data.setValue("Test value");
		datas.add(data);
		RulesError error = new RulesError();
		error.setId(1L);
		error.setDetail("Test");
		error.setTitle("Test");
		error.setValue("Test");
		errors.add(error);
		RulesSummary summary = new RulesSummary();
		summary.setCreated(1);
		summary.setNotDeleted(1);
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		meta.setSummary(summary);
		RulesResponse response = new RulesResponse();
		response.setData(datas);
		response.setErrors(errors);
		response.setMeta(meta);
		return response;
	}

	private RulesResponseVO generateRulesResponseVO() {
		List<RulesDataVO> datas = new ArrayList<>();
		List<RulesErrorVO> errors = new ArrayList<>();
		RulesMetaVO meta = new RulesMetaVO();
		RulesDataVO data =  new RulesDataVO();
		data.setId(1L);
		data.setTag("Test tag");
		data.setValue("Test value");
		datas.add(data);
		RulesErrorVO error = new RulesErrorVO();
		error.setId(1L);
		error.setDetail("Test");
		error.setTitle("Test");
		error.setValue("Test");
		errors.add(error);
		RulesSummaryVO summary = new RulesSummaryVO();
		summary.setCreated(1);
		summary.setNotDeleted(1);
		meta.setSent(Instant.parse("2020-11-30T00:00:00Z"));
		meta.setSummary(summary);
		RulesResponseVO response = new RulesResponseVO();
		response.setData(datas);
		response.setErrors(errors);
		response.setMeta(meta);
		return response;
	}

	private HttpEntity generateResponse() throws UnsupportedEncodingException {
		String responseEntity = "{\"data\": [{\"value\": \"Test value\",\"tag\": \"testTag\",\"id\": \"1333684444314574857\"}],\"meta\": {\"sent\": \"2020-12-01T08:08:44.878Z\", \\\"summary\\\": {\\\"created\\\": 1,\\\"not_created\\\": 0,\\\"valid\\\": 1,\\\"invalid\\\": 0}}}";
		return new StringEntity(responseEntity);
	}
	
	private String addRequest() {
		return "{\"add\":[{\"value\":\"Test value\",\"tag\":\"testTag\"}]}";
	}

}
