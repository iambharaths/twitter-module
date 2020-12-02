package com.github.iambharaths.twitter.viewtweets.client.vo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.iambharaths.twitter.viewtweets.client.vo.RulesDataVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesErrorVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesMetaVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesResponseVO;
import com.github.iambharaths.twitter.viewtweets.client.vo.RulesSummaryVO;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;

@ExtendWith(MockitoExtension.class)
 class ClientVoMapperTest {

	private ClientVoMapper clientMapper = Mappers.getMapper(ClientVoMapper.class);
	
	@Test
	void testMap() {
		RulesResponse response = clientMapper.map(generateRulesResponseVO());
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
	void testMapWhenNull() {
		RulesResponse response = clientMapper.map(null);
		assertEquals(null, response);
	}
	
	@Test
	void testMapWhenNew() {
		RulesResponse response = clientMapper.map(new RulesResponseVO());
		assertEquals(null, response.getData());
		assertEquals(null, response.getErrors());
		assertEquals(null, response.getErrors());
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
}
