package com.github.iambharaths.twitter.viewtweets.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesErrorDTO", description = "All Details About Rules Error When Adding/Deleting Rules")
public class RulesErrorDTO {
	@ApiModelProperty(notes = "Unique Id of Rule followed")
	private Long id;
	@ApiModelProperty(notes = "Title of Error")
	private String title;
	@ApiModelProperty(notes = "Details of Error")
	private String detail;
	@ApiModelProperty(notes = "Value")
	private String value;
}
