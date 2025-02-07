package com.jphilips.twittercopy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.MyUserResponseDTO;
import com.jphilips.twittercopy.dto.mapper.MyUserMapper;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.repository.MyUserRepository;



@Service
public class MyUserService implements UserDetailsService{
	
	private MyUserRepository myUserRepository;
	

	public MyUserService(MyUserRepository myUserRepository) {
		this.myUserRepository = myUserRepository;
	}
	
	public List<MyUserResponseDTO> getAllUsers(){
		return myUserRepository.findAll().stream()
							.map(myUser -> MyUserMapper.toDto(myUser))
							.collect(Collectors.toList());
		
	}
	
	public MyUserResponseDTO getUserById(Long id) {
		return MyUserMapper.toDto(
				myUserRepository.findById(id)
										.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id))); // placeholder exception
	}
	
	@Override
	public MyUser loadUserByUsername(String username) throws UsernameNotFoundException {
	    return myUserRepository.findByUsername(username)
	                     .orElseThrow(() -> new UsernameNotFoundException(username));
	}

	

	

	
	
	

	


}
