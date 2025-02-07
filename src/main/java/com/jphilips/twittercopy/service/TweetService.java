package com.jphilips.twittercopy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.TweetRequestDTO;
import com.jphilips.twittercopy.dto.TweetResponseDTO;
import com.jphilips.twittercopy.dto.mapper.TweetMapper;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;
import com.jphilips.twittercopy.entity.TweetMetrics;
import com.jphilips.twittercopy.exception.custom.TweetException;
import com.jphilips.twittercopy.exception.custom.TweetNotFoundException;
import com.jphilips.twittercopy.repository.TweetRepository;

import jakarta.transaction.Transactional;

@Service
public class TweetService {
	
	private TweetRepository tweetRepository;

	public TweetService(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}
	
	public List<TweetResponseDTO> getAllMyTweets(Authentication authentication, Pageable pageable){
		return tweetRepository.findByMyUserOrderByIdAsc( findByAuth(authentication),pageable).stream()
													.map(tweet -> TweetMapper.toDto(tweet))
													.collect(Collectors.toList());
	}
	
	public TweetResponseDTO getTweetById(Long id) {
		return TweetMapper.toDto(findById(id));
	}
	
	@Transactional
	public void addTweet(Authentication authentication, TweetRequestDTO tweetRequestDTO) {
		 MyUser myUser = findByAuth(authentication); 
		 
		 Tweet tweet = new Tweet();
		 TweetMetrics metrics = new TweetMetrics();
		 
		 tweet.setMyUser(myUser);
		 tweet.setTweet(tweetRequestDTO.getTweet());
		 tweet.setMetrics(metrics);
		 
		 tweetRepository.save(tweet);
	}
	
	@Transactional
	public void deleteTweet(Authentication authentication, Long id) {
		 MyUser myUser = findByAuth(authentication); 
		 
		 Tweet tweet = findById(id);
		 
		 if (tweet.getMyUser() != myUser) {
			 throw new TweetException("You do not have permission to delete this tweet");
		 }
		 
		 tweetRepository.delete(tweet);
		 
		 
	}
	
	// helper methods
	
	private Tweet findById(Long id) {
		return tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException());
	}
	
	private MyUser findByAuth(Authentication authentication) {
		return (MyUser) authentication.getPrincipal(); 
	}
	
}
