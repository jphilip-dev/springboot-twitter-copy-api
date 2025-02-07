package com.jphilips.twittercopy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.MyUserResponseDTO;
import com.jphilips.twittercopy.dto.mapper.MyUserMapper;
import com.jphilips.twittercopy.entity.Follower;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.repository.FollowRepository;

@Service
public class FollowService {

	private FollowRepository followRepository;

	public FollowService(FollowRepository followRepository) {
		this.followRepository = followRepository;
	}

	public Page<MyUserResponseDTO> getAllMyFollowers(Authentication authentication, Pageable pageable) {

		MyUser myUser = (MyUser) authentication.getPrincipal();

		Page<Follower> followers = followRepository.findByFollowingOrderByIdDesc(myUser, pageable);

		return followers.map(follower -> MyUserMapper.toDto(follower.getFollower()));
	}

	public Page<MyUserResponseDTO> getAllMyFollowings(Authentication authentication, Pageable pageable) {

		MyUser myUser = (MyUser) authentication.getPrincipal();

		Page<Follower> followers = followRepository.findByFollowerOrderByIdDesc(myUser, pageable);

		return followers.map(follower -> MyUserMapper.toDto(follower.getFollowing()));
	}

	public void followUser(Authentication authentication, MyUser toFollow) {
		
		MyUser follower = (MyUser) authentication.getPrincipal();
		
		Follower checkIfExists = followRepository.findByFollowingAndFollower(toFollow, follower).orElse(null);
		
		if(checkIfExists == null) {
			Follower newFollower = new Follower();
			
			newFollower.setFollower(follower);
			newFollower.setFollowing(toFollow);
			
			followRepository.save(newFollower);
		}

	}

	public void unFollowUser(Authentication authentication, MyUser toUnFollow) {
		MyUser follower = (MyUser) authentication.getPrincipal();
		
		Follower checkIfExists = followRepository.findByFollowingAndFollower(toUnFollow, follower).orElse(null);
		
		if (checkIfExists != null) {
			followRepository.delete(checkIfExists);
		}
		
	}


	
	

}
