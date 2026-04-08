package com.batch211.flashcart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequestDto {
	
	@NotNull
	private String fname;
	@NotNull
	private String lname;
	@Email
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String confirmPassword;
}