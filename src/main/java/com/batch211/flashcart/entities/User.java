package com.batch211.flashcart.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String fname;
	private String lname;
	private String email;
	private String password;
	@CreationTimestamp
	private Date createdAt;
	@UpdateTimestamp
	private Date updatesAt;
}