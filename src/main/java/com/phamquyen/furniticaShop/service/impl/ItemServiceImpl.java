package com.phamquyen.furniticaShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamquyen.furniticaShop.domain.Item;
import com.phamquyen.furniticaShop.repository.ItemRepository;
import com.phamquyen.furniticaShop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemRepository itemRepository;

	@Override
	public <S extends Item> List<S> saveAll(Iterable<S> entities) {
		return itemRepository.saveAll(entities);
	}

	@Override
	public <S extends Item> S save(S entity) {
		return itemRepository.save(entity);
	}
	
	
}
