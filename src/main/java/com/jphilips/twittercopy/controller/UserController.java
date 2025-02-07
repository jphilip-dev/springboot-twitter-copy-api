package com.jphilips.twittercopy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.twittercopy.dto.MyUserResponseDTO;
import com.jphilips.twittercopy.service.FollowService;
import com.jphilips.twittercopy.service.MyUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private MyUserService myUserService;
	private FollowService followService;

	public UserController(MyUserService myUserService, FollowService followService) {
		this.myUserService = myUserService;
		this.followService = followService;
	}
	
	@GetMapping("/my-followers")
	public Page<MyUserResponseDTO> getAllMyFollowers(Authentication authentication, Pageable pageable){
		return followService.getAllMyFollowers(authentication,pageable);
	}
	
	@GetMapping("/my-followings")
	public Page<MyUserResponseDTO> getAllMyFollowings(Authentication authentication, Pageable pageable){
		return followService.getAllMyFollowings(authentication,pageable);
	}
	
	@PostMapping("/follow/{username}")
	public ResponseEntity<Void> followUser(Authentication authentication, @PathVariable String username){
		followService.followUser(authentication, myUserService.loadUserByUsername(username));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/unfollow/{username}")
	public ResponseEntity<Void> unFollowUser(Authentication authentication, @PathVariable String username){
		followService.unFollowUser(authentication, myUserService.loadUserByUsername(username));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
