package com.jphilips.twittercopy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.MyUserResponseDTO;
import com.jphilips.twittercopy.dto.mapper.MyUserMapper;
import com.jphilips.twittercopy.repository.MyUserRepository;


@Service
public class MyUserService implements UserDetailsService{
	
	private MyUserRepository repository;

	public MyUserService(MyUserRepository repository) {
		this.repository = repository;
	}
	
	public List<MyUserResponseDTO> getAllUsers(){
		return repository.findAll().stream()
							.map(myUser -> MyUserMapper.toDto(myUser))
							.collect(Collectors.toList());
		
	}
	
	public MyUserResponseDTO getUserById(Long id) {
		return MyUserMapper.toDto(
							repository.findById(id)
										.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id))); // placeholder exception
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    return repository.findByUsername(username)
	                     .orElseThrow(() -> new UsernameNotFoundException(username));
	}


}
