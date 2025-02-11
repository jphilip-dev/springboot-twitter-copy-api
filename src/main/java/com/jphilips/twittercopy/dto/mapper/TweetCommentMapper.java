package com.jphilips.twittercopy.dto.mapper;

import com.jphilips.twittercopy.dto.TweetCommentResponseDTO;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.metrics.Comment;

public class TweetCommentMapper {
	public static TweetCommentResponseDTO toDto (MyUser user, Comment comment) {
		return new TweetCommentResponseDTO(
								comment.getId(), 
								MyUserMapper.toDto(user), 
								comment.getComment());
	}
}
