package com.jphilips.twittercopy.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TweetResponseDTO {
	private Long id;
	
	private String tweet;
	
	private String myUser;
	
	private TweetMetricsResponseDTO metrics;
}
