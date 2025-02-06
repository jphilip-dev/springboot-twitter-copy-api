package com.jphilips.twittercopy.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class MyUserResponseDTO {
	
	private Long id;

	private String username;

	private String firstName;
	
	private String lastName;

	private boolean isActive;

	private List<String> roles;
}
