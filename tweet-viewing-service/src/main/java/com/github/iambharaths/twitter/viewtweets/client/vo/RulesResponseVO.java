package com.github.iambharaths.twitter.viewtweets.client.vo;

import java.util.List;

import lombok.Data;

@Data
public class RulesResponseVO {

	private List<RulesDataVO> data;
	private RulesMetaVO meta;
	private List<RulesErrorVO> errors;

}
