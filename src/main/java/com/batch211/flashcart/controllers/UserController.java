package com.batch211.flashcart.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.batch211.flashcart.dto.UserRequestDto;
import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.services.UserService;

import jakarta.validation.Valid;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;








@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<UserResponseDto>> getAllUser() {
		return ResponseEntity.ok(userService.allUser());
	}
	@GetMapping("/{id}/")
	public ResponseEntity<UserResponseDto>  getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	@GetMapping("/email/")
	public ResponseEntity<UserResponseDto>  getUserByEmail(@RequestParam String email) {
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	 
	@PostMapping("/")
	public ResponseEntity<UserResponseDto> createUser
								(@Valid @RequestBody UserRequestDto userReq) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userService.createUser(userReq));
	}
	
	@GetMapping("/profile/")
	public ResponseEntity<UserResponseDto> getUserProfile(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(userService.mapToDto((User)user));
	}

}