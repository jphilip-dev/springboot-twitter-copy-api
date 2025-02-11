package com.jphilips.twittercopy.dto.mapper;

import com.jphilips.twittercopy.dto.TweetLikeResponseDTO;
import com.jphilips.twittercopy.entity.metrics.Like;

public class TweetLikeMapper {
	public static TweetLikeResponseDTO toDto (Like like) {
		return new TweetLikeResponseDTO(like.getId(), MyUserMapper.toDto( like.getMyUser()));
	}
}
