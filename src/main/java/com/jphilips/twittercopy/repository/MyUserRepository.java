package com.jphilips.twittercopy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jphilips.twittercopy.entity.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{
	
	@Query("SELECT u FROM MyUser u Left JOIN FETCH u.roles WHERE u.username = :username")
	Optional<MyUser> findByUsername(@Param("username") String username);
	
}
