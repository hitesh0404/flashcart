package com.batch211.flashcart.serviceimpl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.batch211.flashcart.dto.UserRequestDto;
import com.batch211.flashcart.dto.UserResponseDto;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.repo.UserRepo;
import com.batch211.flashcart.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	UserRepo userRepo;
	
	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;	
	}

	@Override
	public UserResponseDto createUser(UserRequestDto user) {
		return mapToDto(userRepo.save(mapToEntity(user)));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRequestDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private UserResponseDto mapToDto(User user) {
		UserResponseDto userRes = new UserResponseDto();
		userRes.setEmail(user.getEmail());
		userRes.setFname(user.getFname());
		userRes.setLname(user.getLname());
		return userRes;
	}
	private User mapToEntity(UserRequestDto userReq) {
		User user = new User();
		user.setEmail(userReq.getEmail());
		user.setFname(userReq.getFname());
		user.setLname(userReq.getLname());
		user.setPassword(userReq.getPassword());
		return user;
	}

	
}