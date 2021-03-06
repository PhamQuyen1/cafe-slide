package com.phamquyen.cafeslide.service;

import java.util.Collection;

import com.phamquyen.cafeslide.domain.Item;

public interface ShoppingCartService {

	void update(Long productId, int quantity);

	void addToCart(Item item);

	Collection<Item> getAll();

	void delete(Long productId);

	int getTotalAmount();

	int getTotalItem();

	void clearAll();

}
