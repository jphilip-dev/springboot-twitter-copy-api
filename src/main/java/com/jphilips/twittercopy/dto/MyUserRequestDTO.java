package com.jphilips.twittercopy.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserRequestDTO {
	
	@Size(min = 6, max = 16, message = "Username can only contain 6 - 16 characters")
	private String username;
	
	@Size(min = 6, message = "Password is at least 6 characters long")
	private String password;
	
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;

}
