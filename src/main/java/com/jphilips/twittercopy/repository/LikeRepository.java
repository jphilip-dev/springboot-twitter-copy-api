package com.jphilips.twittercopy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.metrics.Like;
import java.util.Optional;

import com.jphilips.twittercopy.entity.MyUser;


public interface LikeRepository extends JpaRepository<Like, Long>{
	
	Optional<Like> findByTweet_idAndMyUser(Long tweetId, MyUser myUser);
	
	Page<Like> findByTweet_idOrderByIdAsc(Long tweetId, Pageable pageable);
}
