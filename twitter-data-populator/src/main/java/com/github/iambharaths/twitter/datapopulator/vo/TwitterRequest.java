package com.github.iambharaths.twitter.datapopulator.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TwitterRequest {

	private List<TwitterRequestEntity> add;
}
