package com.batch211.flashcart.serviceimpl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.batch211.flashcart.dto.UserRequestDto;
import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.repo.UserRepo;
import com.batch211.flashcart.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	UserRepo userRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;	
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userReq) {
		if (!userReq.getPassword().equals(userReq.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
		User user = mapToEntity(userReq);
		user.setPassword(passwordEncoder.encode(userReq.getPassword()));
		user.setRole("ROLE_CUSTOMER");
		return mapToDto(userRepo.save(user));
	}

	
	@Override
	public List<UserResponseDto> allUser() {
		return userRepo.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		return mapToDto(
				userRepo
				.findById(id)
				.orElseThrow(()->
				new RuntimeException("User not Found with id "+id)));
	}

	@Override
	public UserResponseDto getUserByEmail(String email) {
		return mapToDto(userRepo.findByEmail(email)
				.orElseThrow(()->new RuntimeException("User Not Found With given Email "+email)));
	
	}
	@Override
	public UserResponseDto mapToDto(User user) {
		UserResponseDto userRes = new UserResponseDto();
		userRes.setEmail(user.getEmail());
		userRes.setFname(user.getFname());
		userRes.setLname(user.getLname());
		return userRes;
	}
	@Override
	public User mapToEntity(UserRequestDto userReq) {
		User user = new User();
		user.setEmail(userReq.getEmail());
		user.setFname(userReq.getFname());
		user.setLname(userReq.getLname());
		user.setPassword(userReq.getPassword());
		return user;
	}

	
	
}