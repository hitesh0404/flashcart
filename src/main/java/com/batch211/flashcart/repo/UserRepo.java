package com.batch211.flashcart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch211.flashcart.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
}
