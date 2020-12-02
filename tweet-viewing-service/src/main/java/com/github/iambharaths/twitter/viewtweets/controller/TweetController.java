package com.github.iambharaths.twitter.viewtweets.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.iambharaths.twitter.viewtweets.controller.request.RulesRequestDTO;
import com.github.iambharaths.twitter.viewtweets.controller.request.mapper.RequestMapper;
import com.github.iambharaths.twitter.viewtweets.controller.response.RulesResponseDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.TweetsDataDTO;
import com.github.iambharaths.twitter.viewtweets.controller.response.mapper.ResponseMapper;
import com.github.iambharaths.twitter.viewtweets.exception.RecordNotFoundException;
import com.github.iambharaths.twitter.viewtweets.model.RulesRequest;
import com.github.iambharaths.twitter.viewtweets.model.RulesResponse;
import com.github.iambharaths.twitter.viewtweets.model.TweetsData;
import com.github.iambharaths.twitter.viewtweets.service.TweetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = {"Tweets API"})
@RestController
public class TweetController {

	private TweetService service;
	private RequestMapper requestMapper;
	private ResponseMapper responseMapper;

	public TweetController(TweetService service, RequestMapper requestMapper, ResponseMapper responseMapper) {
		this.service = service;
		this.requestMapper = requestMapper;
		this.responseMapper = responseMapper;
	}
	
	@ApiOperation(value = "Fetching tweets by page number starting from zero and number of records")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Resource Retreived Successfully"), @ApiResponse(code = 404, message = "Requested Resource Not found")})
	@GetMapping("/tweets")
	public ResponseEntity<TweetsDataDTO> fetchTweets(@RequestParam("page_num") int page,
			@RequestParam("no_of_records") int recordSize) {
		TweetsData fetchedTweets = service.fetchTweetsByPage(page, recordSize);
		TweetsDataDTO tweetsPOs = new TweetsDataDTO();
		if (!CollectionUtils.isEmpty(fetchedTweets.getTweets())) {
			tweetsPOs.setTweets(fetchedTweets.getTweets().stream()
														 .map(e -> responseMapper.mapTweet(e))
														 .collect(Collectors.toList()));
			tweetsPOs.setNoOfRecords(fetchedTweets.getNoOfRecords());
		}
		else {
			throw new RecordNotFoundException("No tweets Found");
		}
		return new ResponseEntity<>(tweetsPOs, HttpStatus.OK);
	}

	@ApiOperation(value = "Fetching following rules configuration(account, tags)")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Resource Retreived Successfully")})
	@GetMapping("/rules")
	public ResponseEntity<RulesResponseDTO> fetchRules() {
		RulesResponse response = service.fetchRules();
		RulesResponseDTO rulesPO = responseMapper.mapResponse(response);
		return new ResponseEntity<>(rulesPO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Saving new rules configuration(account, tags)")
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Resource saved successfully")})
	@PostMapping("/rules")
	public ResponseEntity<RulesResponseDTO> addRules(@RequestBody RulesRequestDTO dataPO) {
		RulesRequest data = requestMapper.map(dataPO);
		RulesResponse response = service.addRules(data);
		return new ResponseEntity<>(responseMapper.mapResponse(response), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Deleting following rules configuration(account, tags) by matching rules id")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Resource Deleted successfully")})
	@DeleteMapping("/rules")
	public ResponseEntity<RulesResponseDTO> deleteRules(@RequestParam("id") List<String> ids) {
		RulesResponse response = service.deleteRules(ids);
		return new ResponseEntity<>(responseMapper.mapResponse(response), HttpStatus.OK);
	}

}
