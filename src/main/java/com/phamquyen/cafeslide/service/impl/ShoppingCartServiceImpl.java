package com.phamquyen.cafeslide.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.phamquyen.cafeslide.domain.Item;
import com.phamquyen.cafeslide.service.ShoppingCartService;

@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	Map<Long, Item> cartItems = new HashMap<>();
	
	@Override
	public void addToCart(Item item) {
		Item cartItem = cartItems.get(item.getProduct().getProductId());
		if(cartItem == null) {
			cartItems.put(item.getProduct().getProductId(), item);
		}else {
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
			cartItems.replace(item.getProduct().getProductId(), cartItem);  
		}
	} 
	
	@Override 
	public void update(Long productId, int quantity) {
		Item cartItem = cartItems.get(productId);
		cartItem.setQuantity(quantity);
		cartItems.replace(productId, cartItem);   
		
	}                  
	  
	@Override
	public Collection<Item> getAll() {
		return cartItems.values();
	}
	
	@Override
	public void delete(Long productId) {
		cartItems.remove(productId);
	}
	
	@Override
	public int getTotalAmount() {
		return cartItems.values().stream().mapToInt(o->o.getPrice()*o.getQuantity()).sum();
	}
	
	@Override
	public int getTotalItem() {
		return cartItems.values().stream().mapToInt(o->o.getQuantity()).sum();
	}
	
	@Override
	public void clearAll() {
		cartItems.clear();
	}

}
