package com.batch211.flashcart.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;











@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String fname;
	private String lname;
	private String email;
	private String role;
	private String password;
	@CreationTimestamp
	private Date createdAt;
	@UpdateTimestamp
	private Date updatesAt;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String rolename = (role.isBlank() || role.isEmpty())? "ROLE_CUSTOMER" : role;
		return Arrays.asList(new SimpleGrantedAuthority(rolename));
	}
	@Override
	public String getUsername() {
		return email;
	}
}