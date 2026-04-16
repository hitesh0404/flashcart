package com.batch211.flashcart.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String name;
	
	@Column(nullable = false)
	private int price;
	
	@ManyToMany
	@JsonManagedReference
	private List<Category> categories;
	
	@OneToMany(mappedBy = "product")
	@JsonBackReference
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "product")
	@JsonBackReference
	private List<OrderItem> orderItems;
	
	@ManyToOne
	@JsonBackReference
	private Brand brand;
	

}
