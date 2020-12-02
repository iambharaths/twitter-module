package com.github.iambharaths.twitter.viewtweets.controller.response;

import java.time.Instant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesMetaDTO", description = "All Details About Meta Information")
public class RulesMetaDTO {
	@ApiModelProperty(notes = "Request Sent Time in UTC")
	private Instant sent;
	@ApiModelProperty(notes = "Summary About Rules Added/Deleted")
	private RulesSummaryDTO summary;
}
