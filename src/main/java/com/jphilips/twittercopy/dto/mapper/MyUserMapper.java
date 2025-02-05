package com.jphilips.twittercopy.dto.mapper;

import java.util.stream.Collectors;

import com.jphilips.twittercopy.dto.MyUserRequestDTO;
import com.jphilips.twittercopy.dto.MyUserResomseDTO;
import com.jphilips.twittercopy.entity.MyUser;

public class MyUserMapper {
	
	
	public static MyUserResomseDTO toDto(MyUser myUser) {
		return new MyUserResomseDTO(
						myUser.getId(), 
						myUser.getUsername(),
						myUser.getFirstName(),
						myUser.getLastName(),
						myUser.isActive(),
						myUser.getRoles().stream()
										.map(role -> role.getRole().toString())
										.collect(Collectors.toList())
					);
	}
	
	public static MyUser toEntity(MyUserRequestDTO requestDTO) {
		MyUser myUser = new MyUser();
		
		myUser.setUsername(requestDTO.getUsername());
		myUser.setPassword(requestDTO.getPassword());
		
		myUser.setFirstName(requestDTO.getFirstName());
		myUser.setLastName(requestDTO.getLastName());
		
		
		return myUser;
	}
}
