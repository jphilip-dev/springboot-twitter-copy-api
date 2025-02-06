package com.jphilips.twittercopy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.MyUserRequestDTO;
import com.jphilips.twittercopy.dto.MyUserResponseDTO;
import com.jphilips.twittercopy.dto.mapper.MyUserMapper;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.enums.UserRole;
import com.jphilips.twittercopy.repository.MyUserRepository;

import jakarta.transaction.Transactional;

@Service
public class MyUserService {
	
	private MyUserRepository repository;

	public MyUserService(MyUserRepository repository) {
		this.repository = repository;
	}
	
	@Transactional // for mapper, to be able to access roles 
	public List<MyUserResponseDTO> getAllUsers(){
		return repository.findAll().stream()
							.map(myUser -> MyUserMapper.toDto(myUser))
							.collect(Collectors.toList());
		
	}
	
	@Transactional
	public MyUserResponseDTO getUserById(Long id) {
		return MyUserMapper.toDto(
							repository.findById(id)
										.orElseThrow(() -> new IllegalArgumentException())); // placeholder exception
	}
	
	@Transactional
	public MyUserResponseDTO addUser(MyUserRequestDTO myUserRequestDTO) {
		
		MyUser myUser = MyUserMapper.toEntity(myUserRequestDTO);
		
		myUser.setPassword(myUser.getPassword()); // encrypt this
		
		myUser.setActive(false); // Initial users are set to inactive
		
		myUser.addRole(UserRole.USER);
		
		return MyUserMapper.toDto(repository.save(myUser));
		
	}
	
	
}
