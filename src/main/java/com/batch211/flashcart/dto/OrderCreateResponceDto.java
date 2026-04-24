package com.batch211.flashcart.dto;

import com.razorpay.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderCreateResponceDto {
	
	private Order rzorder;
	private com.batch211.flashcart.entities.Order order;
	private String id;
	private Double  amount;
	private String currency;
	
	public OrderCreateResponceDto(Order rzorder,com.batch211.flashcart.entities.Order order){
        
		this.id = rzorder.get("id").toString();
        
		this.amount = Double.parseDouble(rzorder.get("amount").toString());
        
		this.currency = rzorder.get("currency");
		this.rzorder = rzorder;
		this.order = order;
	}
	public OrderCreateResponceDto(com.batch211.flashcart.entities.Order order){
		this.order = order;
	}
}