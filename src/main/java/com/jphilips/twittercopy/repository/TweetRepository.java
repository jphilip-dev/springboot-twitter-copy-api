package com.jphilips.twittercopy.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
	
	Page<Tweet> findByMyUserOrderByIdAsc(MyUser myUser,Pageable pageable);
	
	@Query("""
			SELECT t
			FROM Tweet t
			JOIN Follower f ON f.following = t.myUser
			WHERE f.follower.id = :userId
			ORDER BY t.id DESC
			""")
	Page<Tweet> findTweetsFromFollowing(@Param("userId") Long userId, Pageable pageable);
	
	


}
