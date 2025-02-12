package com.jphilips.twittercopy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TweetCommentRequestDTO {
	
	@NotBlank
	private String comment;
}
