package com.github.iambharaths.twitter.viewtweets.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesDataDTO", description = "All Details About Rules")
public class RulesDataDTO {
	@ApiModelProperty(notes = "Unique Id of Rule followed")
	private Long id;
	@ApiModelProperty(notes = "Value of Rule followed")
	private String value;
	@ApiModelProperty(notes = "Tag of Rule followed")
	private String tag;
}
