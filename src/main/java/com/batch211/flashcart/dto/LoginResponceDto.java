package com.batch211.flashcart.dto;

import lombok.Data;

@Data
public class LoginResponceDto {
	private String token;
	private String refreshToken;
}
