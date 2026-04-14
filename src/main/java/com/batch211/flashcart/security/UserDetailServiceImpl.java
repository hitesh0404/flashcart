package com.batch211.flashcart.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.batch211.flashcart.repo.UserRepo;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found With given Email "+email));
	}

}