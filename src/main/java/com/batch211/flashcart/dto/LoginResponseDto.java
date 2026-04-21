package com.batch211.flashcart.dto;

import com.batch211.flashcart.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	private String token;
	private String refreshToken;
	private UserResponseDto user;
}
