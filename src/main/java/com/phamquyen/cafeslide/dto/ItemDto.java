package com.phamquyen.cafeslide.dto;

import com.phamquyen.cafeslide.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
	
	private Long itemId;
	private int quantity;
	private int price;
	private Product product;
	
	public int subTotal() {
		return this.price * this.quantity; 
	}
}
