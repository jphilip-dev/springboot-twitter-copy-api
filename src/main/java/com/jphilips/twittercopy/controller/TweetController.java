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

import com.jphilips.twittercopy.dto.TweetCommentRequestDTO;
import com.jphilips.twittercopy.dto.TweetCommentResponseDTO;
import com.jphilips.twittercopy.dto.TweetLikeResponseDTO;
import com.jphilips.twittercopy.dto.TweetRequestDTO;
import com.jphilips.twittercopy.dto.TweetResponseDTO;
import com.jphilips.twittercopy.dto.TweetShareResponseDTO;
import com.jphilips.twittercopy.service.TweetService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


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
	public ResponseEntity<Void> addTweet(Authentication authentication,
			@Valid @RequestBody TweetRequestDTO tweetRequestDTO) {
		tweetService.addTweet(authentication, tweetRequestDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{tweetId}")
	public ResponseEntity<Void> deleteTweet(Authentication authentication, @PathVariable Long tweetId) {
		tweetService.deleteTweet(authentication, tweetId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{tweetId}/likes")
	public ResponseEntity<Void>  likeTweet(Authentication authentication,@PathVariable Long tweetId) {
		tweetService.likeTweet(authentication, tweetId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{tweetId}/shares")
	public ResponseEntity<Void>  shareTweet(Authentication authentication,@PathVariable Long tweetId) {
		tweetService.shareTweet(authentication, tweetId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{tweetId}/comments")
	public ResponseEntity<Void>  commentTweet(Authentication authentication,@PathVariable Long tweetId, @Valid @RequestBody TweetCommentRequestDTO tweetCommentRequestDTO) {
		tweetService.commentTweet(authentication, tweetId, tweetCommentRequestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{tweetId}/likes")
	public Page<TweetLikeResponseDTO> getTweetLikes(Authentication authentication,@PathVariable Long tweetId, Pageable pageable) {
		return tweetService.getLikes(authentication, tweetId, pageable);
	}
	@GetMapping("/{tweetId}/shares")
	public Page<TweetShareResponseDTO> getTweetShares(Authentication authentication,@PathVariable Long tweetId, Pageable pageable) {
		return tweetService.getShares(authentication, tweetId, pageable);
	}
	@GetMapping("/{tweetId}/comments")
	public Page<TweetCommentResponseDTO> getTweetComments(Authentication authentication,@PathVariable Long tweetId, Pageable pageable) {
		return tweetService.getComments(authentication, tweetId, pageable);
	}
	

}
