package com.jphilips.twittercopy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.metrics.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findByTweet_idOrderByIdAsc(Long id, Pageable pageable);

}
