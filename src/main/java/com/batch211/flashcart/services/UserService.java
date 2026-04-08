package com.batch211.flashcart.services;

import java.util.List;

import com.batch211.flashcart.dto.UserRequestDto;
import com.batch211.flashcart.dto.UserResponseDto;











public interface UserService {
	
	UserResponseDto createUser(UserRequestDto user);
	List<UserResponseDto> allUser();
	UserResponseDto getUserById(Long id);
	UserRequestDto getUserByEmail(String email);
	
}