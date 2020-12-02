package com.github.iambharaths.twitter.viewtweets.controller.request.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.iambharaths.twitter.viewtweets.controller.request.RulesRequestDTO;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;

@ExtendWith(MockitoExtension.class)
 class AddRulesDataMapperTest {

	private RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);
	
	@Test
	void testMap() {
		RulesRequest data = requestMapper.map(generateAddRulesRequest());
		assertEquals(2, data.getAccounts().size());
		assertEquals("account1",  data.getAccounts().get(0));
		assertEquals("account2",  data.getAccounts().get(1));
		assertEquals("hashTag1",  data.getHashTags().get(0));
		assertEquals("hashTag2",  data.getHashTags().get(1));
	}
	
	@Test
	void testMapIfNull() {
		RulesRequest data = requestMapper.map(null);
		assertEquals(null, data);
	}
	
	

	private RulesRequestDTO generateAddRulesRequest() {
		RulesRequestDTO addRules = new RulesRequestDTO();
		addRules.setAccounts(Arrays.asList("account1","account2"));
		addRules.setHashTags(Arrays.asList("hashTag1","hashTag2"));
		return addRules;
	}
	
}
