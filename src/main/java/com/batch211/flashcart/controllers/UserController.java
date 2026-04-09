package com.batch211.flashcart.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;






@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<UserResponseDto>> getAllUser() {
		return ResponseEntity.ok(userService.allUser());
	}
}