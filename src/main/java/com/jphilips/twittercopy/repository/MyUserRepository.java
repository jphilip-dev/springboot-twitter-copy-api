package com.jphilips.twittercopy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.twittercopy.entity.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{

}
