package com.jphilips.twittercopy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.Follower;
import com.jphilips.twittercopy.entity.MyUser;

public interface FollowRepository extends JpaRepository<Follower, Long>{
	
	Page<Follower> findByFollowingOrderByIdDesc(MyUser following, Pageable pageable);
	
	Page<Follower> findByFollowerOrderByIdDesc(MyUser follower, Pageable pageable);
	
	Optional<Follower> findByFollowingAndFollower(MyUser following,MyUser follower);
}
