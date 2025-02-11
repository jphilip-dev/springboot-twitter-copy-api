package com.jphilips.twittercopy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.metrics.Share;

public interface ShareRepository extends JpaRepository<Share, Long>{

	Optional<Share> findByTweet_idAndMyUser(Long id, MyUser myUser);

	Page<Share> findByTweet_idOrderByIdAsc(Long id, Pageable pageable);

}
