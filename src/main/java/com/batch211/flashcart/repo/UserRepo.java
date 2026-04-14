package com.batch211.flashcart.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch211.flashcart.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
}
