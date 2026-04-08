package com.batch211.flashcart.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;







@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
	
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<UserResponseDto>> getAllUser() {
		return ResponseEntity.ok(userService.allUser());
	}
	
}
