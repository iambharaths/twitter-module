package com.github.iambharaths.twitter.viewtweets.controller.request;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RulesRequestDTO", description = "Request Details For Adding new Accounts/Tags")
public class RulesRequestDTO {
	@ApiModelProperty(notes = "List of Accounts to follow")
	private List<String> accounts;
	@ApiModelProperty(notes = "List of HashTags to follow")
	private List<String> hashTags;
}
