package com.jphilips.twittercopy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{
	
	Optional<MyUser> findByUsername(String username);
	
}
