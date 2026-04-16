package com.batch211.flashcart.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.batch211.flashcart.dto.LoginRequestDto;
import com.batch211.flashcart.dto.LoginResponseDto;
import com.batch211.flashcart.dto.RefreshTokenRequest;
import com.batch211.flashcart.dto.RefreshTokenResponse;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.repo.UserRepo;
import com.batch211.flashcart.security.JwtService;
import com.batch211.flashcart.security.UserDetailServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("api/user")
public class AuthController {
	@Autowired
    private  AuthenticationManager authenticationManager;
	@Autowired
    private  JwtService jwtService;
	@Autowired
    private  UserRepo userRepo;
	@Autowired
	private UserDetailServiceImpl customUserDetailsService;
	
    @PostMapping("/login/")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails);
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDto(token,refreshToken));
    }
    
    @PostMapping("/refresh/")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        System.out.println(refreshToken);
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        System.out.println("dfdf"+userDetails);
        
        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            return ResponseEntity.status(403).build();
        }

        String newAccessToken = jwtService.generateToken(userDetails);
        // optional: also generate a new refresh token:
        // String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(
                new RefreshTokenResponse(newAccessToken, refreshToken)
        );
    }
}