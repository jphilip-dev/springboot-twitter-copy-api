package com.jphilips.twittercopy.dto.mapper;

import com.jphilips.twittercopy.dto.TweetMetricsResponseDTO;
import com.jphilips.twittercopy.dto.TweetResponseDTO;
import com.jphilips.twittercopy.entity.Tweet;
import com.jphilips.twittercopy.entity.TweetMetrics;

public class TweetMapper {
	public static TweetResponseDTO toDto (Tweet tweet) {
		return new TweetResponseDTO(
						tweet.getId(),
						tweet.getTweet(), 
						tweet.getMyUser().getUsername(),
						TweetMetricsMapper.toDto(tweet.getMetrics()));
	}
}

class TweetMetricsMapper {
	public static TweetMetricsResponseDTO toDto(TweetMetrics metrics) {
		return new TweetMetricsResponseDTO(
								metrics.getLikeCount(), 
								metrics.getCommentCount(), 
								metrics.getShareCount());
	}
}
