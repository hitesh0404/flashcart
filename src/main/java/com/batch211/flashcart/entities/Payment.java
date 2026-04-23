package com.batch211.flashcart.entities;

import com.batch211.flashcart.enums.PaymentStatus;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	private Order orderId;
	@ManyToOne
	@JsonManagedReference
	private User user;
	
	private Integer amount;
	
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String razorpayPaymentSign;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	

}

