package com.github.iambharaths.twitter.viewtweets.client.vo.mapper;

import org.mapstruct.Mapper;

import com.github.iambharaths.twitter.viewtweets.client.vo.RulesResponseVO;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;

@Mapper(componentModel = "spring")
public interface ClientVoMapper {

	RulesResponse map(RulesResponseVO rulesVO);
}
