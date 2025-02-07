package com.jphilips.twittercopy.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TweetMetricsResponseDTO {
	private Long likeCount;
	private Long commentCount;
	private Long shareCount;
}
