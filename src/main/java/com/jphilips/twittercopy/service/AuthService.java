package com.jphilips.twittercopy.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.jphilips.twittercopy.dto.JwtResponseDTO;
import com.jphilips.twittercopy.dto.LoginRequestDTO;
import com.jphilips.twittercopy.dto.MyUserRequestDTO;
import com.jphilips.twittercopy.dto.mapper.MyUserMapper;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.enums.UserRole;
import com.jphilips.twittercopy.exception.custom.CustomValidationException;
import com.jphilips.twittercopy.repository.MyUserRepository;


@Service
public class AuthService {

	private MyUserRepository repository;

	private AuthenticationManager authenticationManager;
	
	private PasswordEncoder encoder;
	
	private JwtService jwtService;

	public AuthService(MyUserRepository repository, AuthenticationManager authenticationManager,PasswordEncoder encoder,JwtService jwtService) {
		this.repository = repository;
		this.authenticationManager = authenticationManager;
		this.encoder = encoder;
		this.jwtService = jwtService;
	}
	
	public JwtResponseDTO login(LoginRequestDTO loginRequestDTO, BindingResult bindingResult)
			throws MethodArgumentNotValidException {
		
		if (!bindingResult.hasErrors()) {
			try {
				@SuppressWarnings("unused")
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
			} catch (BadCredentialsException ex) {
				
				handleAuthenticationError(loginRequestDTO,bindingResult);

			}
		}
		

		if (bindingResult.hasErrors()) {
			throw new CustomValidationException(bindingResult);
		}
		
		
		JwtResponseDTO jwt = new JwtResponseDTO(jwtService.generateToken(loginRequestDTO.getUsername())); // place holder

		return jwt;
	}
	
	
	
	public void register(MyUserRequestDTO myUserRequestDTO, BindingResult bindingResult) throws MethodArgumentNotValidException {
		if (!bindingResult.hasErrors() && userExists(myUserRequestDTO.getUsername())) {
			
			bindingResult.rejectValue("username", "username.exists", "Username alreadt exists.");
			
		}
		
		if (bindingResult.hasErrors()) {
			throw new CustomValidationException(bindingResult);
		}
		
		MyUser myUser = MyUserMapper.toEntity(myUserRequestDTO);
		
		myUser.setPassword(encoder.encode(myUser.getPassword())); 
		
		myUser.setActive(true); // Initial users are set to inactive, testing purposes
		
		myUser.addRole(UserRole.USER);
		
		repository.save(myUser);
		
	}
	
	
	// helper methods
	
	private void handleAuthenticationError(LoginRequestDTO loginRequestDTO, BindingResult bindingResult) {
        if (!userExists(loginRequestDTO.getUsername())) {
            bindingResult.rejectValue("username", "username.notfound", "Username don't exist.");
        } else {
            bindingResult.rejectValue("password", "password.incorrect", "Incorrect password.");
        }
    }

	private boolean userExists(String username) {
		return (repository.findByUsername(username).orElse(null)) != null ? true : false; // Assume user doesn't exist for demo purposes
	}

}
