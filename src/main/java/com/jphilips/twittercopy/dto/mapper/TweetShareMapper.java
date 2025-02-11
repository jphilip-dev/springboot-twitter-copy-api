package com.jphilips.twittercopy.dto.mapper;

import com.jphilips.twittercopy.dto.TweetShareResponseDTO;
import com.jphilips.twittercopy.entity.metrics.Share;

public class TweetShareMapper {
	public static TweetShareResponseDTO toDto (Share share) {
		return new TweetShareResponseDTO(share.getId(), MyUserMapper.toDto( share.getMyUser()));
	}
}
