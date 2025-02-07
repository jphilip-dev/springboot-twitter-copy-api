package com.jphilips.twittercopy.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.twittercopy.dto.TweetRequestDTO;
import com.jphilips.twittercopy.dto.TweetResponseDTO;
import com.jphilips.twittercopy.service.TweetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
	private TweetService tweetService;

	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping("/my-tweets")
	public Page<TweetResponseDTO> getAllMyTweets(Authentication authentication, Pageable pageable) {
		return tweetService.getAllMyTweets(authentication, pageable);
	}

	@GetMapping("/following-tweets")
	public Page<TweetResponseDTO> getTweetsFromFollowing(Authentication authentication, Pageable pageable) {
		return tweetService.getTweetsFromFollowing(authentication, pageable);
	}

	@GetMapping("/{tweetId}")
	public TweetResponseDTO getAllMyTweets(Authentication authentication, @PathVariable Long tweetId) {
		return tweetService.getTweetById(tweetId);
	}

	@PostMapping()
	public ResponseEntity<Void> postMethodName(Authentication authentication,
			@RequestBody TweetRequestDTO tweetRequestDTO) {
		tweetService.addTweet(authentication, tweetRequestDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{tweetId}")
	public ResponseEntity<Void> deleteTweet(Authentication authentication, @PathVariable Long tweetId) {
		tweetService.deleteTweet(authentication, tweetId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
