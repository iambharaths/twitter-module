package com.github.iambharaths.twitter.viewtweets.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesSummaryDTO", description = "All Details About Rules Summary When Adding/Deleting rules")
public class RulesSummaryDTO {
	@ApiModelProperty(notes = "Number of ids Deleted")
	private int deleted;
	@ApiModelProperty(notes = "Number of ids not Deleted")
	private int notDeleted;
	@ApiModelProperty(notes = "Number of new rules created")
	private int created;
	@ApiModelProperty(notes = "Number of new rules not created")
	private int notCreated;
	@ApiModelProperty(notes = "Number of rules which are valid")
	private int valid;
	@ApiModelProperty(notes = "Number of rules which are invalid")
	private int invalid;
}
