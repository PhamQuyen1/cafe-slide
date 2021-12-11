package com.phamquyen.furniticaShop.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.phamquyen.furniticaShop.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	
	private Long orderId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	private String orderUser;  
	private String orderStatus;
	private String orderAddress;
	private int total;
	private List<Item> items; 
}
