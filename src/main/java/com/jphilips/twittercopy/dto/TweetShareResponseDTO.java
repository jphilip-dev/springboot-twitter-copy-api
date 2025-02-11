package com.jphilips.twittercopy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TweetShareResponseDTO {
	private Long id;

	private MyUserResponseDTO user;
}