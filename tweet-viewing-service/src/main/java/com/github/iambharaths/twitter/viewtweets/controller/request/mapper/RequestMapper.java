package com.github.iambharaths.twitter.viewtweets.controller.request.mapper;

import org.mapstruct.Mapper;

import com.github.iambharaths.twitter.viewtweets.controller.request.RulesRequestDTO;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {

	RulesRequest map(RulesRequestDTO addRulesRequest);
}
