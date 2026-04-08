package com.batch211.flashcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponseDto {
	private String fname;
	private String lname;
	private String email;
}