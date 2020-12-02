package com.github.iambharaths.twitter.viewtweets.controller.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesResponseDTO", description = "All Details About Rules Response When Adding/Deleting/Fetching rules")
public class RulesResponseDTO {
	@ApiModelProperty(notes = "List of Rules Data")
	private List<RulesDataDTO> data;
	@ApiModelProperty(notes = "List of Rules Meta Info")
	private RulesMetaDTO meta;
	@ApiModelProperty(notes = "List of Errors")
	private List<RulesErrorDTO> errors;
}
