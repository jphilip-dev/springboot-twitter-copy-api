package com.jphilips.twittercopy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TweetCommentResponseDTO {
	
	private Long id;
	
	private MyUserResponseDTO user;
	
	private String comment;
}
