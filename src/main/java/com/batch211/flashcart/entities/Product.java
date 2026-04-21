package com.batch211.flashcart.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String name;
	
	@Column(nullable = false)
	private int price;
	
	@ManyToMany
	@JsonIgnore // Prevents infinite loop by ignoring products list in Category
	private List<Category> categories;

	@OneToMany(mappedBy = "product")
	@JsonIgnore   // Prevents infinite loop in Cart
	private List<Cart> carts;

	@OneToMany(mappedBy = "product")
	@JsonIgnore // Prevents infinite loop in OrderItem
	private List<OrderItem> orderItems;

	@ManyToOne
	@JsonIgnoreProperties({"products", "handler", "hibernateLazyInitializer"}) 
	private Brand brand;
}
