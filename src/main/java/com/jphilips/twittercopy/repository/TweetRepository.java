package com.jphilips.twittercopy.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
	
	Page<Tweet> findByMyUserOrderByIdAsc(MyUser myUser,Pageable pageable);
	
}
