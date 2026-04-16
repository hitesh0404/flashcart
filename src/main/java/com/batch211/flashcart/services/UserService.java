package com.batch211.flashcart.services;

import java.util.List;

import com.batch211.flashcart.dto.UserRequestDto;
import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.entities.User;



public interface UserService {
	
	UserResponseDto createUser(UserRequestDto user);
	List<UserResponseDto> allUser();
	UserResponseDto getUserById(Long id);
	UserResponseDto getUserByEmail(String email);
	UserResponseDto mapToDto(User user);
	User mapToEntity(UserRequestDto userReq);
	
}